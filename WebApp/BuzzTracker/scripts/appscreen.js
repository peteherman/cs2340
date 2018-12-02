function init() {
    firebase.auth().onAuthStateChanged(function(user) {
        if (user) {
            createLinks(user);
        } else {
            // alert("You need to be signed in "
            //     + "to access this page.");
            window.location.href="./index.html";
        }
    });
}
window.onload = init;

function logout() {
    firebase.auth().signOut().then(function() {
        window.location.href="./index.html";
    }).catch(function(error) {
        console.log(error.message);
    });
}

function createLinks(user) {
    console.log("creating links");

    var ul = document.getElementById("page_links");
    var locationElement = document.createElement("li");
    var locationLink = document.createElement("a");
    var searchElement = document.createElement("li");
    var searchLink = document.createElement("a");

    locationLink.textContent = "Locations";
    locationLink.setAttribute("href", "./locationlist.html");
    locationElement.appendChild(locationLink);
    ul.appendChild(locationElement);

    searchLink.textContent = "Search";
    searchLink.setAttribute("href", "./search.html");
    searchElement.appendChild(searchLink);
    ul.appendChild(searchElement);
}
