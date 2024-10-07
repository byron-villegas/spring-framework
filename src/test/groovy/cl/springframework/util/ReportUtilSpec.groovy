package cl.springframework.util

import cl.springframework.exception.ErrorNegocioException
import cl.springframework.exception.ErrorTecnicoException
import spock.lang.Specification
import javax.sql.DataSource
import java.sql.Connection

class ReportUtilSpec extends Specification {
    ReportUtil reportUtil = new ReportUtil()

    def "Generar reporte mediante nombre y tipo ok"() {
        given: "Una solicitud de generacion de reporte"
        def parameters = new HashMap<String, Object>()

        when: "Generamos el reporte"
        def response = reportUtil.generateReportByNameAndTypeAndParameters(name, type, parameters)

        then: "Validamos que sea de manera correcta"
        response != null

        where:
        name     | type
        "report" | "pdf"
        "report" | "xlsx"
        "report" | "csv"
        "report" | "html"
    }

    def "Generar reporte mediante nombre y tipo con error"() {
        given: "Una solicitud de generacion de reporte"
        def parameters = new HashMap<String, Object>()

        when: "Generamos el reporte"
        reportUtil.generateReportByNameAndTypeAndParameters("report1", "pdf", parameters)

        then: "Validamos que venga la excepcion"
        thrown(ErrorTecnicoException)
    }

    def "Generar reporte mediante nombre y tipo con error tipo desconocido"() {
        given: "Una solicitud de generacion de reporte"
        def parameters = new HashMap<String, Object>()

        when: "Generamos el reporte"
        reportUtil.generateReportByNameAndTypeAndParameters("report", "txt", parameters)

        then: "Validamos que venga la excepcion"
        thrown(ErrorNegocioException)
    }
}