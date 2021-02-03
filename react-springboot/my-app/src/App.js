import { useMemo, useState } from 'react';
import './App.css';

function App() {
  const [list, setList] = useState([1, 2, 3, 4]);
  const [str, setStr] = useState('합계');

  const getAddResult = () => {
    let sum = 0;
    list.forEach((i) => (sum += i));
    console.log(sum);
    return sum;
  };
  const addResult = useMemo(() => getAddResult(), [list]);
  return (
    <>
      <button
        onClick={() => {
          setStr('안녕');
        }}
      >
        문자변경
      </button>
      <button
        onClick={() => {
          setList([...list, 10]);
        }}
      >
        리스트 값 추가
      </button>
      <div>
        {list.map((i) => (
          <h1>{i}</h1>
        ))}
      </div>
      <div>
        {str} : {addResult}
      </div>
    </>
  );
}

export default App;
