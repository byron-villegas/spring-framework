package cl.springframework.exception.handler;

import cl.springframework.dto.ErrorDTO;
import cl.springframework.dto.ValidacionesParametrosErrorDTO;
import cl.springframework.exception.ErrorNegocioException;
import cl.springframework.exception.ErrorTecnicoException;
import cl.springframework.util.ValidacionesParametrosUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.validation.ConstraintViolationException;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {
    private static final String ERROR = "Error";
    private static final String ERROR_DEL_SERVIDOR = "Error del servidor";

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException constraintViolationException) {
        constraintViolationException.getConstraintViolations();
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        final ValidacionesParametrosErrorDTO validacionesParametrosErrorDTO = ValidacionesParametrosErrorDTO
                .builder()
                .mensaje("Error en los parametros de entrada")
                .detalle(ValidacionesParametrosUtil.generarDetallesParametrosErrores(methodArgumentNotValidException.getFieldErrors()))
                .build();

        return new ResponseEntity<>(validacionesParametrosErrorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(Exception exception) {
        log.error("Exception [{}, {}]", exception.getMessage(), exception.getLocalizedMessage());
        final ErrorDTO errorDTO = ErrorDTO
                .builder()
                .codigo(ERROR)
                .mensaje(ERROR_DEL_SERVIDOR)
                .build();
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ErrorNegocioException.class)
    protected ResponseEntity<Object> handleErrorNegocioException(ErrorNegocioException errorNegocioException) {
        final ErrorDTO errorDTO = ErrorDTO
                .builder()
                .codigo(errorNegocioException.getCodigo())
                .mensaje(errorNegocioException.getMensaje())
                .build();
        return new ResponseEntity<>(errorDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ErrorTecnicoException.class)
    protected ResponseEntity<Object> handleErrorTecnicoException(ErrorTecnicoException errorTecnicoException) {
        final ErrorDTO errorDTO = ErrorDTO
                .builder()
                .codigo(errorTecnicoException.getCodigo())
                .mensaje(ERROR_DEL_SERVIDOR)
                .build();
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}