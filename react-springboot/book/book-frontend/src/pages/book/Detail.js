import React, { useEffect, useState } from 'react';
import { Button } from 'react-bootstrap';

const Detail = (props) => {
  const id = props.match.params.id;

  const [book, setBook] = useState({
    id: '',
    title: '',
    author: '',
  });

  useEffect(() => {
    fetch('http://localhost:8080/book/' + id)
      .then((res) => res.json())
      .then((res) => {
        setBook(res);
      });
  }, [id]);

  const deleteBook = () => {
    fetch('http://localhost:8080/book/' + id, {
      method: 'delete',
    })
      .then((res) => res.text())
      .then((res) => {
        res === 'ok' ? props.history.push('/') : alert('삭제실패');
      });
  };

  return (
    <div>
      <h1>책 상세보기</h1>
      <Button variant="warning">수정</Button>{' '}
      <Button variant="danger" onClick={deleteBook}>
        삭제
      </Button>
      <hr />
      <h3>{book.author}</h3>
      <h1>{book.title}</h1>
    </div>
  );
};

export default Detail;
