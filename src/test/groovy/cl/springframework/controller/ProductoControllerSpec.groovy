package cl.springframework.controller

import cl.springframework.dto.ProductoRequestDTO
import cl.springframework.dto.ProductoResponseDTO
import cl.springframework.service.ProductoService
import com.google.gson.Gson
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class ProductoControllerSpec extends Specification {
    def productoService = Mock(ProductoService)
    def mvc = MockMvcBuilders.standaloneSetup(new ProductoController(productoService)).build()
    def gson = new Gson()

    def setup() {

    }

    def "Obtener todos los productos"() {
        given: "Una solicitud de obtencion de todos los productos"

        when: "Obtenemos"
        def response = mvc.perform(MockMvcRequestBuilders.get("/productos")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().response

        then: "Validamos que sea de manera correcta"
        response.status == HttpStatus.OK.value()
    }

    def "Obtener producto mediante sku OK"() {
        given: "Una solicitud de obtener producto mediante sku"
        def sku = 234242244L

        when: "Obtenemos"
        productoService.findBySku(_ as Long) >> Optional.of(ProductoResponseDTO.builder().build())
        def response = mvc.perform(MockMvcRequestBuilders.get("/productos/{sku}", sku)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().response

        then: "Validamos que sea de manera correcta"
        response.status == HttpStatus.OK.value()
    }

    def "Obtener producto mediante sku sin respuesta"() {
        given: "Una solicitud de obtener producto mediante sku"
        def sku = 234242244L

        when: "Obtenemos"
        productoService.findBySku(_ as Long) >> Optional.empty()
        def response = mvc.perform(MockMvcRequestBuilders.get("/productos/{sku}", sku)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().response

        then: "Validamos que venga el error not found"
        response.status == HttpStatus.NOT_FOUND.value()
    }

    def "Guardar producto OK"() {
        given: "Una solicitud de creacion de producto"
        def productoRequestDTO = ProductoRequestDTO
                .builder()
                .build()

        when: "Creamos"
        productoService.save(_ as ProductoRequestDTO) >> true
        def response = mvc.perform(MockMvcRequestBuilders.post("/productos")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(productoRequestDTO)))
                .andReturn().response

        then: "Validamos que sea de manera correcta"
        response.status == HttpStatus.CREATED.value()
    }

    def "Guardar producto con error"() {
        given: "Una solicitud de creacion de producto"
        def productoRequestDTO = ProductoRequestDTO
                .builder()
                .build()

        when: "Creamos"
        productoService.save(_ as ProductoRequestDTO) >> false
        def response = mvc.perform(MockMvcRequestBuilders.post("/productos")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(productoRequestDTO)))
                .andReturn().response

        then: "Validamos que venga el error"
        response.status == HttpStatus.INTERNAL_SERVER_ERROR.value()
    }
}