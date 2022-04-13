import "./App.css";
import DiaryEditor from "./DiaryEditor";
import DiaryList from "./DiaryList";

const dummyList = [
  {
    id: 1,
    author: "kim",
    content: "hihi",
    emotion: 5,
    created_date: new Date().getTime(),
  },
  {
    id: 2,
    author: "lee",
    content: "hihi",
    emotion: 2,
    created_date: new Date().getTime(),
  },
  {
    id: 3,
    author: "park",
    content: "hihi",
    emotion: 4,
    created_date: new Date().getTime(),
  },
];

const App = () => {
  return (
    <div className="App">
      <DiaryEditor />
      <DiaryList diaryList={dummyList} />
    </div>
  );
};

export default App;
