import React, { useState, useReducer, useCallback } from "react";
import Table from "./Table";

// 초기 state 값 설정
const initialState = {
  winner: "",
  turn: "O",
  tableData: [
    ["", "", ""],
    ["", "", ""],
    ["", "", ""],
  ],
};

// action 이름 설정 - 하위 컴포넌트에서 사용하기 위해 모듈화
export const SET_WINNER = "SET_WINNER";
export const CLICK_CELL = "CLICK_CELL";
export const CHANGE_TURN = "CHANGE_TURN";

// reducer 설정 - dispatch 한 action 의 값들을 이용해 state 변경
const reducer = (state, action) => {
  switch (action.type) {
    // winner 값 설정
    case SET_WINNER:
      return {
        ...state,
        winner: action.winner,
      };
    // 클릭한 칸 안에 데이터 출력
    case CLICK_CELL: {
      const tableData = [...state.tableData];
      tableData[action.row] = [...tableData[action.row]]; // immer 라는 라이브러리로 가독성 문제 해결
      tableData[action.row][action.cell] = state.turn;
      return {
        ...state,
        tableData,
      };
    }
    // 칸을 클릭 할 때마다 O X 번갈아가면서 출력
    case CHANGE_TURN: {
      return {
        ...state,
        turn: state.turn === "O" ? "X" : "O",
      };
    }
  }
};

const TicTacToe = () => {
  // useReducer 사용
  const [state, dispatch] = useReducer(reducer, initialState);

  return (
    <>
      <Table tableData={state.tableData} dispatch={dispatch} />
      {state.winner && <div>{state.winner}님의 승리</div>}
    </>
  );
};

export default TicTacToe;
