package br.edu.utfpr.cp.espjava.crudcidades.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    public Usuario findByNome(String nome);
    
}
