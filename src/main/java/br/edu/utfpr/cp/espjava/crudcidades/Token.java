package br.edu.utfpr.cp.espjava.crudcidades;

import java.time.LocalDateTime;

public record Token(String authorization, LocalDateTime createdAt, LocalDateTime expiredAt) {
    
}
