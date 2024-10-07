package cl.springframework.service

import cl.springframework.dto.ProductoRequestDTO
import cl.springframework.model.Caracteristica
import cl.springframework.model.Producto
import cl.springframework.repository.ProductoRepository
import spock.lang.Specification

class ProductoServiceSpec extends Specification {
    def productoRepository = Stub(ProductoRepository)
    ProductoService productoService = new ProductoService(productoRepository)

    def "findAll"() {
        given: "Una solicitud de consulta general"
        def producto = Producto
                .builder()
                .id(1)
                .sku(1)
                .nombre("Ejemplo")
                .descripcion("Ejemplo")
                .precio(100.0)
                .caracteristicas(Arrays.asList(Caracteristica
                        .builder()
                        .titulo("Modelo")
                        .valor("Ejemplo")
                        .build()))
                .build()
        def productos = Arrays.asList(producto)

        when: "Consultamos"
        productoRepository.findAll() >> productos
        def response = productoService.findAll()

        then: "Validamos que sea de manera correcta"
        response != null
        !response.isEmpty()
    }

    def "findById is present"() {
        given: "Una solicitud para encontrar por id"
        def idProducto = 1
        def producto = Producto
                .builder()
                .id(1)
                .sku(1)
                .nombre("Ejemplo")
                .descripcion("Ejemplo")
                .precio(100.0)
                .caracteristicas(Arrays.asList(Caracteristica
                        .builder()
                        .titulo("Modelo")
                        .valor("Ejemplo")
                        .build()))
                .build()

        when: "Consultamos"
        productoRepository.findBySku(_ as Long) >> Optional.of(producto)
        def response = productoService.findBySku(idProducto)

        then: "Validamos que sea de manera correcta"
        response.isPresent()
    }

    def "findBySku is not present"() {
        given: "Una solicitud para encontrar por id"
        def idProducto = 1

        when: "Consultamos"
        productoRepository.findBySku(_ as Long) >> Optional.empty()
        def response = productoService.findBySku(idProducto)

        then: "Validamos que sea de manera correcta"
        !response.isPresent()
    }

    def "save"() {
        given: "Una solicitud para guardar"
        def productoDTO = ProductoRequestDTO
                .builder()
                .sku(sku)
                .nombre(nombre)
                .descripcion(descripcion)
                .precio(precio)
                .build()
        def productoCreado = Producto
                .builder()
                .id(id)
                .sku(sku)
                .nombre(nombre)
                .descripcion(descripcion)
                .precio(precio)
                .build()

        when: "Creamos"
        productoRepository.save(_ as Producto) >> productoCreado
        def response = productoService.save(productoDTO)

        then: "Validamos la respuesta"
        response == respuesta

        where:
        id       | sku     | nombre             | descripcion         | precio    | respuesta
        11111111 | 2222222 | "Gameboy Advance"  | "Nintendo Portatil" | 100_000.0 | true
        0        | 2222222 | "Gameboy Advance"  | "Nintendo Portatil" | 100_000.0 | false
    }
}