package cl.springframework.repository;

import cl.springframework.model.Producto;
import cl.springframework.util.FileUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class ProductoRepositoryImpl implements  ProductoRepository {
    private static final Gson gson = new Gson();
    private static final String PRODUCTOS_ARCHIVO = "data/productos.json";

    @Override
    public List<Producto> findAll() {
        String productosJson = FileUtil.getFileContent(PRODUCTOS_ARCHIVO);

        List<Producto> productos = gson.fromJson(productosJson, new TypeToken<List<Producto>>(){}.getType());

        return productos;
    }

    @Override
    public Optional<Producto> findBySku(Long sku) {
        List<Producto> productos = findAll();

        Optional<Producto> productoOptional = productos
                .stream()
                .filter(producto -> producto.getSku() == sku)
                .findFirst();

        return productoOptional;
    }

    @Override
    public Producto save(Producto producto) {
        List<Producto> productos = findAll();

        Long id = productos
                .stream()
                .max(Comparator.comparingLong(Producto::getId))
                .get()
                .getId() + 1;

        producto.setId(id);

        productos.add(producto);

        return producto;
    }
}
