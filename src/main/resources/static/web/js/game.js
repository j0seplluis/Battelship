let vue = new Vue({
    el: '#myApp',
    data: {
        letters: [
            "",
            "A",
            "B",
            "C",
            "D",
            "E",
            "F",
            "G",
            "H",
            "I",
            "J"
        ],
        numbers: [
            "",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10"
        ],
        gp: "",
        gameData: "",
        gamePlayer_1: "",
        gamePlayer_2: "",
        salvoLocation: ""
    },

    methods: {
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
                    console.log(response);


                    return response.json();

                })
                .then(json => {
                    if (json.error) {
                        alert(json.error);
                        window.location = "/web/games.html"
                    } else {

                        console.log(json);
                        this.gameData = json.owner;
                        console.log(this.gameData);

                        //functions to call
                        this.showShips(this.gameData);
                        this.showPlayers(this.gameData);
                        this.showSalvoes(this.gameData);
                    }
                })
                .catch(function (error) {
                    console.log("Request failed: ", error);
                })
        },

        showShips: function (gameData) {
            console.log(gameData.ships.length);
            for (let i = 0; i < gameData.ships.length; i++) {
                for (let j = 0; j < gameData.ships[i].shipLocation.length; j++) {
                    document
                        .getElementById(gameData.ships[i].shipLocation[j])
                        .classList
                        .add("ships");
                }
            }
        },

        showPlayers: function (gameData) {
            for (let i = 0; i < gameData.gamePlayer.length; i++) {
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
        },

        showSalvoes: function (gameData) {
            //Opponent shots
            gameData
                .Opponent
                .forEach(element => {
                    element
                        .salvoLocation
                        .forEach(loc => {
                            let cell = document.getElementById(loc);

                            cell.innerHTML = element.turn;

                            if (cell.classList.contains("ships")) {
                                cell
                                    .classList
                                    .add("hit");
                            } else {
                                cell
                                    .classList
                                    .add("miss");
                            }
                        })
                });

            //MyShots
            gameData
                .salvo
                .forEach(element => {
                    element
                        .salvoLocation
                        .forEach(loc => {
                            let cell = document.getElementById(loc + "o");
                            cell.innerHTML = element.turn;

                            cell
                                .classList
                                .add("hit")
                        })
                });
        },
        // TheirShots 
        //for (let i = 0; i < gameData.Opponent.length; i++) {     
        //    for (let j = 0; j < gameData.Opponent[i].salvoLocation.length; j++) {         
        //          document.getElementById(gameData.Opponent[i].salvoLocation[j]).innerHTML =
        //          this.gameData.Opponent[i].turn;         
        //          if (document.getElementById(gameData.Opponent[i].salvoLocation[j]).classList.contains("ships")){            
        //               document.getElementById(gameData.Opponent[i].salvoLocation[j]).classList.add("hit");  
        //          } else {              
        //              document.getElementById(gameData.Opponent[i].salvoLocation[j]).classList.add("miss");
        //          }} 
        //      } 
        // My shots 
        // for (leti = 0; i < gameData.salvo.length; i++) {     
        //     for (let j = 0; j <gameData.salvo[i].salvoLocation.length; j++) {         
        //          document.getElementById(gameData.salvo[i].salvoLocation[j] + "o").innerHTML =
        //          this.gameData.salvo[i].turn;         
        //      document.getElementById(gameData.salvo[i].salvoLocation[j] + "o").classList.add("hit");  
        // } }
    },

    created: function () {
        this.getURL();
    }

});