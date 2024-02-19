import React, { useState, useRef, memo } from 'react';
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

const NumberBaseball = memo(() => {
    const [result, setresult] = useState('');
    const [value, setvalue] = useState('');
    const [answer, setanswer] = useState(getNumbers());
    const [tries, settries] = useState([]);
    const inputRef = useRef(null);

    const onSubmitForm = (e) => {
        e.preventDefault();
        if(value === answer.join('')) { // 정답
            setresult('홈런!');
            settries((prevTries) => {
                return [...prevTries, { try: value, result: '홈런!'}]
            })
            alert('게임을 다시 시작합니다.');
            setvalue('');
            setanswer(getNumbers());
            settries([]);
            inputRef.current.focus();
        } else { 
            const answerArray = value.split('').map((v) => parseInt(v));
            let strike = 0;
            let ball = 0 ;
            if(tries.length >= 9) { // 10번이상 틀렸을 때
                setresult(`실패! 답은 ${answer.join(',')} 였습니다!`);
                alert('게임을 다시 시작합니다.');
                setvalue('');
                setanswer(getNumbers());
                settries([]);
            } else {
                for (let i = 0; i < 4; i++) {
                    if(answerArray[i] === answer[i]) {
                        strike++;
                    } else if (answer.includes(answerArray[i])) {
                        ball++;
                     }
                }
                settries((prevTries) => {
                    return [...tries, {try: value, result: `${strike} 스트라이크, ${ball} 볼 입니다.`}]
                })
                setvalue('');
            }
            inputRef.current.focus();
        }
    }

    const onChangeInput = (e) => {
        setvalue(e.target.value);
    }
    
    return (
        <>
            <h1>{result}</h1>
            <form onSubmit={onSubmitForm}>
                <input ref={inputRef} maxLength={4} value={value} onChange={onChangeInput}/>
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
});
export default NumberBaseball; // import NumberBaseball