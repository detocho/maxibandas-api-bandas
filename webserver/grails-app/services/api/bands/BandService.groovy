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


}
