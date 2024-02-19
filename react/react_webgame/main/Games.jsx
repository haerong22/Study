import React from "react";
import { BrowserRouter, Link, Route, Switch } from "react-router-dom";
import GameMatcher from "./GameMatcher";

const Games = () => {
  return (
    <BrowserRouter>
      <div>
        <Link to="/game/number-baseball?hello=world&query=10">숫자야구</Link>
        &nbsp;
        <Link to="/game/rock-scissors-paper">가위바위보</Link>&nbsp;
        <Link to="/game/lotto-generator">로또추첨기</Link>&nbsp;
        <Link to="/game/index">GameMatcher</Link>
      </div>
      <div>
        <Switch>
          <Route exact path="/game" component={GameMatcher} />
          <Route path="/game/:name" component={GameMatcher} />
        </Switch>
      </div>
    </BrowserRouter>
  );
};

export default Games;
