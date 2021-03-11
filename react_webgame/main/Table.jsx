import React, {memo} from "react";
import Tr from "./Tr";

const Table = memo(({ tableData, dispatch }) => {
  console.log("table rendered");
  return (
    <table>
      {Array(tableData.length)
        .fill()
        .map((tr, i) => (
          <Tr rowIndex={i} rowData={tableData[i]} dispatch={dispatch} />
        ))}
    </table>
  );
});

export default Table;
