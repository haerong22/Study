import React from "react";
import { BrowserRouter, HashRouter, Link, Route } from "react-router-dom";
import NumberBaseBall from "./games/NumberBaseball";
import RSP from "./games/RSP";
import Lotto from "./games/Lotto";

const Games = () => {
  return (
    <BrowserRouter>
      <div>
        <Link to="/number-baseball">숫자야구</Link>&nbsp;
        <Link to="/rock-scissors-paper">가위바위보</Link>&nbsp;
        <Link to="/lotto-generator">로또추첨기</Link>
      </div>
      <div>
        <Route path="/number-baseball" component={NumberBaseBall} />
        <Route path="/rock-scissors-paper" component={RSP} />
        <Route path="/lotto-generator" component={Lotto} />
      </div>
    </BrowserRouter>
  );
};

export default Games;
