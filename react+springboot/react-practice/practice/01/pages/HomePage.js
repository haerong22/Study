import React, { useEffect, useState } from 'react';
import Home from '../components/home/Home';

const HomePage = () => {
  // Http 요청 (fetch, axios)
  const [board, setBoard] = useState([]);
  const [user, setUser] = useState({});

  useEffect(() => {
    // 데이터를 받았다고 가정
    // fetch(), axios()
    let data = [
      { id: 1, title: '제목1', content: '내용1' },
      { id: 2, title: '제목2', content: '내용2' },
      { id: 3, title: '제목3', content: '내용3' },
    ];

    setBoard([...data]);
    setUser({ id: 1, username: 'lee' });
  }, []);

  return (
    <div>
      <Home board={board} setBoard={setBoard} user={user} />
    </div>
  );
};

export default HomePage;

// props
// const HomePage = () => {
//   // Http 요청 (fetch, axios)
//   const [board, setBoard] = useState([]);
//   const [number, setNumber] = useState(0);

//   useEffect(() => {
//     // 데이터를 받았다고 가정
//     // fetch(), axios()
//     let data = [
//       { id: 1, title: '제목1', content: '내용1' },
//       { id: 2, title: '제목2', content: '내용2' },
//       { id: 3, title: '제목3', content: '내용3' },
//     ];

//     setBoard([...data]);
//   }, []);

//   return (
//     <div>
//       <Header />
//       <Home
//         number={number}
//         board={board}
//         setBoard={setBoard}
//         setNumber={setNumber}
//       />
//       <Footer />
//     </div>
//   );
// };

// export default HomePage;
