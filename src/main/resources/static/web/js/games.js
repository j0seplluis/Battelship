var data;

fetch("/api/games").then(function (response) {
    if (response.ok) {
        return response.json();
    }
    throw new Error(response.statusText);
}).then(function (json) {
    data = json;
    console.log(data);

    //functions to call
    gamesList(data);

}).catch(function (error) {
    console.log("Request failed: " + error.message);
});


function gamesList(data) {
    // console.log("hola, estic dins");
    // console.log(data.length);
    let games = document.getElementById("games");
    
    for (let i = 0; i < data.length; i++){
        // console.log("dins del for");
        var p1 = data[i].gamePlayer[0].player.userName;
        if(data[i].gamePlayer.length == 1){
            var p2 = "Waiting For An Opponent"
        }else {
            var p2 = data[i].gamePlayer[1].player.userName;
        }

        var date = new Date(data[i].create).toLocaleString();
        let li = document.createElement("li");
        li.innerHTML = `${date} ${p1} vs. ${p2}`;
        games.append(li);
    }
}


