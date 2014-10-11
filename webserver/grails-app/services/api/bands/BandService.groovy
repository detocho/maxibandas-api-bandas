package api.bands

import java.text.MessageFormat
import org.apache.ivy.plugins.conflict.ConflictManager
import grails.converters.*
import bands.exceptions.NotFoundException
import bands.exceptions.ConflictException
import bands.exceptions.BadRequestException

class BandService {

    static transactional = 'mongo'

    def get(def bandId){

        Map jsonResult = [:]

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

    def add(def jsonBand){

        Map jsonResult = [:]

        def responseMessage = ''

        Band newBand = new Band(

                categoryId          : jsonBand?.category_id,
                name                : jsonBand?.name,
                title               : jsonBand?.title,
                priceMin            : jsonBand?.price_min,
                priceMax            : jsonBand?.price_max,
                currencyType        : jsonBand?.currency_type,
                locationId          : jsonBand?.location_id,
                serviceLocations    : jsonBand?.service_locations,
                eventsTypes         : jsonBand?.events_types,
                managerId           : jsonBand?.manager_id,
                webPage             : jsonBand?.web_page,
                pictures            : jsonBand?.pictures,
                urlVideos           : jsonBand?.url_videos,
                description         : jsonBand?.description,

                typeItem            : jsonBand?.type_item,
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

    def put(def id, def jsonBand){

        Map jsonResult = [:]
        def responseMessage

        if (!id){
            throw new NotFoundException("You must provider id band")
        }

        def obteinedBand = Band.findById(id)

        if (!obteinedBand){
            throw new NotFoundException("The Band whit id =" +id+" not found")
        }

        //TODO debemos agregar un validador de json
        //TODO debemos gestionar la api de seguridad

        obteinedBand.categoryId          = jsonBand?.category_id
        obteinedBand.name                = jsonBand?.name
        obteinedBand.title               = jsonBand?.title
        obteinedBand.priceMin            = jsonBand?.price_min
        obteinedBand.priceMax            = jsonBand?.price_max
        obteinedBand.currencyType        = jsonBand?.currency_type
        obteinedBand.locationId          = jsonBand?.location_id
        obteinedBand.serviceLocations    = jsonBand?.service_locations
        obteinedBand.eventsTypes         = jsonBand?.events_types
        obteinedBand.managerId           = jsonBand?.manager_id
        obteinedBand.webPage             = jsonBand?.web_page
        obteinedBand.pictures            = jsonBand?.pictures
        obteinedBand.urlVideos           = jsonBand?.url_videos
        obteinedBand.description         = jsonBand?.description

        obteinedBand.typeItem            = jsonBand?.type_item
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

    
    
    def getResult(def band){
        
        Map jsonResult = [:]

        jsonResult.id                   = band.id
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
