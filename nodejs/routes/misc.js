exports.ping = function (request, response) {
    response.set('Access-Control-Allow-Origin', '*');
    var json = {
        message:"pong"
    };
    response.json(200, json);
}

exports.notFound = function (request, response) {
    response.set('Access-Control-Allow-Origin', '*');
    var json = {
        message:"Resource Not Found",
        error:"not_found",
        status:404,
        cause:{ }
    };
    response.json(404, json);
}