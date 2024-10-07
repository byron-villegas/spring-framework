package cl.springframework.service

import cl.springframework.dto.TemplateRequestDTO
import cl.springframework.exception.ErrorNegocioException
import cl.springframework.util.TemplateUtil
import spock.lang.Specification

class TemplateServiceSpec extends Specification {
    def templateUtil = Stub(TemplateUtil)
    TemplateService templateService = new TemplateService(templateUtil)

    def setup() {

    }

    def "Generar template ok"() {
        given: "Una solicitud de generacion de template"
        def templateRequestDTO = TemplateRequestDTO
                .builder()
                .name("template")
                .parameters(new HashMap<String, Object>())
                .build()

        when: "Generamos"
        templateUtil.generateTemplateByNameTypeAndParameters(_ as String, _ as String, _ as Map<String, String>) >> ""
        def response = templateService.generateTemplate(templateRequestDTO)

        then: "Validamos que sea de manera correcta"
        response != null
    }

    def "Generar template con error template no encontrado"() {
        given: "Una solicitud de generacion de template"
        def templateRequestDTO = TemplateRequestDTO
                .builder()
                .name("template1")
                .parameters(new HashMap<String, Object>())
                .build()

        when: "Generamos"
        templateService.generateTemplate(templateRequestDTO)

        then: "Validamos que venga la excepcion"
        thrown(ErrorNegocioException)
    }
}