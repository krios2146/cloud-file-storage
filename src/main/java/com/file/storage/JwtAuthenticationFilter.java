package com.file.storage;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    // TODO: 23-May-23 Secret isn't protected
    private static final String JWT_SECRET = "secret";

    protected JwtAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher, AuthenticationManager authenticationManager) {
        super(requiresAuthenticationRequestMatcher, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String header = request.getHeader(AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }

        String jwt = header.substring("Bearer ".length());

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(JWT_SECRET)).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(jwt);

        String username = decodedJWT.getSubject();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null);

        return getAuthenticationManager().authenticate(authenticationToken);
    }
}
