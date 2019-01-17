let vue = new Vue({
    el: '#myApp',
    data: {
        letters : ["", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"],
        numbers : ["", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"], 
        gp:"",
        gameData:"",
        gamePlayer_1:"",
        gamePlayer_2:"",
    },

methods : {
    getURL: function () {
        var url = new URL(window.location.href);
        this.gp = url
            .searchParams
            .get("gp");
        this.getData();
    },

    getData: function () {
        fetch("/api/game_view/" + this.gp)
            .then(response => {
                if (response.ok) {
                    return response.json();
                }
                throw new Error(response.statusText);
            })
            .then(json => {
                this.gameData = json;
                console.log(this.gameData);

                //functions to call
                this.showShips(this.gameData);
                this.showPlayers(this.gameData);

            })
            .catch(function (error) {
                console.log("Request failed: " + error.message);
            })
        },

    showShips: function (gameData) {
        console.log(gameData.ships.length);
        for (let i = 0; i < gameData.ships.length; i++) {

            for (let j = 0; j < gameData.ships[i].shipLocation.length; j++) {
                // console.log(gameData.ships[i].type);
                document
                    .getElementById(gameData.ships[i].shipLocation[j])
                    .className += gameData
                    .ships[i]
                    .type;
            }
        }
    },

    showPlayers: function (gameData) {
        // console.log("hi!!!")
        for (let i = 0; i < gameData.gamePlayer.length; i++) {
            // console.log("I'm here!!")
            if (gameData.gamePlayer[i].id == this.gp) {
                this.gamePlayer_1 = gameData
                    .gamePlayer[i]
                    .player
                    .userName;
            } else {
                this.gamePlayer_2 = gameData
                    .gamePlayer[i]
                    .player
                    .userName;
            }
            if (gameData.gamePlayer.length == 1) {
                this.gamePlayer_2 = "Waiting for an Opponent"
            }
        }
    }

},
created: function () {
    this.getURL();
}

});
