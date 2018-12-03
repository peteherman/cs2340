function login() {
    var email = document.getElementById("email").value;
    var pass = document.getElementById("password").value;

    firebase.auth().signInWithEmailAndPassword(email, pass).catch(function(error) {
        // Handle Errors here.
        var errorCode = error.code;
        var errorMessage = error.message;
        document.getElementById("error_message").innerHTML = errorMessage;
    });

    firebase.auth().onAuthStateChanged(function(user) {
        if (user) {
            // User is signed in.
            window.location.href="./appscreen.html";
        } else {
            //No user is signed in
        }
    });
}
