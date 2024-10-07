package cl.springframework.controller

import cl.springframework.dto.TemplateRequestDTO
import cl.springframework.service.TemplateService
import com.google.gson.Gson
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class TemplateControllerSpec extends Specification {
    def templateService = Mock(TemplateService)
    def mvc = MockMvcBuilders.standaloneSetup(new TemplateController(templateService)).build()
    def gson = new Gson()

    def setup() {

    }

    def "Generar template"() {
        given: "Una solicitud de generacion de template"
        def templateRequestDTO = TemplateRequestDTO
                .builder()
                .name("template")
                .parameters(new HashMap<String, Object>())
                .build()

        when: "Generamos"
        def response = mvc.perform(MockMvcRequestBuilders.post("/templates")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(templateRequestDTO)))
                .andReturn().response

        then: "Validamos que sea de manera correcta"
        response.status == HttpStatus.OK.value()
    }
}