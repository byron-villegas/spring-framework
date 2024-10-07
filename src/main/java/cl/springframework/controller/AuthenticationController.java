package cl.springframework.controller;

import cl.springframework.dto.AuthenticationRequestDTO;
import cl.springframework.dto.AuthenticationResponseDTO;
import cl.springframework.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("oauth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping(value = "token", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthenticationResponseDTO auth(@RequestHeader(value = "authorization") String authorization, @Valid @RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        return authenticationService.auth(authorization, authenticationRequestDTO);
    }
}