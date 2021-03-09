import React from "react";
import Td from "./Td";

const Tr = ({ rowData }) => {
  return (
    <tr>
      {Array(rowData.length)
        .fill()
        .map((td) => (
          <Td>{""}</Td>
        ))}
    </tr>
  );
};

export default Tr;
