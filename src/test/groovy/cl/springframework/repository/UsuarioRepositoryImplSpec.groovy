package cl.springframework.repository

import spock.lang.Specification

class UsuarioRepositoryImplSpec extends Specification {
    def usuarioRepositoryImpl = new UsuarioRepositoryImpl()

    def "FindByUsernameAndPassword"() {
        given: "Una solicitud de obtencion por usuario y clave"

        when: "Obtenemos"
        def response = usuarioRepositoryImpl.findByUsernameAndPassword(username, password)

        then: "Validamos que sea de manera correcta"
        response.isPresent() == present

        where:
        username | password   | present
        "jbodoq" | "admin123" | true
        "jbodoq" | "admin124" | false
        "dssaSS" | "admin123" | false
    }
}
