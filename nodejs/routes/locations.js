exports.get = function (request, response){

    var locationId = request.params.locationId
    var json

    if (locationId == "MX3MB00"){
        json ={
            "message": "The categoryId not found",
            "status": 404,
            "error": "not_found"
        };

        response.json(404,json)

    }else {

        json = {
				"location_id": ""+locationId+"",
				"name": "Ampliación San Pedro Xalpa",
				"level": "neighborhood",
				"parent_location": [{
				    "location_id": "CTY767",
				    "name": "Azcapotzalco",
				    "level": "city"
				}, {
				    "location_id": "EST15",
				    "name": "Distrito Federal",
				    "level": "state"
				}, {
				    "location_id": "MX",
				    "name": "México",
				    "level": "country"
				}],
				"children_locations": []
			};

        response.json(200,json)
    }


}