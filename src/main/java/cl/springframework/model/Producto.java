package cl.springframework.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Producto {
    private long id;
    private long sku;
    private String nombre;
    private String descripcion;
    private String imagen;
    private double precio;
    private String marca;
    private List<Caracteristica> caracteristicas;
}