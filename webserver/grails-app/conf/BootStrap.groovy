import api.bands.Band

class BootStrap {

    def init = { servletContext ->

        test{

        }
        development{

            if(Band.count() == 0){

                def picture01 = [
                        pictureId:"PIC-201408221",
                        url:"http://www.detocho.com.mx/Images/tv_lista200.jpg",
                        secureUrl:"",
                        size:"260x190"
                ]
                def picture02 = [
                        pictureId:"PIC-201408222",
                        url:"http://www.detocho.com.mx/Images/tv_lista200.jpg",
                        secureUrl:"",
                        size:"260x190"
                ]
                def picture03 = [
                        pictureId:"PIC-201408223",
                        url:"http://www.detocho.com.mx/Images/tv_lista200.jpg",
                        secureUrl:"",
                        size:"260x190"
                ]
                def picture04 = [
                        pictureId:"PIC-201408224",
                        url:"http://www.detocho.com.mx/Images/tv_lista200.jpg",
                        secureUrl:"",
                        size:"260x190"
                ]
                def picture05 = [
                        pictureId:"PIC-201408225",
                        url:"http://www.detocho.com.mx/Images/tv_lista200.jpg",
                        secureUrl:"",
                        size:"260x190"
                ]

                def serviceLocation01 = [
                        locationId: "LOC-0001",
                        name: "Aguscalientes"
                ]

                def serviceLocation02 = [
                        locationId: "LOC-0002",
                        name: "Baja California"
                ]

                def serviceLocation03 = [
                        locationId: "LOC-0002",
                        name: "Distrito Federal"
                ]

                def eventsType01 = "Bodas"
                def eventsType02 = "XV años"
                def eventsType03 = "Ferias"
                def eventsType04 = "Palenques"

                def urlVideos01 = "https://www.youtube.com/watch?v=kIJX9bJ7ZOU"
                def urlVideos02 = "https://www.youtube.com/watch?v=kIJX9bJ7ZOU"
                def urlVideos03 = "https://www.youtube.com/watch?v=kIJX9bJ7ZOU"
                def urlVideos04 = "https://www.youtube.com/watch?v=kIJX9bJ7ZOU"


                def band01 =  new Band(

                        categoryId:"4",
                        name:'Mariachi gamamil de mexico',
                        title: "Mariachi: Mariachi Gamamil de México",
                        priceMin:2500,
                        priceMax:20000,
                        currencyType:"MXP",
                        locationId:"5",
                        managerId:"2",
                        webPage:"",
                        typeItem:"destacado",
                        status:"active",
                        attributes:[],
                        description:"Somos un mariachi con mas de 10 años de experiencia, nuestra musica es con hecha con alta " +
                                "calidad, no te arrepntiras, miranos en el video"

                )

                band01.pictures.add(picture01)
                band01.pictures.add(picture02)
                band01.pictures.add(picture03)
                band01.pictures.add(picture04)

                band01.eventsTypes.add(eventsType01)
                band01.eventsTypes.add(eventsType02)
                band01.eventsTypes.add(eventsType03)

                band01.serviceLocations.add(serviceLocation01)
                band01.serviceLocations.add(serviceLocation02)

                band01.urlVideos.add(urlVideos01)
                band01.urlVideos.add(urlVideos02)

                if(!band01.save()){
                    println 'fallo la creacion de la clase'
                    band01.errors.each {
                        println it
                    }
                }

                def band02 =  new Band(

                        categoryId:"4",
                        name:'Mariachi gamamil de mexico',
                        title: "Mariachi: Mariachi Gamamil de México",
                        priceMin:2500,
                        priceMax:20000,
                        currencyType:"MXP",
                        locationId:"5",
                        managerId:"2",
                        webPage:"",
                        typeItem:"free",
                        status:"active",
                        attributes:[],
                        description:"Somos un mariachi con mas de 10 años de experiencia, nuestra musica es con hecha con alta " +
                                "calidad, no te arrepntiras, miranos en el video"

                )

                band02.pictures.add(picture05)


                band02.eventsTypes.add(eventsType01)
                band02.eventsTypes.add(eventsType02)
                band02.eventsTypes.add(eventsType04)


                band02.serviceLocations.add(serviceLocation01)
                band02.serviceLocations.add(serviceLocation02)
                band02.serviceLocations.add(serviceLocation03)

                band02.urlVideos.add(urlVideos01)
                band02.urlVideos.add(urlVideos03)

                band02.save()


            }

        }
        production{

        }
    }
    def destroy = {
    }
}
