package cl.springframework.util

import cl.springframework.configuration.JwtConfiguration
import spock.lang.Specification

class JwtTokenUtilSpec extends Specification {
    def jwtConfiguration = Stub(JwtConfiguration)
    JwtTokenUtil jwtTokenUtil = new JwtTokenUtil(jwtConfiguration)

    def setup() {
        jwtConfiguration.getSecret() >> "Test@123"
        jwtConfiguration.getDuration() >> 60
    }

    def "Generar token"() {
        given: "Una solicitud de generacion de token"
        def rutCliente = "11.111.111-1"
        def username = "admin"
        def roles = Arrays.asList("ROLE_USER", "ROLE_ADMINISTRATOR")

        when: "Generamos el token"
        def response = jwtTokenUtil.generateToken(rutCliente, username, roles)

        then: "Validamos que sea de manera correcta"
        response != null
    }

    def "Obtener claim"() {
        given: "Una solicitud de obtencion de claim"
        def rutCliente = "11.111.111-1"
        def username = "admin"
        def roles = Collections.singletonList("ROLE_USER")

        when: "Obtenemos el claim"
        def token = jwtTokenUtil.generateToken(rutCliente, username, roles)
        def claim = jwtTokenUtil.getClaimFromToken(token, "username")

        then: "Validamos que sea de manera correcta"
        token != null
        claim != null
    }

    def "Obtener expiration"() {
        given: "Una solicitud de obtencion de expiration"
        def rutCliente = "11.111.111-1"
        def username = "admin"
        def roles = Collections.singletonList("ROLE_USER")

        when: "Obtenemos la expiration"
        def token = jwtTokenUtil.generateToken(rutCliente, username, roles)
        def expiration = jwtTokenUtil.getExpirationFromToken(token)

        then: "Validamos que sea de manera correcta"
        token != null
        expiration > 0
    }

    def "Validar token exitoso"() {
        given: "Una solicitud de validacion de token"
        def rutCliente = "11.111.111-1"
        def username = "admin"
        def roles = Collections.singletonList("ROLE_USER")

        when: "Validamos"
        def token = jwtTokenUtil.generateToken(rutCliente, username, roles)
        def response = jwtTokenUtil.isValidToken(token)

        then: "Comprobamos el resultado"
        token != null
        response
    }

    def "Validar token con error"() {
        given: "Una solicitud de validacion de token"
        def token = "eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6IltST0xFX1VTRVJdIiwicnV0X2NsaWVudGUiOiIxMS4xMTEuMTExLTEiLCJleHAiOjE2ODkyMzI3MDksImlhdCI6MTY4OTIyOTEwOSwidXNlcm5hbWUiOiJqYm9kb3EifQ.H5FYae2MY-KH9E73jcjyF8IY57uha1M-AtqUCfbdDObjVEN-TTGXDdxvHbW3A0RzeyU3XhM-Rw_QB4CqFoQgtQ"

        when: "Validamos"
        def response = jwtTokenUtil.isValidToken(token)

        then: "Comprobamos el resultado"
        token != null
        !response
    }
}