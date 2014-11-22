environments {
    development {
        grails {
            mongo {
                host = "localhost"
                databaseName = "mb_bands"
            }
        }
    }
    test {
        grails {
            mongo {
                host = "localhost"
                databaseName = "mb_bands"
            }
        }
    }
    production {
        grails {
            mongo {

                // replicaSet = []
                host = "localhost"
                username = ""
                password = ""
                databaseName = "mb_bands"
            }
        }
    }
}