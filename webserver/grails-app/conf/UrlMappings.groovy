class UrlMappings {

    static mappings = {

        "/$bandId?" {
            controller = "Band"
            action = [GET: 'getBand', POST:'notAllowed',PUT:'notAllowed' ,DELETE: 'notAllowed']
        }

    }

}
