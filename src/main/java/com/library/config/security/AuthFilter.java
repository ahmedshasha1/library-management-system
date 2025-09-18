package com.library.config.security;

import com.library.model.auth.User;
import com.library.service.security.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class AuthFilter  extends OncePerRequestFilter {
    @Autowired
    private JwtTokenHandler jwtTokenHandler;
    @Autowired
    private UserService userService;


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        if (path.contains("login") || path.contains("sign-up") ||
                path.contains("/v3/api-docs") || path.contains("/swagger-ui")) {
            return true;
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if(token == null || !token.contains("Bearer ")  ){
            response.reset();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        token = token.substring(7);
        if (!jwtTokenHandler.validToken(token)) {
            response.reset();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {

        User user = userService.checkClientExistByToken(token);
        if (user == null) {
            response.reset();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_"+user.getRole().toString());

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, Collections.singletonList(grantedAuthority));

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        filterChain.doFilter(request, response);

    }catch (ServletException e) {
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            System.out.println("Error in AuthFilter");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }




}
