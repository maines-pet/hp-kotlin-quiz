var end = false;

$(document).ready(function () {
    connect();

    $("#nav-next").click(getQuestion);
    $(".start").click(function(){
       $(".start").hide(0, getQuestion);
    });
});

function getQuestion() {
    $(".question-wrapper").remove();

    $.get("/game/quiz/" + (end ? "result" : "next"), function (data) {
        $("#grid-wrapper").append(data);
    });

}
var connect = function () {
    // var source = new EventSource('/game/sse');
    var source = new EventSource('../sse');

    source.addEventListener('open', function (e) {
        console.log(e);
        console.log("connected to sse");
    });

    source.addEventListener('message', function (evt) {
        console.log(evt.data);
        // var message = JSON.parse(evt.data);
        var message = evt.data;
        if (message === "end"){
            end = true;

        }
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