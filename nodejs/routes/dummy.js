exports.getDummyInfo = function (request, response) {
    response.set('Access-Control-Allow-Origin', '*');
    var json = {
        dummy:["mango","manzana","pera"]
    };
    response.json(200, json);
}