package api.bands

import java.text.MessageFormat
import org.apache.ivy.plugins.conflict.ConflictManager
import grails.converters.*
import bands.exceptions.NotFoundException
import bands.exceptions.ConflictException
import bands.exceptions.BadRequestException

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import api.bands.ValidAccess

import javax.servlet.http.HttpServletResponse

class BandService {

    static transactional = 'mongo'

    def restService
    def validAccess = new ValidAccess()

    def get(def params){

        Map jsonResult = [:]

        def bandId = params.bandId

        if (!bandId){

            throw new NotFoundException("You must provider de bandId")
        }

        def bands = Band.findById(bandId)

        if (!bands){
            throw  new NotFoundException("The bandId = "+bandId+" not found")
        }


        jsonResult =  getResult(bands)
        jsonResult



    }

    def add(def params, def jsonBand){

        Map jsonResult = [:]

        def responseMessage = ''

        if (!params.access_token){

            throw new BadRequestException ("You must provider de access_token")

        }

        def access_token = validAccess.validAccessToken(params.access_token)

        def user_id = params.access_token.split('_')[2]

        def category = getCategory(jsonBand?.category_id)
        def location = getLocation(jsonBand?.location_id)
        def arrayPictures = validPictures(jsonBand?.pictures)

        //definimos la construccion de titulo en base a la categoria y la location
        // categoryNAme name banda locationEstate

        def title = category.name+" "+jsonBand?.name+" "+getStateLocation(location)


        Band newBand = new Band(

                categoryId          : jsonBand?.category_id,
                name                : jsonBand?.name,
                title               : title,
                priceMin            : jsonBand?.price_min,
                priceMax            : jsonBand?.price_max,
                currencyType        : jsonBand?.currency_type,
                locationId          : jsonBand?.location_id,
                serviceLocations    : jsonBand?.service_locations,
                eventsTypes         : jsonBand?.events_types,
                managerId           : user_id,
                webPage             : jsonBand?.web_page,
                pictures            : jsonBand?.pictures,
                urlVideos           : jsonBand?.url_videos,
                description         : jsonBand?.description,
                typeItem            : jsonBand?.type_item,
                attributes          : jsonBand?.attributes ? jsonBand?.attributes : [],
                status              : 'active'
        )

        if (!newBand.validate()){
            newBand.errors.allErrors.each {
                responseMessage += MessageFormat.format(it.defaultMessage, it.arguments) + " "
            }

            throw new BadRequestException(responseMessage)
        }

        newBand.save()

        jsonResult =  getResult(newBand)
        jsonResult
    }

    def put(def params, def jsonBand){

        Map jsonResult = [:]
        def responseMessage

        if (!params.bandId){
            throw new NotFoundException("You must provider id band")
        }

        def obteinedBand = Band.findById(params.bandId)

        if (!obteinedBand){
            throw new NotFoundException("The Band whit id =" +params.bandId+" not found")
        }

        if (!params.access_token){

            throw new BadRequestException ("You must provider de access_token")

        }

        if (jsonBand?.manager_id){
            throw new BadRequestException("Error: Parameter manager_id not is modifiable")
        }

        if (jsonBand?.catalog_id){

            throw new BadRequestException("Error: Parameter category_id is not modifiable")
        }

        if (jsonBand?.title){

            throw new BadRequestException("Error: Parameter title is not modifiable")
        }

        def access_token = validAccess.validAccessToken(params.access_token)

        def user_id = params.access_token.split('_')[2]

        if (user_id != obteinedBand.managerId){
            throw new BadRequestException("Error: Your token  invalid for this publication")
        }

        def category = getCategory(jsonBand?.category_id)
        def location = getLocation(jsonBand?.location_id)
        def arrayPictures = validPictures(jsonBand?.pictures)

        def title = category.name+" "+jsonBand?.name+" "+getStateLocation(location)

        //obteinedBand.categoryId          = jsonBand?.category_id
        obteinedBand.name                = jsonBand?.name
        obteinedBand.title               = title
        obteinedBand.priceMin            = jsonBand?.price_min
        obteinedBand.priceMax            = jsonBand?.price_max
        obteinedBand.currencyType        = jsonBand?.currency_type
        obteinedBand.locationId          = jsonBand?.location_id
        obteinedBand.serviceLocations    = jsonBand?.service_locations
        obteinedBand.eventsTypes         = jsonBand?.events_types
        //obteinedBand.managerId           = jsonBand?.manager_id
        obteinedBand.webPage             = jsonBand?.web_page
        obteinedBand.pictures            = jsonBand?.pictures
        obteinedBand.urlVideos           = jsonBand?.url_videos
        obteinedBand.description         = jsonBand?.description
        obteinedBand.typeItem            = jsonBand?.type_item
        obteinedBand.attributtes         = jsonBand?.attributes ? jsonBand?.attributes : []
        obteinedBand.status              = 'active'
        obteinedBand.dateUpdate          = new Date()

        if (!obteinedBand.validate()){
            obteinedBand.errors.allErrors.each {
                responseMessage += MessageFormat.format(it.defaultMessage, it.arguments) + " "
            }
            throw new BadRequestException(responseMessage)
        }

        obteinedBand.save()

        jsonResult =  getResult(obteinedBand)
        jsonResult


    }

