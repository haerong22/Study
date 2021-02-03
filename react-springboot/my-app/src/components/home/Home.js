import React from 'react';
import styled from 'styled-components';

const StyledDeleteButton = styled.button`
  color: ${(props) => (props.user.username === 'kim' ? 'blue' : 'red')};
`;

const StyleAddButton = styled(StyledDeleteButton)`
  background-color: green;
`;

const Home = (props) => {
  // 구조분할 할당
  const { board, setBoard, user } = props;

  return (
    <div>
      <h1>홈</h1>
      <StyleAddButton user={user}>더하기</StyleAddButton>
      <StyledDeleteButton user={user} onClick={() => setBoard([])}>
        전체삭제
      </StyledDeleteButton>
      {board.map((p) => (
        <h3>
          제목: {p.title} 내용: {p.content}
        </h3>
      ))}
    </div>
  );
};

export default Home;

// props
// const Home = (props) => {
//   // const board = props.board;
//   // const id = props.id;

//   // 구조분할 할당
//   const { board, setBoard, number, setNumber } = props;

//   return (
//     <div>
//       <h1>홈 : {number} </h1>
//       <button onClick={() => setNumber(number + 1)}>번호증가</button>
//       <button onClick={() => setBoard([])}>전체삭제</button>
//       {board.map((p) => (
//         <h3>
//           제목: {p.title} 내용: {p.content}
//         </h3>
//       ))}
//     </div>
//   );
// };

// export default Home;
