import React, { Component } from "react";
import Lotto from "./games/Lotto";
import NumberBaseball from "./games/NumberBaseball";
import RSP from "./games/RSP";
// import { withRouter } from "react-router-dom";

class GameMatcher extends Component {
  render() {
    console.log(this.props);
    const urlSearchParams = new URLSearchParams(
      this.props.location.search.slice(1)
    );
    console.log(urlSearchParams);
    console.log(urlSearchParams.get("hello"));
    if (this.props.match.params.name === "number-baseball") {
      return <NumberBaseball />;
    } else if (this.props.match.params.name === "rock-scissors-paper") {
      return <RSP />;
    } else if (this.props.match.params.name === "lotto-generator") {
      return <Lotto />;
    }
    return (
      <>
        <div>게임이 없습니다.</div>
      </>
    );
  }
}

export default GameMatcher;
