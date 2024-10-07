package cl.springframework.repository;

import cl.springframework.model.Autorizacion;
import java.util.Optional;


public interface AutorizacionRepository {
    Optional<Autorizacion> findByBasic(String basic);
}
