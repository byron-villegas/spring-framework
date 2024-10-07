package cl.springframework.repository;

import cl.springframework.model.Usuario;
import cl.springframework.util.FileUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class UsuarioRepositoryImpl implements UsuarioRepository {
    private static final Gson gson = new Gson();
    private static final String USUARIOS_ARCHIVO = "data/usuarios.json";

    private List<Usuario> findAll() {
        String usuariosJson = FileUtil.getFileContent(USUARIOS_ARCHIVO);

        List<Usuario> usuarios = gson.fromJson(usuariosJson, new TypeToken<List<Usuario>>(){}.getType());

        return usuarios;
    }

    @Override
    public Optional<Usuario> findByUsernameAndPassword(String username, String password) {
        List<Usuario> usuarios = findAll();

        Optional<Usuario> usuarioOptional = usuarios
                .stream()
                .filter(usuario -> usuario.getUsername().equals(username) && usuario.getPassword().equals(password))
                .findFirst();

        return usuarioOptional;
    }
}
