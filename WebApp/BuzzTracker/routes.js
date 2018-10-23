const express = require('express');
var router = express.Router();

router.get('/', function(req, res){
    res.render('index.pug');
});

router.post('/', function(req, res){
    res.send('POST route on things.');
});

router.get('/login', function(req, res){
    res.render('login.pug');
});
//export this router to use in our BuzzTracker.js
module.exports = router;
