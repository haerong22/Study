import React, { createContext, useReducer, useMemo } from "react";
import Form from "./Form";
import Table from "./Table";

// 칸의 상태에 따라 코드 부여
export const CODE = {
  MINE: -7,
  NORMAL: -1,
  QUESTION: -2,
  FLAG: -3,
  QUESTION_MINE: -4,
  FLAG_MINE: -5,
  CLICKED_MINE: -6,
  OPENED: 0, // 0 이상이면 다 opened
};

// context api
export const TableContext = createContext({
  tableData: [],
  dispatch: () => {},
});

// state 초기값
const initialState = {
  tableData: [],
  timer: 0,
  result: "",
};

// action 명
export const START_GAME = "START_GAME";

// action 동작 정의
const reducer = (state, action) => {
  switch (action.type) {
    case START_GAME:
      return {
        ...state,
        tableDate: plantMine(action.row, action.cell, action.mine),
      };
    default:
      return state;
  }
};

const MineSearch = () => {
  const [state, dispatch] = useReducer(reducer, initialState);

  const value = useMemo(() => {
    tableData: state.tableData, dispatch;
  }, [state.tableData]);

  return (
    <TableContext.Provider value={value}>
      <Form />
      <div>{timer}</div>
      <Table />
      <div>{result}</div>
    </TableContext.Provider>
  );
};

export default MineSearch;
