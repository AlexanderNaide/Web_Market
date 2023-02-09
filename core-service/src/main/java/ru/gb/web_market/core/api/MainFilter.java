package ru.gb.web_market.core.api;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.gb.web_market.core.services.JwtService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class MainFilter extends OncePerRequestFilter/*implements Filter*/ {

    private JwtService jwtService;
    @Autowired
    public void setJwtService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("\n\nВходящие данные:\n");
        System.out.println("URL:");
        System.out.println(request.getRequestURI());
        System.out.println("Parameters:");
        request.getParameterMap().forEach((a, e) -> System.out.println("[" + a + " " + Arrays.toString(e) + "]"));
        System.out.println(request.getHeader("Authorization"));

        response.setCharacterEncoding("UTF-8");

        String authorizationHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorizationHeaderValue != null && authorizationHeaderValue.startsWith("Bearer ")){
            String bearerTokenValue = authorizationHeaderValue.substring(7);
            String username = jwtService.getUsername(bearerTokenValue);
            List<GrantedAuthority> authorityList = jwtService.getAuthorities(bearerTokenValue);
            if(Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())){
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, authorityList));
            }
        }

        filterChain.doFilter(request, response);
    }
}