package br.edu.utfpr.cp.espjava.crudcidades.infra;

import org.hibernate.type.format.jakartajson.JsonBJsonFormatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.gson.GsonBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;

@EnableWebSecurity
@Configuration
public class SecurityConfig{
    
    @Bean
    public PasswordEncoder passwordEncoder(){
       PasswordEncoder encoder = new BCryptPasswordEncoder(12);
       return encoder;
    }


    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    
    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception{
   
    
        
        return http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .formLogin((form)->form.disable())
        .authorizeHttpRequests((authorize)->{ 
                     authorize.requestMatchers("/")
                              .hasAnyRole("listar", "admin")
                              .requestMatchers("/criar", "/alterar", "/excluir", "/preparaAlterar")
                              .hasRole("admin")
                              .requestMatchers("teste/*").anonymous()
                              .requestMatchers("admin/*").hasAnyAuthority("ROLE_admin")
                              .requestMatchers("teste/autenticar", "login" ).permitAll();
                            }
        ).addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
        
        .build();

       
                          
    }


    @Bean
	public AuthenticationManager authenticationManager(
			UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);

		ProviderManager providerManager = new ProviderManager(authenticationProvider);
		providerManager.setEraseCredentialsAfterAuthentication(false);

		return providerManager;
	}



}
