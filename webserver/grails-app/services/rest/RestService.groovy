package rest

import org.codehaus.groovy.grails.commons.DefaultGrailsApplication
import grails.converters.JSON
import javassist.NotFoundException
import net.sf.json.JSONNull
import org.codehaus.groovy.grails.web.json.JSONObject
import bands.exceptions.NotFoundException
import bands.exceptions.ConflictException
import bands.exceptions.BadRequestException

import grails.util.Environment
import groovyx.net.http.*
import groovyx.net.http.HTTPBuilder

import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

import groovyx.net.http.RESTClient

import javax.servlet.http.HttpServletResponse
import grails.plugin.gson.converters.GSON




class RestService {


    static transactional = true

    def grailsApplication = new DefaultGrailsApplication()
    def urlBase = grailsApplication.config.domainMainUsers



    def restClient  = new RESTClient(urlBase)


    def getResource(def resource, def queryParams){

        Map result = [:]



        try {

            def resp = restClient.get(path:resource, query:queryParams)

            if (resp.status == HttpServletResponse.SC_OK) {

                result.status   = resp.status
                result.data     = resp.data

            }
            else{
                result.status   = HttpServletResponse.SC_NOT_FOUND
                result.data     = []
            }
        } catch (Exception e){
            result.status   = HttpServletResponse.SC_NOT_FOUND
            result.data     = []
        }

        result


    }

    def getResource(def resource){

        Map result = [:]

        try {

            def resp = restClient.get(path: resource)

            if (resp.status == HttpServletResponse.SC_OK) {

                result.status   = resp.status
                result.data     = resp.data

            }
            else{
                result.status   = HttpServletResponse.SC_NOT_FOUND
                result.data     = []
            }
        } catch (Exception e){
            result.status   = HttpServletResponse.SC_NOT_FOUND
            result.data     = []
        }


        result


    }


    def postResource(def resource, def body){

        Map result = [:]

        try {

            def resp = restClient.post(
                    path: resource,
                    body: body,
                    requestContentType: 'application/json')

            if (resp.status == HttpServletResponse.SC_CREATED || resp.status == HttpServletResponse.SC_OK) {

                result.status = resp.status
                result.data = resp.data

            }else {

                result.status   = HttpServletResponse.SC_NOT_FOUND
                result.data     = []
            }


        }catch(Exception e){

            result.status   = HttpServletResponse.SC_NOT_FOUND
            result.data     = []

        }

        result
    }

    def postResource(def resource,def query, def body){

        Map result = [:]

        try {

            def resp = restClient.post(
                    path: resource,
                    query: query,
                    body: body,
                    requestContentType: 'application/json')

            if (resp.status == HttpServletResponse.SC_CREATED || resp.status == HttpServletResponse.SC_OK) {

                result.status = resp.status
                result.data = resp.data

            }else {

                result.status   = HttpServletResponse.SC_NOT_FOUND
                result.data     = []
            }


        }catch(Exception e){

            result.status   = HttpServletResponse.SC_NOT_FOUND
            result.data     = []

        }

        result
    }

    def putResource(def resource, def body){
        Map result = [:]



        try {

            def resp = restClient.put(
                    path : resource,
                    body : body,
                    requestContentType : 'application/json' )

            if (resp.status == HttpServletResponse.SC_CREATED || resp.status == HttpServletResponse.SC_OK) {

                result.status = resp.status
                result.data = resp.data

            }else {

                result.status   = HttpServletResponse.SC_NOT_FOUND
                result.data     = []
            }


        }catch(Exception e){

            result.status   = HttpServletResponse.SC_NOT_FOUND
            result.data     = []

        }

        result

        result
    }


}
