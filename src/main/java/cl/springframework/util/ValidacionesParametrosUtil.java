package cl.springframework.util;

import cl.springframework.dto.DetalleParametroErrorDTO;
import org.springframework.validation.FieldError;
import java.util.List;
import java.util.stream.Collectors;

public class ValidacionesParametrosUtil {

    private ValidacionesParametrosUtil() {

    }

    public static List<DetalleParametroErrorDTO> generarDetallesParametrosErrores(List<FieldError> errores) {
        return errores
                .stream()
                .map(objectError -> DetalleParametroErrorDTO
                        .builder()
                        .id(objectError.getField())
                        .mensaje(objectError.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());
    }
}