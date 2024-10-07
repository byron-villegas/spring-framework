package cl.springframework.controller;

import cl.springframework.dto.ProductoRequestDTO;
import cl.springframework.dto.ProductoResponseDTO;
import cl.springframework.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("productos")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService productoService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductoResponseDTO> findAll() {
        return productoService.findAll();
    }

    @GetMapping(value = "{sku}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductoResponseDTO> findBySku(@PathVariable Long sku) {
        Optional<ProductoResponseDTO> optionalProducto = productoService.findBySku(sku);
        return optionalProducto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> save(@RequestBody ProductoRequestDTO productoRequestDTO) {
        boolean fueCreado = productoService.save(productoRequestDTO);

        HttpStatus resultado = fueCreado ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity.status(resultado).build();
    }
}