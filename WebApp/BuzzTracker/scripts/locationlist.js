//function queryFB() {
var db = firebase.database();
var locationRef = db.ref("Locations");
var locationNames = [];
var addresses = [];
var phonenumber = [];
var lats = [];
var longs = [];
var types = [];
var websites = [];
    //console.log(locationRef.child('locations'));
locationRef.orderByKey().once('value', function(snapshot) {
     createLinks(snapshot);
     snapshot.forEach(function(data) {
         var usable = data.val();
         locationNames.push(usable.name);
         addresses.push(usable.address);
         phonenumber.push(usable.phoneNum);
         lats.push(usable.latitude);
         longs.push(usable.longitdue);
         types.push(usable.type);
         websites.push(usable.website);
     });
});

//}
function main() {
    //var locationInfo = queryFB();
    //createLinks(locationInfo);
}
//window.onload=main;


function createLinks(snapshot) {
    //var locationNames = [];
    var ul = document.getElementById("location_list");
    var counter = 0;
    snapshot.forEach(function(data) {
        var li = document.createElement("li");
        var link = document.createElement("a");

        link.textContent = data.val().name;
        link.setAttribute("href", "#");
        link.setAttribute("onclick", "buildTable(" + counter + ")");

        li.appendChild(link);
        ul.appendChild(li);

        counter++;
    });
}

function buildTable(index) {
    //Set Title
    document.getElementById("detail_title").innerHTML = locationNames[index];

    //Clear out old list
    var detailList = document.getElementById("detail_list");
    detailList.innerHTML = "";

    
}
