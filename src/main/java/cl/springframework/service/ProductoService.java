package cl.springframework.service;

import cl.springframework.dto.CaracteristicaDTO;
import cl.springframework.dto.ProductoRequestDTO;
import cl.springframework.dto.ProductoResponseDTO;
import cl.springframework.model.Producto;
import cl.springframework.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository productoRepository;

    public List<ProductoResponseDTO> findAll() {
        List<Producto> productos = productoRepository.findAll();

        log.info("Productos Encontrados [{}]", productos);

        List<ProductoResponseDTO> responseDTOS = productos
                .stream()
                .map(producto -> ProductoResponseDTO
                        .builder()
                        .sku(producto.getSku())
                        .nombre(producto.getNombre())
                        .descripcion(producto.getDescripcion())
                        .imagen(producto.getImagen())
                        .precio(producto.getPrecio())
                        .caracteristicas(producto
                                .getCaracteristicas()
                                .stream()
                                .map(caracteristica -> CaracteristicaDTO
                                        .builder()
                                        .titulo(caracteristica.getTitulo())
                                        .valor(caracteristica.getValor())
                                        .build())
                                .collect(Collectors.toList()))
                        .marca(producto.getMarca())
                        .build())
                .collect(Collectors.toList());

        log.info("Productos Transformados [{}]", responseDTOS);

        return responseDTOS;
    }

    public Optional<ProductoResponseDTO> findBySku(Long sku) {
        Optional<Producto> optionalProducto = productoRepository.findBySku(sku);

        log.info("Encontrar Producto por Sku [{}]", sku);

        if (!optionalProducto.isPresent()) {
            return Optional.empty();
        }

        Producto producto = optionalProducto.get();

        List<CaracteristicaDTO> caracteristicas = producto
                .getCaracteristicas()
                .stream()
                .map(caracteristica -> CaracteristicaDTO
                        .builder()
                        .titulo(caracteristica.getTitulo())
                        .valor(caracteristica.getValor())
                        .build())
                .collect(Collectors.toList());

        ProductoResponseDTO productoResponseDTO = ProductoResponseDTO
                .builder()
                .sku(producto.getSku())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .imagen(producto.getImagen())
                .precio(producto.getPrecio())
                .caracteristicas(caracteristicas)
                .marca(producto.getMarca())
                .build();

        log.info("Producto Encontrado [{}]", productoResponseDTO);

        return Optional.of(productoResponseDTO);
    }

    public boolean save(ProductoRequestDTO productoRequestDTO) {

        log.info("Guardar Producto [{}]", productoRequestDTO);

        Producto productoACrear = Producto
                .builder()
                .sku(productoRequestDTO.getSku())
                .nombre(productoRequestDTO.getNombre())
                .descripcion(productoRequestDTO.getDescripcion())
                .precio(productoRequestDTO.getPrecio())
                .build();

        log.info("Producto a Crear [{}]", productoACrear);

        Producto productoCreado = productoRepository.save(productoACrear);

        boolean creadoExitosamente = productoCreado.getId() > 0;

        log.info("Resultado [{}]", creadoExitosamente);

        return creadoExitosamente;
    }
}