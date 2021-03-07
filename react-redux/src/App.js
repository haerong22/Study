import "./App.css";

const Head = (props) => <h1>{props.title}</h1>;

function App() {
  return (
    <>
      <Head title="this is a title" name="this is a name" />
      <Head title="this is a title" name="this is a name" />
    </>
  );
}

export default App;
