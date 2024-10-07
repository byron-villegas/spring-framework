package cl.springframework.util

import freemarker.cache.FileTemplateLoader
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer
import spock.lang.Specification

class TemplateUtilSpec extends Specification {
    def freeMarkerConfigurer = Stub(FreeMarkerConfigurer)
    TemplateUtil templateUtil = new TemplateUtil(freeMarkerConfigurer)

    def setup() {
        freemarker.template.Configuration config = new freemarker.template.Configuration()
        config.setTemplateLoader(new FileTemplateLoader(new File(System.getProperty("user.dir") + "/src/test/resources/templates/")))

        freeMarkerConfigurer.getConfiguration() >> config
    }

    def "Generar Template mediante nombre exitosamente"() {
        given: "Una solicitud de generacion de template"
        def name = "template"
        def type = "html"
        def parameters = new HashMap<String, String>()

        parameters.put("nombre", "abc")

        when: "Generamos el template"
        def response = templateUtil.generateTemplateByNameTypeAndParameters(name, type, parameters)

        then: "Validamos que sea de manera correcta"
        response != null
    }

    def "Generar Template mediante nombre con error"() {
        given: "Una solicitud de generacion de template"
        def name = "template"
        def type = "html"
        def parameters = new HashMap<String, String>()

        when: "Generamos el template"
        def response = templateUtil.generateTemplateByNameTypeAndParameters(name, type, parameters)

        then: "Validamos que venga el error"
        response != null
    }
}