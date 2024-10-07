package cl.springframework.util

import spock.lang.Specification

class HttpHeaderUtilSpec extends Specification {

    def setup() {

    }

    def "Generar Http Headers mediante el tipo de archivo"() {
        given: "Una solicitud de generacion de Http Headers"

        when: "Generamos los Http Headers"
        def response = HttpHeaderUtil.getHttpHeadersByFileType(name, type)

        then: "Validamos que sea de manera correcta"
        response != null

        where:
        name      | type
        "Archivo" | "xlsx"
        "Archivo" | "pdf"
        "Archivo" | "csv"
        "Archivo" | "html"
    }
}