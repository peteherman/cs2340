function login() {

    var email = document.getElementById("email").value();
    var pass = document.getElementById("password").value();
    firebase.auth().signInWithEmailAndPassword(email, pass);

    firebase.auth().onAuthStateChanged(function(user) {
        if (user) {
            alert("Logged in!");
        } else {
            alert("Failed");
        }
    });
}
