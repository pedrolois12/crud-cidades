package br.edu.utfpr.cp.espjava.crudcidades.infra;
import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Order(1)
@Component
public class JwtRequestFilter extends OncePerRequestFilter{


    @Autowired
    JwtTokenService jwtTokenService;

    @Autowired
    UserDetailsService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
       
              
                String auth = request.getHeader("auth");
                if (!Objects.isNull(auth)){
                  DecodedJWT decode =   jwtTokenService.validaToken(auth);
                  if(Objects.isNull(decode)) throw new JWTDecodeException("Token invalido ou expirado");
                  UserDetails user = userService.loadUserByUsername(decode.getSubject());
                  Authentication authenc = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
                  SecurityContextHolder.getContext().setAuthentication(authenc);
                  filterChain.doFilter(request, response);

                }else{
                    
                    filterChain.doFilter(request, response);
                   
                }

    }

}