import React, { Component } from 'react';

// 숫자 4개를 겹치지않고 랜덤하고 뽑는 함수
function getNumbers() {

}

class NumberBaseball extends Component {
    state = {
        result: '',
        value: '',
        answer: getNumbers(),
        tries: [],
    };

    onSubmitForm = () => {

    }

    onChangeInput = () => {

    }

    input;
    onRefInput = (c) => {
        this.input = c;
    }

    render() {
        return (
            <>
                <h1>{this.state.result}</h1>
                <form onSubmit={this.onSubmitForm}>
                    <input ref={this.onRefInput} maxLength={4} value={this.state.value} onChange={this.onChangeInput}/>
                </form>
                <div>시도: {this.state.tries.length}</div>
                <ul>
                    {['사과', '바나나', '포도', '귤', '수박'].map((v) => {
                        return (
                            <li>{v}</li>
                        )
                    })

                    }
                </ul>
            </>
        )
    }
}

export default NumberBaseball; // import NumberBaseball