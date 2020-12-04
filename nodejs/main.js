const fs = require('fs');
const os = require('os');


let filelist = fs.readdirSync('.');
console.log(filelist);

fs.writeFileSync('new', 'Hello Node.js');

console.log(os.cpus());