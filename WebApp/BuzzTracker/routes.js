const express = require('express');
var router = express.Router();

router.get('/', function(req, res){
    res.render('index.pug');
});

router.post('/', function(req, res){
    res.send('POST route on things.');
});

//export this router to use in our BuzzTracker.js
module.exports = router;