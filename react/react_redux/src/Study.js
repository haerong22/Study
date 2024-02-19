import React, { Component } from "react";

const Loading = () => <div>Loading...</div>;

class Study extends Component {
  constructor(props) {
    super(props);
    this.state = {
      loading: true,
    };
  }

  comment() {
    const { loading } = this.state;
    const con = 1;
    const res = con > 0 ? true : false; // 두가지 조건 (if, else)
    const and = loading && <div>loading...</div>; // 한가지 조건(if)
  }
  render() {
    const { loading } = this.state;
    return (
      <>
        {loading ? <Loading /> : <div>this is a webpage</div>}
        {loading && <Loading />}
      </>
    );
  }
}

export default Study;
