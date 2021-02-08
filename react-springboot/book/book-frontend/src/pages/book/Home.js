import React, { useState, useEffect } from 'react';
import BookItem from '../../components/BookItem';

const Home = () => {
  const [books, setBooks] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/book')
      .then((res) => res.json())
      .then((res) => {
        setBooks(res);
      }); // 비동기 함수
  }, []);

  return (
    <div>
      {books.map((book) => (
        <BookItem key={book.id} book={book} />
      ))}
    </div>
  );
};

export default Home;
