import React from "react";
import Tr from "./Tr";

const Table = ({ onClick, tableData, dispatch }) => {
  return (
    <table>
      {Array(tableData.length)
        .fill()
        .map((tr, i) => (
          <Tr rowIndex={i} rowData={tableData[i]} dispatch={dispatch} />
        ))}
    </table>
  );
};

export default Table;
