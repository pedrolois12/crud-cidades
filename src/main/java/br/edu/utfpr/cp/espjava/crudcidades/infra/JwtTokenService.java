package br.edu.utfpr.cp.espjava.crudcidades.infra;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;

import br.edu.utfpr.cp.espjava.crudcidades.Token;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.ZoneIdConverter;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonGenerator;

import br.edu.utfpr.cp.espjava.crudcidades.usuario.Usuario;
import ch.qos.logback.core.util.Duration;



@Service
public class JwtTokenService {
    private static final String  token = "K<AJGSDgdgsdjagsd78*A¨Sd6i8dgsdgkaH*ASD¨(*as5)";
    private static final String issuer = "pedro";

    public Token generateToken(String usuario){
            // System.out.println(ZoneOffset.getAvailableZoneIds());
            Instant createdAt = Instant.now();
            Algorithm algorithm = Algorithm.HMAC256(token);
            ZoneId zone = ZoneId.of("America/Sao_Paulo");
            Instant expiredAt = createdAt.plusSeconds(60);
            
            String auth = JWT.create().withIssuer(issuer)
            .withSubject(usuario)
            .withIssuedAt(createdAt)
            .withExpiresAt(expiredAt)
            .sign(algorithm);

            Token token = new Token(auth, LocalDateTime.ofInstant(createdAt, zone), LocalDateTime.ofInstant(expiredAt, zone) );
            

            return token;
            
          
    }


    public DecodedJWT validaToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(this.token);
        try{
            JWTVerifier verifier =  JWT.require(algorithm).withIssuer(issuer).build();
            DecodedJWT decode = verifier.verify(token);

            System.out.println(decode.getExpiresAt());

            return decode;
        } catch( JWTVerificationException e){
            System.err.println(e.getMessage());
            return null;
        }
    }


    
}
