// let url = new URL('http://example.com/business/mart/item?category=14&id=2965')

// console.log(url.protocol)
// console.log(url.host)
// console.log(url.pathname)
// console.log(url.search)


// const http = require('http')

// const users = ['Tom', 'Andy', 'Jessica', 'Paul']

// const server = http.createServer((req, res) => {
  
//   if(req.url === '/') {
//     res.end('<h1>Welcome!</h1>')  
//   } else if(req.url === '/users') {
//     res.end(`<h1>${users}</h1>`)
//   } else if(req.url.split('/')[1] === 'users') {
//     let userIdx = req.url.split('/')[2]
//     let userName = users[userIdx - 1]
//     res.end(`<h1>${userName}</h1>`)
//   } else {
//     res.end('<h1>Page Not Available</h1>')
//   }
  
// })

// server.listen(3000)

const http = require('http');
const express = require('express');

const app = express();

const users = ['Tom', 'Andy', 'Jessica', 'Paul'];
  
app.get('/', (req,res) => {
  res.end('<h1>Welcome!</h1>')
});

app.get('/users', (req,res) => {
  res.end(`<h1>${users}</h1>`)
});

app.get('/users/:id', (req,res) => {
  const userName = users[req.params.id - 1]
  res.end(`<h1>${userName}</h1>`)
});

app.get('*', (req,res) => {
  res.end('<h1>Page Not Available</h1>')
});

app.listen(3000)