class UrlMappings {

    static mappings = {

        "/$bandId?" {
            controller = "Band"
            action = [GET: 'getBand', POST:'addBand',PUT:'putBand' ,DELETE: 'notAllowed']
        }

        "/search"{
            controller = "Band"
            action = [GET:'searchBand', POST:'notAllowed', PUT:'notAllowed', DELETE:'NotAllowed']
        }

    }

}
