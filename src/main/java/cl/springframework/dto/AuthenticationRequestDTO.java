package cl.springframework.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthenticationRequestDTO {
    @NotBlank(message = "El username es obligatorio")
    @Size(max = 12, message = "El username no puede superar el maximo de 12 caracteres")
    @Pattern(regexp = "[0-9aA-zZ.-]+", message = "El username solo admite numeros, letras, puntos y guiones")
    private String username;

    @ToString.Exclude
    @NotBlank(message = "El password es obligatorio")
    @Size(max = 10, message = "El password no puede superar el maximo de 10 caracteres")
    private String password;
}