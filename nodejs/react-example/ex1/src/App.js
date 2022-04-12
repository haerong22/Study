// import './App.css';

import MyHeader from './MyHeader';
import MyFooter from './MyFooter';
import Counter from './Counter';
import Container from './Container';

function App() {

  const name = "react";

  const style = {
    App : {
      backgroundColor: 'white',
    },
    h2 : {
      color: 'red',
    },
    bold_text : {
      color: 'green'
    }
  }

  const number = 5;

  const counterProps = {
    a: 1,
    b: 2,
    c: 3,
    d: 4,
    e: 5,
    // initialValue: 5,
  }

  return (
    <Container>
      <div style={style.App}>
        <MyHeader />
        <h2 style={style.h2}>
          hello {name}!
        </h2>
        <b style={style.bold_text} id="bold_text">
          {number}는 : {number % 2 === 0 ? "짝수" : "홀수"}
        </b>
        <h2>Counter</h2>
        <Counter {...counterProps} />
        <MyFooter />
      </div>
    </Container>
  );
}

export default App;
