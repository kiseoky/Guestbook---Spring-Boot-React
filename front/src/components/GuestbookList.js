import axios from 'axios';
import { useEffect, useState } from 'react';
import './GuestbookList.css';
import { useNavigate } from 'react-router-dom';
import { baseURL } from '../common';
import qs from 'qs';

function GuestbookList({ searchParam }) {
  const [guestbookList, setGuestbookList] = useState(null);
  const [pageInfo, setPageInfo] = useState({
    pageList: null,
    prevPage: false,
    nextPage: false,
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [page, setPage] = useState(1);
  const navigate = useNavigate();

  useEffect(() => {
    const getList = async () => {
      setGuestbookList(null);
      setLoading(true);
      setError(null);
      setPageInfo({
        ...pageInfo,
        pageList: null,
        prevPage: false,
        nextPage: false,
      });

      try {
        const response = await axios(
          `${baseURL}/guestbook/list?&${qs.stringify({
            ...searchParam,
            page,
          })}`
        );

        const {
          dtoList,
          pageList,
          prev: prevPage,
          next: nextPage,
        } = response.data;
        setGuestbookList(dtoList);
        setPageInfo({ ...pageInfo, pageList, prevPage, nextPage });
      } catch (e) {
        setError(e);
        console.log(e);
      }
      setLoading(false);
    };
    getList();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [page, searchParam]);

  const pageClickHandler = (e) => {
    if (!e.target.classList.contains('page')) return;

    if (e.target.classList.contains('prev')) {
      setPage(Number(pageInfo.pageList.at(0) - searchParam.size));
      return;
    }
    if (e.target.classList.contains('next')) {
      setPage(Number(pageInfo.pageList.at(-1) + 1));
      return;
    }

    setPage(Number(e.target.textContent));
  };

  const guestbookClickHandler = (id) => {
    navigate(`/${id}`);
  };

  if (loading || guestbookList === null) return <div>로딩중</div>;
  if (error) return <div>에러</div>;

  return (
    <>
      <table className="guestbook-container">
        <thead>
          <tr>
            <th>#</th>
            <th>Title</th>
            <th>Writer</th>
            <th>createdAt</th>
          </tr>
        </thead>
        <tbody>
          {guestbookList.map((guestbook) => (
            <tr
              key={guestbook.id}
              onClick={() => guestbookClickHandler(guestbook.id)}
            >
              <td>{guestbook.id}</td>
              <td>{guestbook.title}</td>
              <td>{guestbook.writer}</td>
              <td>{new Date(guestbook.createdAt).toLocaleDateString()}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <div onClick={pageClickHandler} className="page-list">
        {pageInfo.prevPage && <div className="page prev"> {'<'} </div>}
        {pageInfo.pageList.map((pnum, index) => (
          <div
            className={`page ${page === pnum && 'active'}`}
            key={index}
            onClick={pageClickHandler}
          >
            {pnum}
          </div>
        ))}
        {pageInfo.nextPage && <div className="page next"> {'>'} </div>}
      </div>
    </>
  );
}

export default GuestbookList;
