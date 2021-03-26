import React from "react";
import { BrowserRouter, HashRouter, Link, Route } from "react-router-dom";
import NumberBaseBall from "./games/NumberBaseball";
import RSP from "./games/RSP";
import Lotto from "./games/Lotto";
import GameMatcher from "./GameMatcher";

const Games = () => {
  return (
    <HashRouter>
      <div>
        <Link to="/game/number-baseball?hello=world&query=10">숫자야구</Link>
        &nbsp;
        <Link to="/game/rock-scissors-paper">가위바위보</Link>&nbsp;
        <Link to="/game/lotto-generator">로또추첨기</Link>&nbsp;
        <Link to="/game/index">GameMatcher</Link>
      </div>
      <div>
        <Route path="/game/:name" component={GameMatcher} />
      </div>
    </HashRouter>
  );
};

export default Games;
