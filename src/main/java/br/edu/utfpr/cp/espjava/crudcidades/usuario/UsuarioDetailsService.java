package br.edu.utfpr.cp.espjava.crudcidades.usuario;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        var usuario = usuarioRepository.findByNome(username);

        if(usuario == null) throw new UsernameNotFoundException("Usuario não encontrado na base de dados");
        return usuario;

    }
     
    
    
     
}
