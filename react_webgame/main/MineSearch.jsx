import React, { useReducer } from "react";
import Table from "./Table";

const initialState = {
  tableData: [],
  timer: 0,
  result: "",
};

const reducer = (state, action) => {
  switch (action.type) {
    default:
      return state;
  }
};

const MineSearch = () => {
  const [state, dispatch] = useReducer(reducer, initialState);
  const { result } = state;
  return (
    <>
      <Form />
      <div>{timer}</div>
      <Table />
      <div>{result}</div>
    </>
  );
};

export default MineSearch;
