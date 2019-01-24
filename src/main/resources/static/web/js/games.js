// var data;
// fetch("/api/games")
//     .then(function (response) {
//         if (response.ok) {
//             return response.json();
//         }
//         throw new
//         Error(response.statusText);
//     })
//     .then(function (json) {
//         data = json;
//         console.log(data);
//         functions to call gamesList(data);
//     })
//     .catch(function (error) {
//         console.log("Request failed: " + error.message);
//     });
// function gamesList(data) {
//     let games = document.getElementById("games");
//     for (let i = 0; i < data.length; i++) {
//         var p1 = data[i]
//             .gamePlayer[0].player.userName;
//         if (data[i].gamePlayer.length == 1) {
//             var p2 = "Waiting..."
//         } else {
//             var p2 = data[i].gamePlayer[1].player.userName;
//         }
//         var date = new Date(data[i].create).toLocaleString();
//         let li = document.createElement("li");
//         li.innerHTML = `${date} ${p1} vs. ${p2}`;
//         games.append(li);
//     }
// }
Vue.config.devtools = true

let vue = new Vue({
    el: "#myApp",
    data: {
        games: [],
        scores:[],
    },

    methods: {
        getData: function () {
            fetch("/api/games")
                .then(response => {
                    if (response.ok) {
                        return response.json();
                    }
                    throw new Error(response.statusText);
                })
                .then(json => {
                    this.games = json;
                    console.log(this.games);

                    //functions to call

                })
                .catch(function (error) {
                    console.log("Request failed: " + error.message);
                })
            },
            
        getScores: function () {
            fetch("/api/leaderboard")
            .then(response => {
                if (response.ok) {
                    return response.json();
                }
                throw new Error(response.statusText);
            })
            .then(json => {
                this.scores = json;
                console.log(this.scores);

                //functions to call

            })
            .catch(function (error) {
                console.log("Request failed: " + error.message);
            })
        },
        sortedGamePlayers(gp){
            return gp.sort((a,b) => (Number(a.id) > Number(b.id)) ? 1 : ((Number(b.id) > Number(a.id)) ? -1 : 0));
        }
        
    },

    computed: {
        
    },

    created: function () {
        this.getData();
        this.getScores();
    }

});