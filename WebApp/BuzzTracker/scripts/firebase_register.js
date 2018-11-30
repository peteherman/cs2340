function register() {
    //configureFirebase();
    var email = document.getElementById("email").value;
    const pass = document.getElementById("password").value;
    const confirm_pass = document.getElementById("confirm_pass").value;
    //Ensure passwords match

    if (pass.length < 6) {
        alert("Error: Password must be longer than 8 characters");
        return false;
    }
    if (pass.localeCompare(confirm_pass) != 0) {
        alert("Error: Passwords did not match");
        return false;
    }
    alert("Calling firebase");
    //If passwords match and are appropiate length, create firebase user
    firebase.auth().createUserWithEmailAndPassword(email, pass).catch(function(error) {
        alert("Something went wrong!");
        alert(error.message);
    });
    alert("Called firebase");
}
