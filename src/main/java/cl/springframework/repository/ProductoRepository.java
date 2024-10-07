package cl.springframework.repository;

import cl.springframework.model.Producto;
import java.util.List;
import java.util.Optional;


public interface ProductoRepository {
    List<Producto> findAll();

    Optional<Producto> findBySku(Long sku);

    Producto save(Producto producto);
}