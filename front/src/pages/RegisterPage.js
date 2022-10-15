import { useState } from 'react';
import axios from 'axios';
import qs from 'qs';
import { baseURL } from '../common';
import { useNavigate } from 'react-router-dom';
import './RegisterPage.css';

function RegisterPage() {
  const [title, setTitle] = useState(null);
  const [content, setContent] = useState(null);
  const [writer, setWriter] = useState(null);
  const navigate = useNavigate();

  const submitRegister = (e) => {
    e.preventDefault();

    const postRegister = async () => {
      const data = {
        title,
        content,
        writer,
      };
      try {
        await axios.post(`${baseURL}/guestbook/register`, qs.stringify(data));
        navigate('/');
      } catch (e) {
        alert(e);
      }
    };
    postRegister();
  };

  const inputChangeHandler = (e) => {
    if (e.target.className === 'title') {
      setTitle(e.target.value);
      return;
    }
    if (e.target.className === 'content') {
      setContent(e.target.value);
      return;
    }
    if (e.target.className === 'writer') {
      setWriter(e.target.value);
      return;
    }
  };
  return (
    <>
      <h1>Guestbook Register Page</h1>
      <form
        className="register"
        onSubmit={submitRegister}
        onChange={inputChangeHandler}
      >
        <h3>Title</h3>
        <input type="text" className="title" placeholder="title"></input>
        <h3>Content</h3>
        <input type="text" className="content" placeholder="content"></input>
        <h3>Writer</h3>
        <input type="text" className="writer" placeholder="writer"></input>
        <input type="submit"></input>
      </form>
    </>
  );
}

export default RegisterPage;
