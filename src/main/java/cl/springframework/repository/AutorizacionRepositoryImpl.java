package cl.springframework.repository;

import cl.springframework.model.Autorizacion;
import cl.springframework.util.FileUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AutorizacionRepositoryImpl implements AutorizacionRepository {
    private static final Gson gson = new Gson();
    private static final String AUTORIZACIONES_ARCHIVO = "data/autorizaciones.json";

    private List<Autorizacion> findAll() {
        String autorizacionesJson = FileUtil.getFileContent(AUTORIZACIONES_ARCHIVO);

        List<Autorizacion> autorizaciones = gson.fromJson(autorizacionesJson, new TypeToken<List<Autorizacion>>(){}.getType());

        return autorizaciones;
    }

    @Override
    public Optional<Autorizacion> findByBasic(String basic) {
        List<Autorizacion> autorizaciones = findAll();

        Optional<Autorizacion> autorizacionOptional = autorizaciones
                .stream()
                .filter(autorizacion -> autorizacion.getBasic().equals(basic))
                .findFirst();

        return autorizacionOptional;
    }
}
