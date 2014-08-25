package api.bands

class Band {

    static constraints = {

        categoryId      nullable:false, blank:false, maxSize: 20
        name            nullable:false, blank:false, maxSize: 128, minSize: 10
        title           nullable:false, blank:false, maxSize: 150, minSize: 25
        priceMin        min:0F
        priceMax        min:0F
        currencyType    inList:['MXP','USD']
        locationId      nullable: false, blank:false, maxSize: 20
        managerId       nullable:false, blank:false, maxSize: 20
        webPage         nullable: true, blank:true
        description     nullable:false, blank:true, maxSize: 1024

        typeItem        inList:['destacado','basico','free','promotor']
        status          inList: ['active','closed','pending','paused']
        dateDeleted     nullable: true
    }

    String  categoryId
    String  name
    String  title
    Float   priceMin
    Float   priceMax
    String  currencyType
    String  locationId
    List    serviceLocations = []
    List    eventsTypes = []
    String  managerId
    String  webPage
    List    pictures = []
    List    urlVideos = []
    String  description

    String  typeItem
    String  status

    Date    dateRegistration = new Date()
    Date    dateUpdate = new Date()
    Date    dateExpired = new Date() + 60
    Date    dateActivation = new Date()
    Date    dateRenovation = new Date()
    Date    dateDeleted


}
