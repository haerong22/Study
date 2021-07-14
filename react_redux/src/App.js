import "./App.css";
import Study from "./Study";

const Head = (props) => <h1>{props.title}</h1>;

function App() {
  return (
    <>
      <Head title="this is a title" name="this is a name" />
      <Study />
    </>
  );
}

export default App;
