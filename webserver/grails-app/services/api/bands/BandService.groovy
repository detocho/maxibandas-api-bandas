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

        jsonResult.id                   = bands.id
        jsonResult.category_id          = bands.categoryId
        jsonResult.name                 = bands.name
        jsonResult.title                = bands.title
        jsonResult.price_min            = bands.priceMin
        jsonResult.price_max            = bands.priceMax
        jsonResult.currency_type        = bands.currencyType
        jsonResult.location_id          = bands.locationId
        jsonResult.service_locations    = bands.serviceLocations
        jsonResult.events_types         = bands.eventsTypes
        jsonResult.manager_id           = bands.managerId
        jsonResult.web_page             = bands.webPage
        jsonResult.pictures             = bands.pictures
        jsonResult.url_videos           = bands.urlVideos
        jsonResult.description          = bands.description

        jsonResult.type_item            = bands.typeItem
        jsonResult.status               = bands.status

        jsonResult.date_registration    = bands.dateRegistration
        jsonResult.date_update          = bands.dateUpdate
        jsonResult.date_expired         = bands.dateExpired
        jsonResult.date_activation      = bands.dateActivation
        jsonResult.date_renovation      = bands.dateRenovation
        jsonResult.date_deleted         = (bands.dateDeleted) ? bands.dateDeleted : "null"

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

        jsonResult.id                   = newBand.id
        jsonResult.category_id          = newBand.categoryId
        jsonResult.name                 = newBand.name
        jsonResult.title                = newBand.title
        jsonResult.price_min            = newBand.priceMin
        jsonResult.price_max            = newBand.priceMax
        jsonResult.currency_type        = newBand.currencyType
        jsonResult.location_id          = newBand.locationId
        jsonResult.service_locations    = newBand.serviceLocations
        jsonResult.events_types         = newBand.eventsTypes
        jsonResult.manager_id           = newBand.managerId
        jsonResult.web_page             = newBand.webPage
        jsonResult.pictures             = newBand.pictures
        jsonResult.url_videos           = newBand.urlVideos
        jsonResult.description          = newBand.description

        jsonResult.type_item            = newBand.typeItem
        jsonResult.status               = newBand.status

        jsonResult.date_registration    = newBand.dateRegistration
        jsonResult.date_update          = newBand.dateUpdate
        jsonResult.date_expired         = newBand.dateExpired
        jsonResult.date_activation      = newBand.dateActivation
        jsonResult.date_renovation      = newBand.dateRenovation
        jsonResult.date_deleted         = (newBand.dateDeleted) ? newBand.dateDeleted : "null"

        jsonResult
    }

    def put(def id, def jsonBand){

    }


}
