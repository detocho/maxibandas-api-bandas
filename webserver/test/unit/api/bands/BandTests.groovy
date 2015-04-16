package api.bands



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Band)
class BandTests {

    def registeredBand
    def sampleBand

    @Before
    void setUp(){

        registeredBand =  new Band(

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
                description:"Somos un mariachi con mas de 10 años de experiencia, nuestra musica es con hecha con alta " +
                        "calidad, no te arrepntiras, miranos en el video"


        )

        mockForConstraintsTests(Band, [registeredBand])

        sampleBand =  new Band(

                categoryId:"5",
                name:'Los biscochitos de santa Fe',
                title: "Norteño: Los biscohitos de santa fe",
                priceMin:2500,
                priceMax:20000,
                currencyType:"MXP",
                locationId:"5",
                managerId:"2",
                webPage:"",
                typeItem:"destacado",
                status:"active",
                description:"Somos una grupo norteño con mas de 10 años de experiencia, nuestra musica es con hecha con alta " +
                        "calidad, no te arrepntiras, miranos en el video"


        )


    }


    void test_BandIsInvalid_WithCategoryIdIsNullOrEmpty(){

        sampleBand.categoryId = ''
        assertFalse(sampleBand.validate())
        assertEquals('The categoryId not be must empty','blank',sampleBand.errors['categoryId'])
    }

    void test_BandIsInvalid_WithCategoryIdSize(){
        sampleBand.categoryId = 'MB-2014001238393039393HDNDHCJFJNCJF0000000000000000000001'
        assertFalse(sampleBand.validate())
        assertEquals('The categoryId is very long','maxSize',sampleBand.errors['categoryId'])
    }

    void test_BandIsInvalid_WithNameIsNullOrEmpty(){

        sampleBand.name = ''
        assertFalse(sampleBand.validate())
        assertEquals('The name not be must blank', 'blank', sampleBand.errors['name'])

        sampleBand.name = null
        assertFalse(sampleBand.validate())
        assertEquals('The name not be must null', 'nullable', sampleBand.errors['name'])
    }

    void test_BandIsInvalid_WithNameSize(){

        sampleBand.name = 'el chao'
        assertFalse(sampleBand.validate())
        assertEquals('The name is very short','minSize',sampleBand.errors['name'])

        sampleBand.name = 'la banda sinaloense de los muchos sinaloa que canta bien chingon no hay ninguna igual de verdad que no' +
                'somos los mejores y los unicos'
        assertFalse(sampleBand.validate())
        assertEquals('The name is very long','maxSize',sampleBand.errors['name'])

    }

    void test_BandIsInvalid_WithTitleIsNullOrEmpty(){

        sampleBand.title = ''
        assertFalse(sampleBand.validate())
        assertEquals('The title not be must blank','blank',sampleBand.errors['title'])

        sampleBand.title = null
        assertFalse(sampleBand.validate())
        assertEquals('The title not be must null','nullable',sampleBand.errors['title'])
    }

    void test_BandIsInvalid_WithTitleSize(){

        sampleBand.title = 'este no es un titulo'
        assertFalse(sampleBand.validate())
        assertEquals('The lenght title is short', 'minSize', sampleBand.errors['title'])

        sampleBand.title = 'este no es un titulo que esta demasiado largo para ser procesado como titulo' +
                'debemos quitar algunos de los caracteres para que el titulo sea procesado asi que vean como '
        assertFalse(sampleBand.validate())
        assertEquals('The lenght title is long', 'maxSize', sampleBand.errors['title'])

    }

    void test_BandIsNotValid_WithPriceMinNegative(){

        sampleBand.priceMin = -1
        assertFalse(sampleBand.validate())
        assertEquals('min',sampleBand.errors['priceMin'])
    }

    void test_BandIsNotValid_WithPriceMaxNegative(){

        sampleBand.priceMax = -1
        assertFalse(sampleBand.validate())
        assertEquals('min',sampleBand.errors['priceMax'])

    }

    void test_BandIsInvalid_WithCurrencyTypeNotInList(){

        sampleBand.currencyType = 'EUS'
        assertFalse(sampleBand.validate())
        assertEquals('inList', sampleBand.errors['currencyType'])
    }

    void test_BandIsInvalid_WithLocationIdIsNullOrEmpty(){

        sampleBand.locationId = ''
        assertFalse(sampleBand.validate())
        assertEquals('blank',sampleBand.errors['locationId'])

        sampleBand.locationId =  null
        assertFalse(sampleBand.validate())
        assertEquals('nullable',sampleBand.errors['locationId'])

    }

    void test_BandIsInvalid_WithLocationIdMaxSize(){

        sampleBand.locationId = 'MB-LOC123321323487632489347081476012746104872364023784621093842132134'
        assertFalse(sampleBand.validate())
        assertEquals('maxSize',sampleBand.errors['locationId'])

    }

