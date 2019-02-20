Vue.config.devtools = true;

let vue = new Vue({
  el: "#myApp",
  data: {
    letters: ["", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"],
    numbers: ["", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"],
    gp: "",
    gameData: "",
    gamePlayer_1: "",
    gamePlayer_2: "",
    salvoLocation: "",
    shipLength: "",
    shipType: "",
    placedShip: 0,
    shipSelected: "",
    shipPosition: [{
        type: "Patrol",
        shipLocation: [],
        length: 2
      },
      {
        type: "Destroyer",
        shipLocation: [],
        length: 3
      },
      {
        type: "Submarine",
        shipLocation: [],
        length: 3
      },
      {
        type: "Battleship",
        shipLocation: [],
        length: 4
      },
      {
        type: "Carrier",
        shipLocation: [],
        length: 5
      }
    ],
    active: true
  },

  created: function () {
    this.getURL();
    this.getData();
  },

  methods: {
    getURL: function () {
      var url = new URL(window.location.href);
      this.gp = url.searchParams.get("gp");
      return url.searchParams.get("gp");
    },

    getData: function () {
      fetch("/api/game_view/" + this.getURL())
        .then(response => {
          console.log(response);

          return response.json();
        })
        .then(json => {
          if (json.error) {
            alert(json.error);
            window.location = "/web/games.html";
          } else {
            console.log(json);
            this.gameData = json.owner;
            console.log(this.gameData);

            //functions to call
            this.showShips(this.gameData);
            this.showPlayers(this.gameData);
            this.showOpponentSalvoes(this.gameData);
            this.showMySalvoes(this.gameData);
          }
        })
        .catch(function (error) {
          console.log("Request failed: ", error);
        });
    },

    sendShips: function () {
      console.log("hello");
      fetch("/api/games/players/" + this.getURL() + "/ships", {
          credentials: "include",
          body: JSON.stringify(vue.shipPosition),
          headers: {
            "Content-Type": "application/json"
          },
          method: "POST"
        })
        .then(data => {
          console.log("Request success: ", data);
          return data.json();
        })
        .then(json => {
          console.log(json);
        })
        .catch(function (error) {
          console.log("Request failure: ", error);
          window.location = "/web/game.html?gp=" + json.gamePlayer_id;
        });
    },

    showShips: function (gameData) {
      // console.log(gameData.ships.length);
      for (let i = 0; i < gameData.ships.length; i++) {
        for (let j = 0; j < gameData.ships[i].shipLocation.length; j++) {
          document
            .getElementById(gameData.ships[i].shipLocation[j])
            .classList.add("ships");
        }
      }
    },

    showPlayers: function (gameData) {
      for (let i = 0; i < gameData.gamePlayer.length; i++) {
        if (gameData.gamePlayer[i].id == this.gp) {
          this.gamePlayer_1 = gameData.gamePlayer[i].player.userName;
        } else {
          this.gamePlayer_2 = gameData.gamePlayer[i].player.userName;
        }
        if (gameData.gamePlayer.length == 1) {
          this.gamePlayer_2 = "Waiting for an Opponent";
        }
      }
    },

    showOpponentSalvoes: function (gameData) {
      gameData.Opponent.forEach(element => {
        element.salvoLocation.forEach(loc => {
          let cell = document.getElementById(loc);

          cell.innerHTML = element.turn;

          if (cell.classList.contains("ships")) {
            cell.classList.add("hit");
          } else {
            cell.classList.add("miss");
          }
        });
      });
    },

    showMySalvoes: function (gameData) {
      gameData.salvo.forEach(element => {
        element.salvoLocation.forEach(loc => {
          let cell = document.getElementById(loc + "o");
          cell.innerHTML = element.turn;

          cell.classList.add("miss");
        });
      });

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

    getLocationsByshiptType() {
      this.shipType = event.target.innerHTML;

      this.shipLength = this.shipPosition.find(
        ship => ship.type == event.target.innerHTML
      ).length;
      this.active = true;
      this.shipSelected = event.target; //ALL THE ELEMENT SAVED
    },

    mouseOver() {
      let letter = event.target.id.substr(0, 1);
      let number = event.target.id.substr(1, 2);

      if (this.active == true) {
        for (let i = 0; i < this.shipLength; i++) {
          let id = letter + (Number(number) + i);
          document.getElementById(id).classList.add("pointerOn");
        }
        document
          .getElementById(event.target.id)
          .addEventListener("click", this.setShip);
      } else {}
    },

    mouseLeave() {
      let letter = event.target.id.substr(0, 1);
      let number = event.target.id.substr(1, 2);

      for (let i = 0; i < this.shipLength; i++) {
        let id = letter + (Number(number) + i);
        document.getElementById(id).classList.remove("pointerOn");
      }
    },

    setShip() {
      let letter = event.target.id.substr(0, 1);
      let number = event.target.id.substr(1, 2);

      let ship = this.shipPosition.find(ship => ship.type == this.shipType);
      for (let i = 0; i < this.shipLength; i++) {
        let id = letter + (Number(number) + i);
        ship.shipLocation.push(id);
        document.getElementById(id).classList.add("ships");
      }
      this.active = false;
      this.shipSelected.style.display = "none"
    }
  }
});