package cl.springframework.util

import org.springframework.validation.FieldError
import spock.lang.Specification

class ValidacionesParametrosUtilSpec extends Specification {

    def "generar detalles parametros error"() {
        given: "Una solicitud de generacion de detalles parametros error"

        def fieldError = new FieldError("Test", "test", "test es obligatorio")
        def errores = Arrays.asList(fieldError)

        when: "Generamos"
        def response = ValidacionesParametrosUtil.generarDetallesParametrosErrores(errores)

        then: "Validamos que sea de manera correcta"
        !response.isEmpty()
    }
}