Vue.config.devtools = true

let vue = new Vue({
    el: "#myApp",
    data: {
        games: [],
        scores: [],
        ourData: {
            name: "",
            pwd: ""
        }
    },

    methods: {
        getLogIn: function () {
            fetch("/api/login", {
                credentials: 'include',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                method: 'POST',
                body: this.getBody(this.ourData)
            })
                .then(function (data) {
                    console.log('Request success: ', data);
                })
                .catch(error => {
                    console.log('Request failure: ', error);
                });
        },

        getLogOut: function () {
            fetch("/api/logout", {method: 'POST'})
                .then(data => {
                    console.log('Request success: ', data);
                })
                .catch(function (error) {
                    console.log('Request failure: ', error);
                });
        },

        getData: function () {
            fetch("/api/games/")
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

        sortedGamePlayers(gp) {
            return gp.sort(
                (a, b) => (Number(a.id) > Number(b.id))
                    ? 1
                    : (
                        (Number(b.id) > Number(a.id))
                            ? -1
                            : 0
                    )
            );
        },

        getBody(json) {
            var body = [];
            for (var key in json) {
                var encKey = encodeURIComponent(key);
                var encVal = encodeURIComponent(json[key]);
                body.push(encKey + "=" + encVal);
            }
            return body.join("&");
        }
    },

    computed: {},

    created: function () {
        this.getData();
        this.getScores();
    }

});