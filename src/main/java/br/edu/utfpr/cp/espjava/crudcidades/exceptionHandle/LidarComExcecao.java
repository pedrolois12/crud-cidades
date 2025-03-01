package br.edu.utfpr.cp.espjava.crudcidades.exceptionHandle;

import java.nio.file.AccessDeniedException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.auth0.jwt.exceptions.JWTDecodeException;

public class LidarComExcecao {
    

    @ExceptionHandler(JWTDecodeException.class)
    public ResponseEntity trataTokenInvalido(){ return ResponseEntity.notFound().build();};

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity trataForbidden(AccessDeniedException e){ return ResponseEntity.status(403).body(e.getReason());};

}
