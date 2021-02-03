import './App.css';

let a = 10;
const b = 20;

function App() {
  const mystyle = {
    color: 'red',
  };

  let list = [1, 2, 3];

  const users = [
    { id: 1, name: 'kim', phone: '1234' },
    { id: 2, name: 'lee', phone: '1234' },
    { id: 3, name: 'choi', phone: '1234' },
  ];
  const update = {
    id: 2,
    name: 'park',
  };
  const newUser = users.map((u) =>
    u.id === update.id ? { ...u, ...update } : u,
  );
  console.log(newUser);

  return (
    <>
      <div style={mystyle}>안녕{a === 10 ? '10입니다.' : '10이 아닙니다'}</div>
      <h1 className="box-style">제목{b === 20 && '20입니다.'}</h1>
      <div>
        {list.map((n) => (
          <h1>n</h1>
        ))}
      </div>
    </>
  );
}

export default App;
