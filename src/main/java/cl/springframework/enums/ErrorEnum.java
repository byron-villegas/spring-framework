package cl.springframework.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorEnum {

    EXT001("EXT001", "Error en username y password");

    private final String codigo;
    private final String mensaje;
}