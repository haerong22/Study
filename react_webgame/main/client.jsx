const React = require('react');
const ReactDom = require('react-dom');

const WordRelay = require('./WordRelayHooks');

ReactDom.render(<WordRelay />, document.querySelector('#root'));