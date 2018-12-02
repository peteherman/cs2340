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
    //If passwords match and are appropiate length, create firebase user
    try {
        firebase.auth().createUserWithEmailAndPassword(email, pass).catch(function(error) {
            alert(error.message);
        });
    } catch (error) {
        alert(error.message);
    }
    // firebase.auth().createUserWithEmailAndPassword(email, pass).catch(function(error) {
    //     alert("Something went wrong: " + error.message);
    //     //alert(error.message);
    //     window.location.href="./register.html";
    // });

    firebase.auth().onAuthStateChanged(function(user) {
        if(user) {
            user.userType = document.getElementById("type_spinner").value;
            //window.location.href="./appscreen.html"
            alert("Registered Successfully!");
            window.location.href="./appscreen.html";
        }
    })
}

function doNothing() {}
