import React, { useState, useEffect, useReducer, useCallback } from "react";
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
  recentCell: [-1, -1],
};

// action 이름 설정 - 하위 컴포넌트에서 사용하기 위해 모듈화
export const SET_WINNER = "SET_WINNER";
export const CLICK_CELL = "CLICK_CELL";
export const CHANGE_TURN = "CHANGE_TURN";
export const RESET_GAME = "RESET_GAME";

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
        recentCell: [action.row, action.cell]
      };
    }
    // 칸을 클릭 할 때마다 O X 번갈아가면서 출력
    case CHANGE_TURN: {
      return {
        ...state,
        turn: state.turn === "O" ? "X" : "O",
      };
    }
    case RESET_GAME: {
      return {
        ...state,
        turn: "O",
        tableData: [
          ["", "", ""],
          ["", "", ""],
          ["", "", ""],
        ],
        recentCell: [-1, -1],
      }
    }
    default:
      return state;
  }
};

const TicTacToe = () => {
  // useReducer 사용
  const [state, dispatch] = useReducer(reducer, initialState);
  const { tableData, turn, winner, recentCell } = state;
  // 승자 체크
  useEffect(() => {
    const [row, cell] = recentCell;
    if (row < 0) {
      return;
    }
    let win = false;
    if (tableData[row][0] === turn && tableData[row][1] === turn && tableData[row][2] === turn) {
      win = true;
    }
    if (tableData[0][cell] === turn && tableData[1][cell] === turn && tableData[2][cell] === turn) {
      win = true;
    }
    if (tableData[0][0] === turn && tableData[1][1] === turn && tableData[2][2] === turn) {
      win = true;
    }
    if (tableData[0][2] === turn && tableData[1][1] === turn && tableData[2][0] === turn) {
      win = true;
    }
    if (win) {
      // 승자가 있을 때
      dispatch({type:SET_WINNER, winner: turn})
      dispatch({type:RESET_GAME});
    } else {
      // 무승부 일 때
      let all = true; // all 이 true 면 무승부
      tableData.forEach((row) => {
        row.forEach((cell) => {
          if(!cell) {
            all = false;
          }
        })
      });
      if(all) { // 모든 칸이 다 차있으면 무승부
        dispatch({type:RESET_GAME});
      } else {
        dispatch({ type: CHANGE_TURN });
      }
    }
  }, [recentCell])

  return (
    <>
      <Table tableData={tableData} dispatch={dispatch} />
      {winner && <div>{winner}님의 승리</div>}
    </>
  );
};

export default TicTacToe;
