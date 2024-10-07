package cl.springframework.filter;

import cl.springframework.constants.Constants;
import cl.springframework.dto.UserDTO;
import cl.springframework.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        try {

            // Get authorization header and validate
            final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (ObjectUtils.isEmpty(authorization) || !authorization.toUpperCase().startsWith(Constants.BEARER.toUpperCase() + Constants.SPACE)) {
                chain.doFilter(request, response);
                return;
            }

            // Get jwt token and validate
            final String token = authorization.split(Constants.SPACE)[1].trim();

            final String rutToken = jwtTokenUtil.getClaimFromToken(token, "rut_cliente");
            final String roles = jwtTokenUtil.getClaimFromToken(token, "roles");

            if (!jwtTokenUtil.isValidToken(token)) {
                chain.doFilter(request, response);
                return;
            }

            final UserDTO userDetails = UserDTO
                    .builder()
                    .username("admin123")
                    .authorities(Collections.singletonList((GrantedAuthority) () -> "USER_ROLE"))
                    .build();

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            chain.doFilter(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}