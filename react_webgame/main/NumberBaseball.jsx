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
                    {[
                        { fruit: '사과', description: '빨개'}, 
                        { fruit: '바나나', description: '길어'},
                        { fruit: '포도', description: '맛있다'},
                        { fruit: '귤', description: '시다'},
                        { fruit: '수박', description: '달다'},    
                    ].map((v) => {
                        return (
                            <li key={v.fruit}><b>{v.fruit}</b> - {v.description}</li>
                        )
                    })}

                    {/* <li><b>사과</b> - 빨개</li>
                    <li><b>바나나</b> - 길어</li>
                    <li><b>포도</b> - 맛있다</li>
                    <li><b>귤</b> - 시다</li>
                    <li><b>수박</b> - 달다</li> */}
                </ul>
            </>
        )
    }
}

export default NumberBaseball; // import NumberBaseball