import React from 'react';

const Home = (props) => {
  // const board = props.board;
  // const id = props.id;

  // 구조분할 할당
  const { board, setBoard, number, setNumber } = props;

  return (
    <div>
      <h1>홈 : {number} </h1>
      <button onClick={() => setNumber(number + 1)}>번호증가</button>
      <button onClick={() => setBoard([])}>전체삭제</button>
      {board.map((p) => (
        <h3>
          제목: {p.title} 내용: {p.content}
        </h3>
      ))}
    </div>
  );
};

export default Home;
<h1>홈페이지 입니다.</h1>;
