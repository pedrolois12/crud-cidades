package br.edu.utfpr.cp.espjava.crudcidades.visao;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public final class Cidade {


    @NotEmpty(message = "{message.nome.vazio}")
    private final String nome;

    @NotBlank(message = "{message.estado.vazio}")
    @Length(max = 2, min=2, message = "{message.estado.length}")
    private final String estado;
    
    public Cidade(final String nome, final String estado){
        this.nome = nome;
        this.estado = estado;
    }

    public String getNome() {
        return nome;
    }

    public String getEstado() {
        return estado;
        
    }

}
