class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(view:"/index")
		"500"(view:'/error')

        /*

        static mappings = {

		"/$categoryId?" {
            controller = "Category"
            action = [GET: 'getCategory', POST:'addCategory',PUT:'putCategory' ,DELETE: 'notAllowed']
        }

	}
         */
	}
}
