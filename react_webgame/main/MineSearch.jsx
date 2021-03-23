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
  halted: true,
  dispatch: () => {},
});

// state 초기값
const initialState = {
  tableData: [],
  timer: 0,
  result: "",
  halted: true,
};

// 지뢰 심는 함수
const plantMine = (row, cell, mine) => {
  console.log(row, cell, mine);
  const candidate = Array(row * cell)
    .fill()
    .map((arr, i) => {
      // 0 ~ 가로*세로 만큼 숫자 배열 생성
      return i;
    });
  const shuffle = [];
  // 지뢰 갯수 만큼 지뢰 생성
  while (candidate.length > row * cell - mine) {
    const chosen = candidate.splice(
      Math.floor(Math.random() * candidate.length),
      1
    )[0];
    shuffle.push(chosen);
  }

  // 가로 * 세로 만큼의 2차원 배열 생성
  const data = [];
  for (let i = 0; i < row; i++) {
    const rowData = [];
    data.push(rowData);
    for (let j = 0; j < cell; j++) {
      rowData.push(CODE.NORMAL);
    }
  }

  // 지뢰 심기
  for (let k = 0; k < shuffle.length; k++) {
    const ver = Math.floor(shuffle[k] / cell);
    const hor = shuffle[k] % cell;
    data[ver][hor] = CODE.MINE;
  }

  console.log(data);
  return data;
};

// action 명
export const START_GAME = "START_GAME";
export const OPEN_CELL = "OPEN_CELL";
export const CLICKED_MINE = "CLICKED_MINE";
export const FLAG_CELL = "FLAG_CELL";
export const QUESTION_CELL = "QUESTION_CELL";
export const NORMALIZED_CELL = "NORMALIZED_CELL";

// action 동작 정의
const reducer = (state, action) => {
  switch (action.type) {
    // 게임 시작
    case START_GAME:
      return {
        ...state,
        tableData: plantMine(action.row, action.cell, action.mine),
        halted: false,
      };
    // 지뢰 없는 칸 클릭
    case OPEN_CELL: {
      const tableData = [...state.tableData];
      tableData.forEach((row, i) => {
        tableData[i] = [...row];
      });
      const checked = [];
      const checkAround = (row, cell) => {
        // 닫힌 칸만 열기
        if (
          [
            CODE.OPENED,
            CODE.FLAG_MINE,
            CODE.FLAG,
            CODE.QUESTION_MINE,
            CODE.QUESTION,
          ].includes(tableData[row][cell])
        ) {
          return;
        }
        // 상하 좌우 칸이 없는 경우 필터링
        if (
          row < 0 ||
          row >= tableData.length ||
          cell < 0 ||
          cell >= tableData[0].length
        ) {
          return;
        }
        // 이미 검사한 칸이면
        if (checked.includes(row + "," + cell)) {
          return;
        } else {
          checked.push(row + "," + cell);
        }

        // 주변 칸의 값을 담을 배열
        let around = [
          tableData[row][cell - 1],
          tableData[row][cell + 1], // 클릭한 칸의 좌, 우
        ];
        if (tableData[row - 1]) {
          // 클릭한 칸의 윗줄이 있을 경우
          around = around.concat(
            tableData[row - 1][cell - 1],
            tableData[row - 1][cell],
            tableData[row - 1][cell + 1]
          );
        }
        if (tableData[row + 1]) {
          // 클릭한 칸의 아랫줄이 있을 경우
          around = around.concat(
            tableData[row + 1][cell - 1],
            tableData[row + 1][cell],
            tableData[row + 1][cell + 1]
          );
        }
        // 선택한 칸의 주변 칸의 지뢰 갯수
        const count = around.filter((v) =>
          [CODE.MINE, CODE.FLAG_MINE, CODE.QUESTION_MINE].includes(v)
        ).length;
        tableData[row][cell] = count;

        // 주변 칸 모두 빈칸 일 경우
        if (count === 0) {
          const near = [];
          if (row - 1 > -1) {
            near.push([row - 1, cell - 1]);
            near.push([row - 1, cell]);
            near.push([row - 1, cell + 1]);
          }
          near.push([row, cell - 1]);
          near.push([row, cell + 1]);
          if (row + 1 < tableData.length) {
            near.push([row + 1, cell - 1]);
            near.push([row + 1, cell]);
            near.push([row + 1, cell + 1]);
          }
          near.forEach((n) => {
            if (tableData[n[0]][n[1]] !== CODE.OPENED) {
              checkAround(n[0], n[1]);
            }
          });
        }
      };
      checkAround(action.row, action.cell);
      return {
        ...state,
        tableData,
      };
    }
    // 지뢰 클릭
    case CLICKED_MINE: {
      const tableData = [...state.tableData];
      tableData[action.row] = [...state.tableData[action.row]];
      tableData[action.row][action.cell] = CODE.CLICKED_MINE;
      return {
        ...state,
        tableData,
        halted: true,
      };
    }
    // 기본 칸 우클릭 시 깃발 칸
    case FLAG_CELL: {
      const tableData = [...state.tableData];
      tableData[action.row] = [...state.tableData[action.row]];
      if (tableData[action.row][action.cell] === CODE.MINE) {
        tableData[action.row][action.cell] = CODE.FLAG_MINE;
      } else {
        tableData[action.row][action.cell] = CODE.FLAG;
      }
      return {
        ...state,
        tableData,
      };
    }
    // 깃발 칸 우클릭 시 물음표 칸
    case QUESTION_CELL: {
      const tableData = [...state.tableData];
      tableData[action.row] = [...state.tableData[action.row]];
      if (tableData[action.row][action.cell] === CODE.FLAG_MINE) {
        tableData[action.row][action.cell] = CODE.QUESTION_MINE;
      } else {
        tableData[action.row][action.cell] = CODE.QUESTION;
      }
      return {
        ...state,
        tableData,
      };
    }
    // 물음표 칸 우클릭 시 기본 칸
    case NORMALIZED_CELL: {
      const tableData = [...state.tableData];
      tableData[action.row] = [...state.tableData[action.row]];
      if (tableData[action.row][action.cell] === CODE.QUESTION_MINE) {
        tableData[action.row][action.cell] = CODE.MINE;
      } else {
        tableData[action.row][action.cell] = CODE.NORMAL;
      }
      return {
        ...state,
        tableData,
      };
    }
    default:
      return state;
  }
};

const MineSearch = () => {
  const [state, dispatch] = useReducer(reducer, initialState);
  const { tableData, halted, timer, result } = state;

  const value = useMemo(
    () => ({ tableData: tableData, halted: halted, dispatch }),
    [tableData, halted]
  );

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
