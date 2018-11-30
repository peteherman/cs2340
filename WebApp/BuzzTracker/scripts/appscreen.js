function init() {
    // var user = firebase.auth().currentUser;
    // if (user) {
    //     alert("signed in");
    // } else {
    //     window.location.href="./login.html";
    // }
}
window.onload = init;

function logout() {
    firebase.auth().signOut().then(function() {
        window.location.href="./index.html";
    }).catch(function(error) {
        console.log(error.message);
    });
}
