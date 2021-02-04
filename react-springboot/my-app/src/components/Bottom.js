import React from 'react';
import { useDispatch } from 'react-redux';
import '../App.css';
import { decrease, increase } from '../store';

const Bottom = () => {
  const dispatch = useDispatch();
  return (
    <div className="sub_container">
      <h1>Bottom</h1>
      <button onClick={() => dispatch(increase('kim'))}>증가</button>
      <button onClick={() => dispatch(decrease())}>감소</button>
    </div>
  );
};

export default Bottom;
