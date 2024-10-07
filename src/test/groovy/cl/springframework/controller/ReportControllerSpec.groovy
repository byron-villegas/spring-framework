package cl.springframework.controller

import cl.springframework.dto.ReportRequestDTO
import cl.springframework.service.ReportService
import com.google.gson.Gson
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class ReportControllerSpec extends Specification {
    def reportService = Mock(ReportService)
    def mvc = MockMvcBuilders.standaloneSetup(new ReportController(reportService)).build()
    def gson = new Gson()

    def setup() {

    }

    def "Generar reporte"() {
        given: "Una solicitud de generacion de reporte"
        def templateRequestDTO = ReportRequestDTO
                .builder()
                .name("report")
                .type("pdf")
                .parameters(new HashMap<String, Object>())
                .build()

        when: "Generamos"
        def response = mvc.perform(MockMvcRequestBuilders.post("/reports")
                .accept(MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(templateRequestDTO)))
                .andReturn().response

        then: "Validamos que sea de manera correcta"
        response.status == HttpStatus.OK.value()
    }
}