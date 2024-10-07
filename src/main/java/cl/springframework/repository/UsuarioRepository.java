package cl.springframework.repository;

import cl.springframework.model.Usuario;
import org.springframework.stereotype.Component;
import java.util.Optional;


public interface UsuarioRepository {
    Optional<Usuario> findByUsernameAndPassword(String username, String password);
}