    def searchBand(params){

        Map jsonResult = [:]
        def result
        def results = []

        def SEARCH_PARAMS_MAP = [

                manager_id:"managerId",
                location_id:"locationId",
                //events_type:"eventsTypes"
                status: "status",
                date_registration_from:"dateRegistration",
                date_resgitration_to:"dateRegistration",
                date_activation_from:"dateActivation",
                date_activation_to:"dateActivation",
                type_item:"typeItem"
        ]

        def date_registration_to
        def date_registration_from
        def date_activation_from
        def date_activation_to

        def queryMap = [:]


        params.each{ key , value ->

            def newKey = SEARCH_PARAMS_MAP[key]

            if(newKey){
                queryMap[newKey]=value
            }
        }

        if (!queryMap){

            jsonResult.total = 0
            jsonResult.bands = []
            return jsonResult
        }

        def bandCriteria = Band.createCriteria()

        result = bandCriteria.list(){

            params.each{ key, value ->

                def newKey = SEARCH_PARAMS_MAP[key]

                if (newKey && (newKey!='dateRegistration') && newKey!='dateActivation'){
                    eq(newKey, value)
                }
            }

            if (params.date_registration_from){

                try {
                    date_registration_from = ISODateTimeFormat.dateTimeParser().parseDateTime(params.date_registration_from).toDate()
                    ge("dateRegistration", date_registration_from)
                }catch(Exception e){
                    throw new BadRequestException("Wrong date format in date_registration_from. Must be ISO json format")
                }
            }

            if (params.date_registration_to){

                try{
                    date_registration_to = ISODateTimeFormat.dateTimeParser().parseDateTime(params.date_registration_to).toDate()
                    le("dateRegistration",date_registration_to)
                }catch(Exception e){
                    throw new BadRequestException("Wrong date format in date_registration_to. Must be ISO json format")
                }
            }

            if(params.date_registration_from && params.date_registration_to){

                if (date_registration_to < date_registration_from){
                    throw new BadRequestException("Invalid date range, date  date_registration_to must be greater than the  date date_registration_from")
                }
            }


            if (params.date_activation_from){

                try {
                    date_activation_from = ISODateTimeFormat.dateTimeParser().parseDateTime(params.date_activation_from).toDate()
                    ge("dateActivation", date_activation_from)
                }catch(Exception e){
                    throw new BadRequestException("Wrong date format in date_activation_from. Must be ISO json format")
                }
            }

            if (params.date_ractivation_to){

                try{
                    date_activation_to = ISODateTimeFormat.dateTimeParser().parseDateTime(params.date_activation_to).toDate()
                    le("dateActivation",date_activation_to)
                }catch(Exception e){
                    throw new BadRequestException("Wrong date format in date_activation_to. Must be ISO json format")
                }
            }

            if(params.date_activation_from && params.date_activation_to){

                if (date_activation_to < date_activation_from){
                    throw new BadRequestException("Invalid date range, date  date_activation_to must be greater than the  date date_activation_from")
                }
            }
        }

        // TODO añadimos la data que debemos mostrar completa


        result.each{


            results.add(

                    band_id              : it.id,
                    category_id          : it.categoryId,
                    name                 : it.name,
                    title                : it.title,
                    price_min            : it.priceMin,
                    price_max            : it.priceMax,
                    currency_type        : it.currencyType,
                    location_id          : it.locationId,
                    service_locations    : it.serviceLocations,
                    events_types         : it.eventsTypes,
                    manager_id           : it.managerId,
                    web_page             : it.webPage,
                    pictures             : it.pictures,
                    url_videos           : it.urlVideos,
                    description          : it.description,

                    type_item            : it.typeItem,
                    status               : it.status,

                    date_registration    : it.dateRegistration,
                    date_update          : it.dateUpdate,
                    date_expired         : it.dateExpired,
                    date_activation      : it.dateActivation,
                    date_renovation      : it.dateRenovation,
                    date_deleted         : (it.dateDeleted) ? it.dateDeleted : "null",
                    attributes           : it.attributes ? it.attributtes: []
            )
        }

        jsonResult.total = results.size()
        jsonResult.results = results

        jsonResult

    }

