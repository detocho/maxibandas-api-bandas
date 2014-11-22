exports.get = function (request, response){

    var categoryId = request.params.categoryId
    var json

    if (categoryId == "MX3"){
        json ={
            "message": "The categoryId not found",
            "status": 404,
            "error": "not_found"
        };

        response.json(404,json)

    }else {

        json = {
                "category_id": ""+categoryId+"",
                "name": "Banda",
                "parent_category": [{
                "category_id": "MX1",
                "name": "Géneros musicales",
                "status": "active"
                }, {
                    "category_id": "MX",
                    "name": "México",
                    "status": "active"
                }],
                "children_categories": []
        };

        response.json(200,json)
    }


}