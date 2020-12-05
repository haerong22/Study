const EventEmitter = require('events');

const myEmitter = new EventEmitter();
const myEmitter2 = new EventEmitter();

const obj = {
  type: 'text',
  data: 'Hello CodeIt',
  date: '2020-09-01'
}

myEmitter.on('test', (info) => {
  console.log(info)
  // console.log(arg2)
  // console.log(arg3)
});


myEmitter.emit('test', obj);