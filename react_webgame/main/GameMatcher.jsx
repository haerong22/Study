import React, { Component } from "react";
import Lotto from "./games/Lotto";
import NumberBaseball from "./games/NumberBaseball";
import RSP from "./games/RSP";
// import { withRouter } from "react-router-dom";

class GameMatcher extends Component {
  render() {
    if (this.props.match.params.name === "number-baseball") {
      return <NumberBaseball />;
    } else if (this.props.match.params.name === "rock-scissors-paper") {
      return <RSP />;
    } else if (this.props.match.params.name === "lotto-generator") {
      return <Lotto />;
    }
    return <>게임이 없습니다.</>;
  }
}

export default GameMatcher;
