const fs = require('fs');

// console.log('Start');

// let content = fs.readFileSync('./new', 'utf8');
// console.log(content);

// console.log('Finish');

console.log('Start');

fs.readFile('./new', 'utf8', (error, data) => {
  console.log(data);
});


console.log('Finish');