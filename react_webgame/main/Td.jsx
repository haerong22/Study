import React, { memo, useCallback, useContext, useMemo } from "react";
import {
  CODE,
  TableContext,
  OPEN_CELL,
  CLICKED_MINE,
  FLAG_CELL,
  QUESTION_CELL,
  NORMALIZED_CELL,
} from "./MineSearch";

// 칸의 상태에 따라 색 배경색 변경
const getTdStyle = (code) => {
  switch (code) {
    case CODE.NORMAL:
    case CODE.MINE:
      return {
        background: "#444",
      };
    case CODE.CLICKED_MINE:
    case CODE.OPENED:
      return {
        background: "white",
      };
    case CODE.QUESTION_MINE:
    case CODE.QUESTION:
      return {
        background: "yellow",
      };
    case CODE.FLAG_MINE:
    case CODE.FLAG:
      return {
        background: "red",
      };
    default:
      return {
        background: "white",
      };
  }
};

// 칸 상태에 따라 출력 값
const getTdText = (code) => {
  console.log("td rendering");
  switch (code) {
    case CODE.NORMAL:
      return "";
    case CODE.MINE:
      return "";
    case CODE.CLICKED_MINE:
      return "펑";
    case CODE.FLAG_MINE:
    case CODE.FLAG:
      return "!";
    case CODE.QUESTION_MINE:
    case CODE.QUESTION:
      return "?";
    default:
      return code || "";
  }
};

const Td = memo(({ rowIndex, cellIndex }) => {
  const { tableData, dispatch, halted } = useContext(TableContext);

  // 클릭시 상태에 따라 dispatch 수행
  const onClickTd = useCallback(() => {
    if (halted) {
      return;
    }
    switch (tableData[rowIndex][cellIndex]) {
      case CODE.OPENED:
      case CODE.FLAG_MINE:
      case CODE.FLAG:
      case CODE.QUESTION_MINE:
      case CODE.QUESTION:
      case CODE.NORMAL:
        dispatch({ type: OPEN_CELL, row: rowIndex, cell: cellIndex }); // 기본칸 일 경우 OPEN_CELL 액션 실행
        return;
      case CODE.MINE:
        dispatch({ type: CLICKED_MINE, row: rowIndex, cell: cellIndex }); // 지뢰칸 일 경우 CLICKED_MINE 액션 실행
        return;
      default:
        return;
    }
  }, [tableData[rowIndex][cellIndex], halted]);

  // 오른쪽 클릭시
  const onRightClickTd = useCallback(
    (e) => {
      e.preventDefault(); // 우클릭시 메뉴 뜨는 이벤트 제거
      if (halted) {
        // 종료된 게임일 경우 바로 리턴
        return;
      }

      // 클릭하지 않은 칸일 때 깃발 -> 물음표 -> 기본 반복
      switch (tableData[rowIndex][cellIndex]) {
        case CODE.NORMAL:
        case CODE.MINE:
          dispatch({ type: FLAG_CELL, row: rowIndex, cell: cellIndex });
          return;
        case CODE.FLAG_MINE:
        case CODE.FLAG:
          dispatch({ type: QUESTION_CELL, row: rowIndex, cell: cellIndex });
          return;
        case CODE.QUESTION_MINE:
        case CODE.QUESTION:
          dispatch({ type: NORMALIZED_CELL, row: rowIndex, cell: cellIndex });
          return;
        default:
          return;
      }
    },
    [tableData[rowIndex][cellIndex], halted]
  );

  return (
    <RealTd
      onClickTd={onClickTd}
      onRightClickTd={onRightClickTd}
      data={tableData[rowIndex][cellIndex]}
    />
  );
  // return useMemo(
  //   () => (
  //     <td
  //       style={getTdStyle(tableData[rowIndex][cellIndex])}
  //       onClick={onClickTd}
  //       onContextMenu={onRightClickTd}
  //     >
  //       {getTdText(tableData[rowIndex][cellIndex])}
  //     </td>
  //   ),
  //   [tableData[rowIndex][cellIndex]]
  // );
});

const RealTd = memo(({ onClickTd, onRightClickTd, data }) => {
  console.log("realTd rendering");
  return (
    <td
      style={getTdStyle(data)}
      onClick={onClickTd}
      onContextMenu={onRightClickTd}
    >
      {getTdText(data)}
    </td>
  );
});

export default Td;
