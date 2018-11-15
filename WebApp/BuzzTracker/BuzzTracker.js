const express = require('express');
const app = express();
const port = 5000; //port on server for app to run
const path = require('path');
const router = require('./routes.js'); //Router for different files

const dir = "."

app.get('/', function (req, res) {
  res.sendFile('index.html', { root: dir });
});

app.get('/register.html', function (req, res) {
  res.sendFile('register.html', { root: dir });
});


//app.set('view engine', 'pug') // allows pug to render html

//app.use('/routes', router);

app.listen(port);
