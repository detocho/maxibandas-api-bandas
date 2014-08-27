package api.bands

import javax.servlet.http.HttpServletResponse
import grails.converters.*
//import grails.transaction.*
import static org.springframework.http.HttpStatus.*
import static org.springframework.http.HttpMethod.*
import grails.plugin.gson.converters.GSON
import bands.exceptions.NotFoundException
import bands.exceptions.ConflictException
import bands.exceptions.BadRequestException

class BandController {

    def bandService

    def notAllowed(){
        def method = request.method

        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED)
        response.setContentType("application/json; charset=utf-8")

        def mapResult = [
                message: "Method $method not allowed",
                status: HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                error:"not_allowed"
        ]
        render mapResult as GSON
    }

    def getBand(){

        def bandId = params.bandId
        def result

        try{
            result = bandService.get(bandId)
            response.setStatus(HttpServletResponse.SC_OK)
            response.setContentType "application/json; charset=utf-8"
            render result as GSON

        }catch (NotFoundException e){
            response.setStatus(e.status)
            response.setContentType "application/json; charset=utf-8"

            def mapExcepction = [
                    message: e.getMessage(),
                    status: e.status,
                    error: e.error
            ]
            render mapExcepction as GSON

        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
            response.setContentType "application/json; charset=utf-8"
            def mapExcepction = [
                    message: e.getMessage(),
                    status: HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    error: "internal_server_error"
            ]
            render mapExcepction as GSON
        }

    }

}
