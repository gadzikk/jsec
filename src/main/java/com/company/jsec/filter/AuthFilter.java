package com.company.jsec.filter;

import com.company.jsec.service.JwtService;
import com.company.jsec.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class AuthFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private UserService userService;

    public AuthFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var header = request.getHeader("Authorization");
        if (header == null) {
            filterChain.doFilter(request, response);
            return;
        }
        var token = header.replaceAll("Bearer ", "");
        var email = jwtService.extractEmail(token);
        log.info("EMAIL:EMAIL:EMAIL {}", email);

        var user = userService.loadUserByUsername(email);

        var isTokenValid = jwtService.validateToken(token, user);

        if (isTokenValid) {
            UsernamePasswordAuthenticationToken upAuthToken = new UsernamePasswordAuthenticationToken(user, null, List.of());
            upAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(upAuthToken);
            log.info("SC:SC:SC {}", SecurityContextHolder.getContext().getAuthentication());
        }
        filterChain.doFilter(request, response);
    }
}
