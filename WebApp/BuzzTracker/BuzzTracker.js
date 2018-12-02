const express = require('express');
const app = express();
const port = 5000; //port on server for app to run
const path = require('path');
const router = require('./routes.js'); //Router for different files

//Set up firebase
var firebase = require('firebase');
var config = {
    apiKey: "AIzaSyCbzZ4-uziDWO_FTlcEWL0vgIZtCorCN4c",
    authDomain: "buzztracker-ed761.firebaseapp.com",
    databaseURL: "https://buzztracker-ed761.firebaseio.com",
    projectId: "buzztracker-ed761",
    storageBucket: "buzztracker-ed761.appspot.com",
    messagingSenderId: "506881197326"
};
firebase.initializeApp(config);

//Include scripts
var scripts = require("./scripts/firebase_register.js");

const dir = "."

app.get('/', function (req, res) {
  res.sendFile('index.html', { root: dir });
});

app.get('/index.html', function (req, res) {
  res.sendFile('index.html', { root: dir });
});


app.post('/login.html', function(req, res) {
    res.sendFile('login.html', { root: dir });
});

app.get('/login.html', function(req, res) {
    res.sendFile('login.html', { root: dir });
});


app.get('/register.html', function (req, res) {
  res.sendFile('register.html', { root: dir });
});

app.post('/register.html', function (req, res) {
  res.sendFile('register.html', { root: dir });
});

app.get('/appscreen.html', function (req, res) {
    res.sendFile('appscreen.html', { root: dir });
});

app.get('/locationlist.html', function (req, res) {
    res.sendFile('locationlist.html', { root: dir });
});

app.get('/detailedlocation.html', function (req, res) {
    res.sendFile('detailedlocation.html', { root: dir });
});

app.get('/scripts/login.js', function (req, res) {
    res.sendFile('/scripts/login.js', { root: dir });
});

app.get('/scripts/firebase_register.js', function (req, res) {
    res.sendFile('/scripts/firebase_register.js', { root: dir });
});

app.get('/scripts/firebase_login.js', function (req, res) {
    res.sendFile('/scripts/firebase_login.js', { root: dir });
});

app.get('/scripts/appscreen.js', function (req, res) {
    res.sendFile('/scripts/appscreen.js', { root: dir });
});

app.get("/search.html", function (req, res) {
    res.sendFile("/search.html", { root: dir });
});

app.get('/scripts/locationlist.js', function (req, res) {
    res.sendFile("/scripts/locationlist.js", { root: dir });
});


//app.set('view engine', 'pug') // allows pug to render html

//app.use('/routes', router);

app.listen(port);
