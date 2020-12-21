import React, { Component } from 'react';
import Try from './Try';

// 숫자 4개를 겹치지않고 랜덤하고 뽑는 함수
function getNumbers() {
    const candidate = [1,2,3,4,5,6,7,8,9];
    const array = [];
    for (let i = 0; i < 4; i++) {
        const chosen = candidate.splice(Math.floor(Math.random() * (9 - i)), 1)[0];
        array.push(chosen);
    }
    return array;
}

class NumberBaseball extends Component {
    state = {
        result: '',
        value: '',
        answer: getNumbers(),
        tries: [], // push 쓰면 안됨
    };

    onSubmitForm = (e) => {
        const { value, tries, answer} = this.state;
        e.preventDefault();
        if(value === answer.join('')) { // 정답
            this.setState({
                result: '홈런!',
                tries: [...tries, { try: value, result: '홈런!'}],
            })
            alert('게임을 다시 시작합니다.');
            this.setState({
                value: '',
                answer: getNumbers(),
                tries: [],
            });
        } else { 
            const answerArray = value.split('').map((v) => parseInt(v));
            let strike = 0;
            let ball = 0;
            if(tries.length >= 9) { // 10번이상 틀렸을 때
                this.setState( {
                    result: `실패! 답은 ${answer.join(',')} 였습니다!`,
                });
                alert('게임을 다시 시작합니다.');
                this.setState({
                    value: '',
                    answer: getNumbers(),
                    tries: [],
                });
            } else {
                for (let i = 0; i < 4; i++) {
                    if(answerArray[i] === answer[i]) {
                        strike++;
                    } else if (answer.includes(answerArray[i])) {
                        ball++;
                    }
                }
                this.setState( {
                    tries: [...tries, {try: value, result: `${strike} 스트라이크, ${ball} 볼 입니다.`}],
                    value: '',
                })
            }
        }
    }

    onChangeInput = (e) => {
        this.setState( {
            value: e.target.value,
        })
    }

    input;
    onRefInput = (c) => {
        this.input = c;
    }

    render() {
        const { result, value, tries } = this.state;
        return (
            <>
                <h1>{result}</h1>
                <form onSubmit={this.onSubmitForm}>
                    <input ref={this.onRefInput} maxLength={4} value={value} onChange={this.onChangeInput}/>
                    <button>입력!</button>
                </form>
                <div>시도: {tries.length}</div>
                <ul>
                    {tries.map((v, i) => {
                        return (
                            <Try key={`${i + 1}차 시도 :`} tryInfo={v} index={i} />
                        )
                    })}
                </ul>
            </>
        )
    }
}

export default NumberBaseball; // import NumberBaseball