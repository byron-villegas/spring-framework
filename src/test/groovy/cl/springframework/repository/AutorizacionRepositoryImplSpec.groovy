package cl.springframework.repository

import spock.lang.Specification

class AutorizacionRepositoryImplSpec extends Specification {
    def autorizacionRepositoryImpl = new AutorizacionRepositoryImpl()

    def "Find By Basic"() {
        given: "Un basic"
        def basic = UUID.randomUUID().toString()

        when: "Buscamos"
        def response = autorizacionRepositoryImpl.findByBasic(basic)

        then:
        !response.isPresent()
    }
}