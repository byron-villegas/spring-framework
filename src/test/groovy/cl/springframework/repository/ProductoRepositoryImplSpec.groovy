package cl.springframework.repository

import cl.springframework.model.Producto
import spock.lang.Specification

class ProductoRepositoryImplSpec extends Specification {
    def productoRepositoryImpl = new ProductoRepositoryImpl()

    def "FindAll"() {
        given: "Una solicitud de obtener todos"

        when: "Obtenemos"
        def response = productoRepositoryImpl.findAll()

        then: "Validamos que sea de manera correcta"
        !response.isEmpty()
    }

    def "FindBySku"() {
        given: "Un sku"

        when: "Encontramos"
        def response = productoRepositoryImpl.findBySku(sku)

        then: "Validamos que sea de manera correcta"
        response.isPresent() == present

        where:
        sku       | present
        15207410L | true
        322342L   | false
    }

    def "Save"() {
        given: "Un producto"
        def producto = new Producto()

        when: "Guardamos"
        def response = productoRepositoryImpl.save(producto)

        then: "Validamos que sea de manera correcta"
        response != null
    }
}