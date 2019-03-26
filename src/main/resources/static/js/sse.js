$(document).ready(function () {
    connect();
    $("#nav-next").click(function(){
        $.get("/game/quiz/next", function (data) {
            $("#question-wrapper").replaceWith(data);
        });
    });
});

var connect = function () {
    // var source = new EventSource('/game/sse');
    var source = new EventSource('../sse');

    source.addEventListener('open', function (e) {
        console.log(e);
        console.log("connected to sse");
    });

    source.addEventListener('message', function (evt) {
        console.log(evt.data);
        var message = JSON.parse(evt.data);
    }, false);

    source.addEventListener('new-player', function (evt) {
        console.log(evt.data);
        console.log(evt);
        console.log('New player has joined');
        $("#players").append(evt.data);
    }, false);

    source.addEventListener('error', function (e) {
        if (e.readyState === EventSource.CLOSED) {
            connect();
        }

    }, false);

};

function get() {
    
}