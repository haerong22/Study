import { createRef, useRef, useState } from 'react';
import './App.css';

function App() {
  // useRef (디자인)
  // dom 을 변경할 때 사용
  const ref = useRef(null);

  const [list, setList] = useState([
    { id: 1, name: 'kim' },
    { id: 2, name: 'hong' },
  ]);

  const refs = Array.from({ length: list.length }).map(() => createRef());

  return (
    <>
      <button onClick={() => (refs[0].current.style.backgroundColor = 'red')}>
        색변경
      </button>
      <div ref={ref}>박스</div>
      {list.map((user, index) => (
        <h1 ref={refs[index]}>{user.name}</h1>
      ))}
    </>
  );
}

export default App;
