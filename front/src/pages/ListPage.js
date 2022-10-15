import GuestbookList from '../components/GuestbookList';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import './ListPage.css';

function ListPage() {
  const navigate = useNavigate();
  const [searchParam, setSearchParam] = useState({ type: 't', size: 10 });
  const reigsterClickHandler = () => {
    navigate('/register');
  };
  const typeChangeHandler = (e) => {
    setSearchParam({ ...searchParam, type: e.target.value });
  };
  const keywordChangeHandler = (e) => {
    setSearchParam({ ...searchParam, keyword: e.target.value });
  };

  return (
    <>
      <h1>Guestbook List Page</h1>
      <div className="list-control">
        <select onChange={typeChangeHandler}>
          <option value="t">제목</option>
          <option value="c">내용</option>
          <option value="w">작성자</option>
          <option value="tc">제목 + 내용</option>
          <option value="tcw">제목 + 내용 + 작성자</option>
        </select>
        <input
          type="text"
          onChange={keywordChangeHandler}
          placeholder="검색어"
        ></input>
        <button onClick={reigsterClickHandler}>글쓰기</button>
      </div>
      <GuestbookList searchParam={searchParam} />
    </>
  );
}

export default ListPage;
