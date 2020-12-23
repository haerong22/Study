import React, { useState, useRef } from 'react';

const ResponseCheck = () => {
    const [state, setstate] = useState('waiting');
    const [message, setmessage] = useState('클릭해서 시작하세요');
    const [result, setresult] = useState([]);
    const timeout = useRef(null);
    const startTime = useRef();
    const endTime = useRef();

    const onClickScreen = () => {
        if( state === 'waiting') {
            setstate('ready');
            setmessage('초록색이 되면 클릭하세요.');
            timeout.current = setTimeout(() => {
                setstate('now');
                setmessage('지금 클릭');
                startTime.current = new Date();
            }, Math.floor(Math.random() * 1000) + 2000); //2~3초 랜덤
        } else if (state === 'ready') {
            clearTimeout(timeout.current)
            setstate('waiting');
            setmessage('너무 성급하시군요! 초록색이 된 후에 클릭하세요');
        } else if (state === 'now') { // 반응속도 체크
            endTime.current = new Date();
            setstate('waiting')
            setmessage('클릭해서 시작하세요')
            setresult((prevResult) => {
                return [...prevResult, endTime.current - startTime.current];
            }); 
        }
    }

    const onReset = () => {
        setresult([]);
    }

    const renderAverage = () => {
        return result.length === 0 
            ? null 
            : <>
                <div>평균 시간: {result.reduce((a, c) => a + c) / result.length}ms</div> 
                <button onClick={onReset}>리셋</button>
            </>    
    }

    return (
        <>
                <div
                    id="screen"
                    className={state}
                    onClick={onClickScreen}
                >
                    {message}   
                </div>
                {renderAverage()}
                
            </>
    )
}
export default ResponseCheck;