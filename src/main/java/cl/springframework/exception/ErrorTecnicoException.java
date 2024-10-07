package cl.springframework.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class ErrorTecnicoException extends RuntimeException {
    private final String codigo;
    private final String mensaje;
}