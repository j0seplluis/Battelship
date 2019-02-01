Vue.config.devtools = true

let vue = new Vue({
    el: "#myApp",
    data: {
        games: [],
        scores: [],
        ourData: {
            name: "",
            pwd: ""
        },
        player: ""
    },

    methods: {
        getSignUp: function () {
            fetch("/api/players", {
                    credentials: 'include',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    method: 'POST',
                    body: this.getBody(this.ourData)
                })
                .then(function (data) {
                    console.log('Request success: ', data);
                    vue.getError(data.status);

                })
                .catch(error => {
                    console.log('Request failure: ', error);
                });
        },

        getError: function (status) {
            //BAD_RQUEST
            if (status == 400) {
                alert("Something is missing, please try again")

            };
            //UNAUTHORIZED
            if (status == 401) {
                alert("Something went wrong, please try again")
            }
            //CONFLICT
            if (status == 409) {
                alert("Email already exist, please try again")
            };
            //CREATED
            if (status == 201) {
                this.getLogIn();
            }
        },

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
                    vue.getError(data.status);
                    window
                        .location
                        .reload();
                })
                .catch(error => {
                    console.log('Request failure: ', error);
                });
        },

        getLogOut: function () {
            fetch("/api/logout", {
                    method: 'POST'
                })
                .then(data => {
                    console.log('Request success: ', data);
                    window
                        .location
                        .reload();
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
                    this.games = json.games;
                    this.player = json.player;
                    console.log(json);

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
                (a, b) => (Number(a.id) > Number(b.id)) ?
                1 :
                (
                    (Number(b.id) > Number(a.id)) ?
                    -1 :
                    0
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