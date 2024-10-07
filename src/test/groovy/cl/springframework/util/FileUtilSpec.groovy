package cl.springframework.util

import cl.springframework.constants.Constants
import spock.lang.Specification
import java.nio.file.FileSystemNotFoundException

class FileUtilSpec extends Specification {

    def setup() {

    }

    def "Validar existencia de archivo"() {
        given: "Una solicitud de existencia de archivo"

        when: "Obtenemos la existencia"
        def response = FileUtil.isFileExistsInResources(nombre, ruta)

        then: "Validamos"
        response == resultado

        where:
        nombre          | ruta                            | resultado
        "report.jrxml"  | Constants.JASPER_REPORTS_FOLDER | true
        "report1.jrxml" | Constants.JASPER_REPORTS_FOLDER | false
    }

    def "Obtener texto de un archivo"() {
        given: "Una solicitud de obtencion de texto de archivo"
        def ruta = "data/productos.json"

        when: "Obtenemos el texto"
        def response = FileUtil.getFileContent(ruta)

        then: "Validamos"
        !response.isEmpty()
    }

    def "Obtener texto de un archivo no existente"() {
        given: "Una solicitud de obtencion de texto de archivo"
        def ruta = "data/test.json"

        when: "Obtenemos el texto"
        FileUtil.getFileContent(ruta)

        then: "Validamos que venga la excepcion"
        def fileSystemNotFoundException = thrown(FileSystemNotFoundException)
        fileSystemNotFoundException.getMessage() == "Archivo no encontrado"
    }
}