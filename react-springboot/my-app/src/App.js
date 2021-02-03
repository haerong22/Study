import { useEffect, useState } from 'react';
import './App.css';

function App() {
  const [data, setData] = useState(0);
  const [search, setSearch] = useState(0);

  const download = () => {
    let downloaddata = 5;
    setData(downloaddata);
  };
  // 실행 시점 :
  // (1) App() 함수 최초 실행 될때 (마운트 될 때)
  // (2) state 변경 시
  // (3) 의존리스트 관리 가능
  useEffect(() => {
    console.log('useEffect 실행');
    download();
  }, [search]);

  return (
    <>
      <h1>검색</h1>
      <button onClick={() => setSearch(2)}>검색하기</button>
      <h1>데이터 : {data} </h1>
      <button
        onClick={() => {
          setData(data + 1);
        }}
      >
        더하기
      </button>
    </>
  );
}

export default App;
