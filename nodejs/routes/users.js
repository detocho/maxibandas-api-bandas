exports.get = function (request, response){
	
	var userId = request.params.userId
	var json
	
    json = {
    	"id": userId,
        "name": "Gio Paz",
        "dealer_id": "123456",
        "email": "gpaz02@maxipublicator.com.mx",
        "password": "maxi123456345",
        "phone": "Casa (55) 55-2345-2534",
        "date_of_birth": "03-27-1986",
        "location_id": "15",
        "origin": "maxipublicator",
        "profile_picture": "http://www.google.com",
        "sex": "Hombre",
        "status": "active",
        "user_type": "admin"
	}
	
	response.json(200,json)
}