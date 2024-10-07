package cl.springframework.service

import cl.springframework.dto.AuthenticationRequestDTO
import cl.springframework.exception.ErrorNegocioException
import cl.springframework.model.Autorizacion
import cl.springframework.model.Usuario
import cl.springframework.repository.AutorizacionRepository
import cl.springframework.repository.UsuarioRepository
import cl.springframework.util.JwtTokenUtil
import spock.lang.Specification

class AuthenticationServiceSpec extends Specification {
    def autorizacionRepository = Stub(AutorizacionRepository)
    def usuarioRepository = Stub(UsuarioRepository)
    def jwtTokenUtil = Stub(JwtTokenUtil)
    def authenticationService

    def setup() {
        authenticationService = new AuthenticationService(autorizacionRepository, usuarioRepository, jwtTokenUtil)
    }

    def "Generar autenticacion de manera correcta"() {
        given: "Una solicitud de autenticacion"
        def authorization = "Basic "+UUID.randomUUID().toString()
        def authenticationRequestDTO = AuthenticationRequestDTO
                .builder()
                .username("11.111.111-1")
                .password("admin123")
                .build()
        def autorizacion = Autorizacion
                .builder()
                .basic(authorization.split(" ")[1])
                .rol("ROLE_USER")
                .build()
        def usuario = Usuario
                .builder()
                .rut("11.111.111-1")
                .username("jbodoq")
                .nombres("Juan Carlos")
                .apellidoPaterno("Bodoque")
                .apellidoMaterno("Bodoque")
                .email("jbodoq@gmail.com")
                .telefono("987654321")
                .estado("VIG")
                .password("admin123")
                .build()

        when: "Autenticamos"
        autorizacionRepository.findByBasic(_ as String) >> Optional.of(autorizacion)
        usuarioRepository.findByUsernameAndPassword(_ as String, _ as String) >> Optional.of(usuario)
        jwtTokenUtil.generateToken(_ as String, _ as String, _ as List<String>) >> UUID.randomUUID().toString()
        jwtTokenUtil.getExpirationFromToken(_ as String) >> Math.random() * 100 * 5
        def response = authenticationService.auth(authorization, authenticationRequestDTO)

        then: "Validamos que sea de manera correcta"
        response != null
        !response.getAccessToken().isEmpty()
        response.getExpiration() > 1
        !response.getRoles().isEmpty()
    }

    def "Generar autenticacion con error authorization no valido"() {
        given: "Una solicitud de autenticacion"
        def authenticationRequestDTO = AuthenticationRequestDTO
                .builder()
                .username(rut)
                .password("admin123")
                .build()

        when: "Autenticamos"
        authenticationService.auth(authorization, authenticationRequestDTO)

        then: "Validamos que venga la excepcion"
        thrown(ErrorNegocioException)

        where:
        rut             | authorization
        "11.111.111-1"  | ""
        "11.111.111-1"  | "a"
    }

    def "Generar autenticacion con error basic no permitido"() {
        given: "Una solicitud de autenticacion"
        def authorization = "Basic "+UUID.randomUUID().toString()
        def authenticationRequestDTO = AuthenticationRequestDTO
                .builder()
                .username("11.111.111-1")
                .password("admin123")
                .build()

        when: "Autenticamos"
        autorizacionRepository.findByBasic(_ as String) >> Optional.empty()
        authenticationService.auth(authorization, authenticationRequestDTO)

        then: "Validamos que venga la excepcion"
        thrown(ErrorNegocioException)
    }

    def "Generar autenticacion con error usuario no encontrado"() {
        given: "Una solicitud de autenticacion"
        def authorization = "Basic "+UUID.randomUUID().toString()
        def authenticationRequestDTO = AuthenticationRequestDTO
                .builder()
                .username("11.111.111-1")
                .password("admin123")
                .build()

        when: "Autenticamos"
        autorizacionRepository.findByBasic(_ as String) >> Optional.of(new Autorizacion())
        usuarioRepository.findByUsernameAndPassword(_ as String, _ as String) >> Optional.empty()
        authenticationService.auth(authorization, authenticationRequestDTO)

        then: "Validamos que venga la excepcion"
        thrown(ErrorNegocioException)
    }
}