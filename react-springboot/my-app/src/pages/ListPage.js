import React from 'react';
import { useState } from 'react';
import styled from 'styled-components';

const StyledItemBoxDiv = styled.div`
  display: flex;
  justify-content: space-between;
  border: 1px solid black;
  padding: 10px;
  height: 100px;
  margin: 20px;
  align-items: center;
`;

const ListPage = () => {
  const [post, setPost] = useState({
    id: 0,
    title: '',
    content: '',
  });

  const [posts, setPosts] = useState([
    { id: 1, title: '제목1', content: '내용1' },
    { id: 2, title: '제목2', content: '내용2' },
    { id: 3, title: '제목3', content: '내용3' },
    { id: 4, title: '제목4', content: '내용4' },
    { id: 5, title: '제목5', content: '내용5' },
  ]);

  const handleWrite = (e) => {
    // ListPage의 setPosts
    e.preventDefault();
    setPosts([...posts, post]);
  };

  const handleForm = (e) => {
    // computed property names 문법 (키값 동적 할당)
    setPost({ ...post, [e.target.name]: e.target.value });
  };

  const handleDelete = (props) => {
    setPosts(posts.filter((v) => v.id !== props));
  };

  return (
    <div>
      <h1>글목록 페이지</h1>
      <form onSubmit={handleWrite}>
        <input
          type="text"
          placeholder="제목을 입력하세요"
          value={post.title}
          onChange={handleForm}
          name="title"
        />
        <input
          type="text"
          placeholder="내용을 입력하세요"
          value={post.content}
          onChange={handleForm}
          name="content"
        />
        <button>글쓰기</button>
      </form>
      {posts.map((post) => (
        <StyledItemBoxDiv>
          <div>
            번호 : {post.id} / 제목 : {post.title} / 내용 : {post.content}
          </div>
          <button onClick={() => handleDelete(post.id)}>삭제</button>
        </StyledItemBoxDiv>
      ))}
    </div>
  );
};

export default ListPage;
