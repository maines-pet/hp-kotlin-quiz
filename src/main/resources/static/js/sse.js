$(document).ready(function () {
    $('#nav-next').click(function () {
        connect();
    });
});

var connect = function () {
    var source = new EventSource('/game/sse');

    source.addEventListener('open', function (e) {
        console.log(e)
        console.log("connected to sse")
    });

    source.addEventListener('message', function (evt) {
        console.log(evt.data);
        var message = JSON.parse(evt.data);

    }, false);

    source.addEventListener('error', function (e) {
        if (e.readyState == EventSource.CLOSED) {
            connect();
        }

    }, false);

};
