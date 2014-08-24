package api.bands

class Band {

    static constraints = {
    }

    String categoryId
    String name
    Float priceMin
    Float priceMax
    String currencyType
    String locationId
    List serviceLocations = []
    List eventsTypes = []
    String managerId
    String webPage
    List pictures = []
    List urlVideos = []
    String description

    String typeItem
    String status

    Date dateRegistration = new Date()
    Date dateUpdate = new Date()
    Date dateExpired = new Date() + 60
    Date dateActivation = new Date()
    Date dateRenovation = new Date()
    Date dateDeleted 


}
