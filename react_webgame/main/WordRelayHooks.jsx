const React = require('react');
const { useState, useRef } = React;

const WordRelayHooks = () => {
    const [word, setword] = useState('리액트!!');
    const [value, setvalue] = useState('');
    const [result, setresult] = useState('');
    const [words, setwords] = useState('리액트');
    const inputRef = useRef(null);

    const onSubmitForm = (e) => {
        e.preventDefault();
        if (word[word.length - 1] === value[0]) {
            setresult('딩동댕');
            setword(value);
            setwords(words + ' => ' + value);
            setvalue('');
            
            inputRef.current.focus();
        } else {
            setresult('땡');
            setvalue('');
            inputRef.current.focus();
        }
    }

    const onChangeInput = (e) => {
        this.setState({ value: e.target.value })
    }

    return (
        <>
            <div>{word}</div>
            <form onSubmit={onSubmitForm}>
                <input ref={inputRef} value={value} onChange={onChangeInput} />
                <button>입력!</button>
            </form>
            <div>{result}</div>
            <div>{words}</div>
        </>
    )
}

module.exports = WordRelay;