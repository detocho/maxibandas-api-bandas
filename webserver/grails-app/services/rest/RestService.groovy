package rest

import org.codehaus.groovy.grails.commons.DefaultGrailsApplication
import groovyx.net.http.RESTClient
import javax.servlet.http.HttpServletResponse





class RestService {


    static transactional = true

    def grailsApplication = new DefaultGrailsApplication()

    def urlBaseUser         = grailsApplication.config.domainMainUsers
    def urlBaseLocations    = grailsApplication.config.domainMainLocations
    def urlBaseCategories   = grailsApplication.config.domainMainCategories

    def restClient = new RESTClient(urlBaseUser)

    def defineServiceType (def typeRestService){

        switch (typeRestService){

            case 'users':
                restClient = new RESTClient(urlBaseUser)
                break
            case 'locations':
                restClient = new RESTClient(urlBaseLocations)
                break
            case 'categories':
                restClient = new RESTClient(urlBaseCategories)
                break
            default:
                restClient = new RESTClient(urlBaseUser)
                break
        }
    }

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
