import { useState } from 'react';
import './App.css';

function App() {
  let sample = [
    { id: 1, name: '홍길동' },
    { id: 2, name: '임꺽정' },
    { id: 3, name: '장보고' },
    { id: 4, name: '세종대왕' },
  ];
  const [users, setUsers] = useState(sample);
  const [num, setNum] = useState(5);

  const download = () => {
    setUsers([...sample, { id: num, name: '조자룡' }]);
    setNum(num + 1);
  };

  console.log('rendering');
  return (
    <>
      <div>
        {users.map((u) => (
          <h1>
            {u.id}, {u.name}
          </h1>
        ))}
      </div>
      <button onClick={download}>다운로드</button>
    </>
  );
}

export default App;
