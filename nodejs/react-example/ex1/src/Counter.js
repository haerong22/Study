import React, {useState} from 'react'
import OddEvenResult from './OddEvenResult';

const Counter = ({initialValue}) =>{ 

  

  const [count, setCount] = useState(initialValue);

  const onIncrease = () => {
    setCount(count + 1);
  }

  const onDecrease = () => {
    setCount(count - 1);
  }

  return (
    <div>
      <h2>{count}</h2>
      <button onClick={onIncrease}>+</button>
      <button onClick={onDecrease}>-</button>
      <OddEvenResult count={count} />
    </div>
  )
}

Counter.defaultProps = {
  initialValue: 0
}

export default Counter;