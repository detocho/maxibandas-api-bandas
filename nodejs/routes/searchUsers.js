exports.post = function (request, response){
	
	var admin = request.params.admin

	var json

    if (admin == 'MB-ADMIN_123456KKAADPZ'){

        json = {
           "user_id": 5,
           "status":"active",
           "user_type":"normal"
        }
        response.json(201,json)


    }else{
	
        json ={
            "message":"not_found",
            "status":"400",
            "error":"invalid admin"
        }

        response.json(404, json)
       
    }
	
	
}