package com.c8n.configuration.security;

import com.c8n.configuration.security.user.AuthUser;
import com.c8n.configuration.security.user.AuthUserService;
import com.c8n.model.Sessions;
import com.c8n.util.JwtTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenManager tokenManager;
    @Autowired
    private AuthUserService userService;
    @Autowired
    private Sessions sessions;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");


        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ") && SecurityContextHolder.getContext().getAuthentication()==null){
            final String token = authorizationHeader.substring(7);
            Optional<String> userNameOptional = tokenManager.decodeUserId(token);
            if (userNameOptional.isPresent()){
                String userName = userNameOptional.get();
                AuthUser user = sessions.getUsers().get(userName); // get user details from cache
                if (user == null){
                    user = (AuthUser) userService.loadUserByUsername(userName);
                    if (user != null)
                        sessions.getUsers().put(userName, user);

                }
                if (user != null){
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
