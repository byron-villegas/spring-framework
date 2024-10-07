package cl.springframework.util;

import cl.springframework.configuration.JwtConfiguration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    private final JwtConfiguration jwtConfiguration;

    private static final String USERNAME = "username";
    private static final String ROLES = "roles";
    private static final String RUT_CLIENTE = "rut_cliente";
    private static final String APERTURA_CORCHETE = "[";
    private static final String COMA = ",";
    private static final String ESPACIO = " ";
    private static final String CIERRE_CORCHETE = "]";
    private static final int MIL = 1000;
    private static final String EXP = "exp";

    public String generateToken(final String rutCliente, final String username, final List<String> roles) {
        return createToken(rutCliente, username, roles);
    }

    private String createToken(final String rutCliente, final String username, final List<String> roles) {
        final Map<String, Object> claims = new HashMap<>();
        claims.put(RUT_CLIENTE, rutCliente);
        claims.put(USERNAME, username);
        claims.put(ROLES, generateRoles(roles));

        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (jwtConfiguration.getDuration() * jwtConfiguration.getDuration()) * MIL))
                .signWith(SignatureAlgorithm.HS512, jwtConfiguration.getSecret())
                .compact();
    }

    private String generateRoles(final List<String> roles) {
        StringBuilder rolesFormateados = new StringBuilder(APERTURA_CORCHETE);

        for (int i = 0; i < roles.size(); i++) {
            if (i != (roles.size() - 1)) {
                rolesFormateados.append(roles.get(i)).append(COMA + ESPACIO);
            } else {
                rolesFormateados.append(roles.get(i)).append(CIERRE_CORCHETE);
            }
        }

        return roles.toString();
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(jwtConfiguration.getSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    public String getClaimFromToken(String token, String key) {
        return getAllClaimsFromToken(token).get(key).toString();
    }

    public int getExpirationFromToken(String token) {
        return Integer.parseInt(getAllClaimsFromToken(token).get(EXP).toString());
    }

    public boolean isValidToken(String token) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(jwtConfiguration.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (SignatureException | ExpiredJwtException ex) {
            return false;
        }
    }
}