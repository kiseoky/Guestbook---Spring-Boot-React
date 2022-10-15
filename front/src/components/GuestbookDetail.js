import axios from 'axios';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { baseURL } from '../common';
import qs from 'qs';
import './GuestbookDetail.css';

function GuestbookDetail({ id }) {
  const [guestbook, setGuestbook] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [modifyMode, setModifyMode] = useState(false);
  const navigate = useNavigate();
  const [title, setTitle] = useState(null);
  const [content, setContent] = useState(null);

  useEffect(() => {
    const getGuestbook = async () => {
      setGuestbook(null);
      setLoading(true);
      setError(null);

      try {
        const response = await axios(`${baseURL}/guestbook/${id}`);
        console.log(response.data);
        setGuestbook(response.data);
        setTitle(response.data.title);
        setContent(response.data.content);
      } catch (e) {
        setError(e);
        console.log(e);
      }
      setLoading(false);
    };
    getGuestbook();
  }, [id]);

  const modifyHandler = async () => {
    if (!modifyMode) {
      setModifyMode(true);
      return;
    }
    const data = {
      id,
      title,
      content,
    };
    await axios.post(`${baseURL}/guestbook/modify`, qs.stringify(data));
    setModifyMode(false);
  };

  const removeHandler = async () => {
    await axios.post(`${baseURL}/guestbook/remove?id=${id}`);
    navigate('/');
  };
  if (loading || guestbook === null) return <div>로딩중</div>;
  if (error) return <div>에러</div>;

  return (
    <>
      <h1>Guestbook {modifyMode ? 'Modify' : 'Read'} Page</h1>
      <h3>ID</h3>
      <input readOnly value={guestbook.id}></input>
      <h3>Title</h3>
      <input
        type="text"
        readOnly={!modifyMode}
        onChange={(e) => setTitle(e.target.value)}
        defaultValue={guestbook.title}
      ></input>
      <h3>Content</h3>
      <input
        type="text"
        readOnly={!modifyMode}
        onChange={(e) => setContent(e.target.value)}
        defaultValue={guestbook.content}
      ></input>
      <h3>작성자</h3>
      <input readOnly value={guestbook.writer}></input>
      <h3>작성일</h3>
      <input readOnly value={guestbook.createdAt}></input>
      <h3>수정일</h3>
      <input readOnly value={guestbook.updatedAt}></input>
      <br />
      <br />
      <button onClick={modifyHandler}>수정</button>
      <button onClick={() => navigate('/')}>리스트</button>
      <button onClick={removeHandler}>삭제</button>
    </>
  );
}

export default GuestbookDetail;
