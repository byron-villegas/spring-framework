package cl.springframework.controller

import cl.springframework.dto.AuthenticationRequestDTO
import cl.springframework.service.AuthenticationService
import com.google.gson.Gson
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class AuthenticationControllerSpec extends Specification {
    def authenticationService = Stub(AuthenticationService)
    def mockMvc = MockMvcBuilders.standaloneSetup(new AuthenticationController(authenticationService)).build()
    def gson = new Gson()

    def setup() {

    }

    def "Generar login"() {
        given: "Una solicitud autenticacion de login"
        def authenticationRequestDTO = AuthenticationRequestDTO
                .builder()
                .username("11.111.111-1")
                .password("admin123")
                .build()

        when: "Autenticamos"
        def response = mockMvc.perform(MockMvcRequestBuilders.post("/oauth/token")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Basic "+UUID.randomUUID().toString())
                .content(gson.toJson(authenticationRequestDTO)))
                .andReturn().response

        then: "Validamos que sea de manera correcta"
        response.status == HttpStatus.OK.value()
    }
}