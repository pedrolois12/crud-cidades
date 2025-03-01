package br.edu.utfpr.cp.espjava.crudcidades.usuario;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Usuario implements Serializable, UserDetails{
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String senha;

    @ElementCollection(fetch=FetchType.EAGER)
    private List<String> papeis;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<String> getPapeis() {
        return papeis;
    }

    public String getSenha() {
        return senha;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPapeis(List<String> papeis) {
        this.papeis = papeis;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
       return this.papeis.stream().map(papelAtual-> new SimpleGrantedAuthority("ROLE_"+ papelAtual)).toList();
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
       return this.senha;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
       return this.nome;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
       return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
       return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
            return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    
}