    def getAttributes(def attributes){
        //TODO implementar la lectura de attributos
    }

    def getStateLocation(def location){

        def result = ''
        location.parent_location.each{
            if(it.level == 'state'){
                result = it.name
            }
        }

        result
    }

    def getLocation(def locationId){

        def result = restService.getResource("/locations/"+locationId+"/")

        if(result.status != HttpServletResponse.SC_OK){
            throw new BadRequestException("The location_id = "+locationId+" not found in categories api")
        }

        result.data


    }

    def getCategory(def categoryId){

        def result = restService.getResource("/categories/"+categoryId+"/")

        if(result.status != HttpServletResponse.SC_OK){
            throw new BadRequestException("The category_id = "+categoryId+" not found in categories api")
        }

        result.data

    }

    def validPictures(def picturesArray){

        def picturesProcess = []


        picturesArray.each{

            try {

                if (!it.picture_id){
                    throw new BadRequestException("The format pictures array is not valid")
                }

                if (!it.url){
                    throw new BadRequestException("The format pictures array is not valid")
                }

                Map pictureProcess = [

                        picture_id: it.picture_id,
                        url       : it.url,
                        secure_url: it.secure_url,
                        size      : it.size
                ]

                picturesProcess.add(pictureProcess)

            }catch(Exception e){

                throw new BadRequestException("The format pictures array is not valid")
            }
        }

        picturesProcess

    }


    
    def getResult(def band){


        // TODO añadimos la data que debemos mostrar completa
        
        Map jsonResult = [:]

        jsonResult.band_id              = band.id
        jsonResult.category_id          = band.categoryId
        jsonResult.name                 = band.name
        jsonResult.title                = band.title
        jsonResult.price_min            = band.priceMin
        jsonResult.price_max            = band.priceMax
        jsonResult.currency_type        = band.currencyType
        jsonResult.location_id          = band.locationId
        jsonResult.service_locations    = band.serviceLocations
        jsonResult.events_types         = band.eventsTypes
        jsonResult.manager_id           = band.managerId
        jsonResult.web_page             = band.webPage
        jsonResult.pictures             = band.pictures
        jsonResult.url_videos           = band.urlVideos
        jsonResult.description          = band.description
        try{
            jsonResult.attributes           = band.attributes ? band.attributes : []
        }catch(Exception e){
            jsonResult.attributes       =[]
        }


        jsonResult.type_item            = band.typeItem
        jsonResult.status               = band.status

        jsonResult.date_registration    = band.dateRegistration
        jsonResult.date_update          = band.dateUpdate
        jsonResult.date_expired         = band.dateExpired
        jsonResult.date_activation      = band.dateActivation
        jsonResult.date_renovation      = band.dateRenovation
        jsonResult.date_deleted         = (band.dateDeleted) ? band.dateDeleted : "null"

        jsonResult
        
    }


}
