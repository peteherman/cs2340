const express = require('express');
const app = express();
const port = 5000; //port on server for app to run

const router = require('./routes.js'); //Router for different files

app.set('view engine', 'pug') // allows pug to render html

app.use('/routes', router);

app.listen(port);
