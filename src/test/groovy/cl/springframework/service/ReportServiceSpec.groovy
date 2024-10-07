package cl.springframework.service

import cl.springframework.dto.ReportRequestDTO
import cl.springframework.exception.ErrorNegocioException
import cl.springframework.util.ReportUtil
import spock.lang.Specification

class ReportServiceSpec extends Specification {
    def reportUtil = Stub(ReportUtil)
    ReportService reportService = new ReportService(reportUtil)

    def setup() {

    }

    def "Generar reporte ok"() {
        given: "Una solicitud de generacion de reporte"
        def reportRequestDTO = ReportRequestDTO
                .builder()
                .name("report")
                .type("pdf")
                .parameters(new HashMap<String, Object>())
                .build()

        when: "Generamos"
        reportUtil.generateReportByNameAndTypeAndParameters(_ as String, _ as String, _ as Map<String, String>) >> new byte[0]
        def response = reportService.generateReport(reportRequestDTO)

        then: "Validamos que sea de manera correcta"
        response != null
    }

    def "Generar reporte con error reporte no encontrado"() {
        given: "Una solicitud de generacion de reporte"
        def reportRequestDTO = ReportRequestDTO
                .builder()
                .name("report1")
                .type("pdf")
                .parameters(new HashMap<String, Object>())
                .build()

        when: "Generamos"
        reportService.generateReport(reportRequestDTO)

        then: "Validamos que venga la excepcion"
        thrown(ErrorNegocioException)
    }
}