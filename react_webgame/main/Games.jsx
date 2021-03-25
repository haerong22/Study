import React from "react";
import { BrowserRouter, HashRouter, Route } from "react-router-dom";
import NumberBaseBall from "./games/NumberBaseball";
import RSP from "./games/RSP";
import Lotto from "./games/Lotto"

const Games = () => {

  return (
    <BrowserRouter>
      <div>
        <Route path="/number-baseball" component={NumberBaseBall} />
        <Route path="/rock-scissors-paper" component={RSP} />
        <Route path="/lotto-generator" component={Lotto} />
      </div>
    </BrowserRouter>
  );
};

export default Games;
