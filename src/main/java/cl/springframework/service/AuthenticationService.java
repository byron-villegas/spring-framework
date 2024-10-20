package cl.springframework.service;

import cl.springframework.constants.Constants;
import cl.springframework.dto.AuthenticationRequestDTO;
import cl.springframework.dto.AuthenticationResponseDTO;
import cl.springframework.enums.ErrorEnum;
import cl.springframework.exception.ErrorNegocioException;
import cl.springframework.model.Autorizacion;
import cl.springframework.model.Usuario;
import cl.springframework.repository.AutorizacionRepository;
import cl.springframework.repository.UsuarioRepository;
import cl.springframework.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AutorizacionRepository autorizacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthenticationResponseDTO auth(final String authorization, final AuthenticationRequestDTO authenticationRequestDTO) {
        log.info("Auth [{}]", authenticationRequestDTO);

        if(ObjectUtils.isEmpty(authorization) || !authorization.toUpperCase().startsWith(Constants.BASIC.toUpperCase() + Constants.SPACE)) {
            throw new ErrorNegocioException(ErrorEnum.EXT001.getCodigo(), ErrorEnum.EXT001.getMensaje());
        }

        final String basic = authorization.split(" ")[1];
        final Optional<Autorizacion> autorizacionOptional = autorizacionRepository.findByBasic(basic);

        if(!autorizacionOptional.isPresent()) {
            throw new ErrorNegocioException(ErrorEnum.EXT001.getCodigo(), ErrorEnum.EXT001.getMensaje());
        }

        final Optional<Usuario> usuarioOptional = usuarioRepository.findByUsernameAndPassword(authenticationRequestDTO.getUsername(), authenticationRequestDTO.getPassword());

        if(!usuarioOptional.isPresent()) {
            throw new ErrorNegocioException(ErrorEnum.EXT001.getCodigo(), ErrorEnum.EXT001.getMensaje());
        }

        final Autorizacion autorizacion = autorizacionOptional.get();
        final List<String> roles = Collections.singletonList(autorizacion.getRol());
        final Usuario usuario = usuarioOptional.get();

        final String token = jwtTokenUtil.generateToken(usuario.getRut(), usuario.getUsername(), roles);
        final int expiration = jwtTokenUtil.getExpirationFromToken(token);

        return AuthenticationResponseDTO
                .builder()
                .accessToken(token)
                .expiration(expiration)
                .roles(roles)
                .build();
    }
}