    void test_BandIsInvalid_WithManagerIdIsNullOrEmpty(){

        sampleBand.managerId = ''
        assertFalse(sampleBand.validate())
        assertEquals('blank',sampleBand.errors['managerId'])

        sampleBand.managerId = null
        assertFalse(sampleBand.validate())
        assertEquals('nullable',sampleBand.errors['managerId'])
    }

    void test_BandIsInvalid_WithManagerIdMaxSize(){

        sampleBand.managerId = 'MB-Mng-1289701924801984103987498374098274029384702398472039487230948723098472'
        assertFalse(sampleBand.validate())
        assertEquals('maxSize',sampleBand.errors['managerId'])
    }

    void test_BandIsInvalid_WithDescriptionIsNullOrEmpty(){

        sampleBand.description = null
        assertFalse(sampleBand.validate())
        assertEquals('nullable',sampleBand.errors['description'])
    }

    void test_BandIsInvalid_WithDescriptionMaxSize(){

        sampleBand.description = 'aqui colocamos caracteres y caracteres para llegar a un adescripcion que ' +
                'exceda los 1024 caracteres, no se como debiera escribir algo aqui que exeda esos caracteres' +
                'creo que debe reducri los caracteres, una description deberia de truncarse en cuantos caracteres' +
                'si sigo asi, llenaré la hoja y creo que no llegare a los 1024 milveinticuatro si lo escucho bien ' +
                'son mas de mil caracteres woow con esto seria genial que pudieran describir su auto ademas que me' +
                'sirve como ejercicio para poder entender mejor el teclado y asi tomar habilidad en esto de la escritura' +
                'de los codigos, aunque deberia de aprender a escribir mucho mas rapido, pero no se por que' +
                'no se me da tal vez me hace mas falta en la computadora denberia de aprender a escribir con ' +
                'mayor velocidad, ya seran los mas de 1024 caracteres, no lo se por lo menos deberia de haber 10' +
                'lineas de 100 cararcters y dudo que este escribiendo mas de 100 carateres por linea, entonce' +
                'mmmm que puedo hacer, claro escribire letras a lo bruto, jajaja si asi hare muscha smuchas' +
                'letras comencemos skjdñlkasdl lkasdlkasj laksjdñl akkjsa ldñalksdsknlñ askllaiheknñida ñañknd ' +
                'lkasndlashlknalñskndalñsdn lknaskdnñlaksndhdaufheuf oanfoic   oijoi ohoanñoiah laknañjfkhsf' +
                'lksnfñsakndk a iahkfmoefuhejalfkapodsjfñoewfkjsñkdhf klasdlkfñoijf aklsdñf akldfj ñaoiwf ñawkf a' +
                'aosñfoia foijaskndfoñijasfoiehfowlakf aksflkwaefoiwe fwifñlaknsdfñoiaw fñiajwf ñaief pfwq' +
                'oasdnfñoaijwef oiahñdfneuhfña  oiañofn aoifñ aknñf oiaoinfvkjadvohas oifnañsoihfñsaoidjf a' +
                'sadjnfoiajw eifj oiajwoñeifñao infñoeifna woiañfnañijsfoiej oieknfhdiofjoe iapfjoiefwkmfñoijdf ' +
                'saoifjef eijoijañijfñwoeifjñwoiejfwñ fiwjef iojfñakdjñijeñrqñoeifjñewkfnñwoqiejfñqe fn fa' +
                'asfñknfneoifn oeifnwoeinfñoiwejeo dñoaisjdñoiejdñoqidjñoqijd qñidqoñidjqwñokdn qñodiqnwd ' +
                'dnqñoidqñwoidqw ñidonqwñidoq odinqodnslkanñaoifjñeoinfñalkjsñkdañodije ñdianñoidjañoksndñoaijd ' +
                'oaisnñdoiañdkm oifjapñosjfñasmf aoñisjfñapksf ñaosifnñaiosnfño aisndña sdnñoasindñoaisndañ doin a' +
                'asndlkansd'

        assertFalse(sampleBand.validate())
        assertEquals('maxSize',sampleBand.errors['description'])
    }

    void test_BandIsInvalid_WithTypeItemNotInList(){

        sampleBand.typeItem = 'gratis'
        assertFalse(sampleBand.validate())
        assertEquals('inList',sampleBand.errors['typeItem'])
    }

    void test_BandIsInvalid_WithStatusInList(){

        sampleBand.status = 'fraude'
        assertFalse(sampleBand.validate())
        assertEquals('inList',sampleBand.errors['status'])
    }

    void test_BandIsValid(){

        sampleBand.save()
        assertEquals('5',sampleBand.categoryId)
        assertEquals('active',sampleBand.status)
    }
}
