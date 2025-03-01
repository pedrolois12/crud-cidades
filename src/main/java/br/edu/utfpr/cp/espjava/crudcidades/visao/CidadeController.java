package br.edu.utfpr.cp.espjava.crudcidades.visao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.auth0.jwt.interfaces.DecodedJWT;

import br.edu.utfpr.cp.espjava.crudcidades.infra.JwtTokenService;
import br.edu.utfpr.cp.espjava.crudcidades.Token;
import br.edu.utfpr.cp.espjava.crudcidades.usuario.Usuario;



@Controller
public class CidadeController {
    private List<Cidade> cidades;
    private Cidade cidadeAtual;
    private List<String> erros= new ArrayList<>();

    public CidadeController(){

        this.cidades = new ArrayList<>();

    }

    @ModelAttribute(name = "listaCidades")
    public List<Cidade> listaNomes(){
        return cidades;
    }

    @ModelAttribute(name="cidadeAtual")
    public Cidade setaCidadeAtual(){
        return this.cidadeAtual;
    }

    @ModelAttribute(name="erros")
    public List<String> returnErros(){
        return erros;
    }
    
    @GetMapping("/")
    public String listar() {
        
        return "/crud";
    }
    
    @Autowired
    public JwtTokenService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;



    @PostMapping("/criar")
    public String criar(@Valid Cidade cidade, BindingResult validacao){
        
        if(validacao.hasErrors()){
            erros.clear();
            validacao.getFieldErrors().forEach(e->{
                System.out.println(String.format("O atributo %s emitiu a seguinte mensagem %s", e.getField(), e.getDefaultMessage()));
                erros.add(e.getDefaultMessage());
            });
            
            return "redirect:/";
        }else{
            
            cidades.add(cidade);
            erros.clear();
            this.cidadeAtual = null;
            return "redirect:/";
        }
        
    }


    @GetMapping("/excluir")
    public String excluir(
        @RequestParam String nome,
        @RequestParam String estado){
            
            cidades.removeIf(cidadeAtual -> 
                cidadeAtual.getNome().equals(nome) &&
                cidadeAtual.getEstado().equals(estado));
            
            return "redirect:/";
        }


    @GetMapping("/preparaAlterar")
    public String preparaAlterar( 
        @RequestParam String nome,
        @RequestParam String estado,
        Model memoria
        ){

        cidades.stream().filter(cidade->
                cidade.getNome().equals(nome) &&
                cidade.getEstado().equals(estado)
        ).findAny().ifPresent(cidade->this.cidadeAtual = new Cidade(cidade.getNome(), cidade.getEstado()));

       
        return "redirect:/";
    }
    
    @GetMapping("/alterar")
    public String alterar(@Valid Cidade cidade, BindingResult bind) {
        
        System.out.println(cidade.getEstado());
        System.out.println(cidade.getNome());

        cidades.forEach(e->System.out.println(e.getEstado() + "- " +e.getNome() ));

        if(!bind.hasErrors()){
            cidades.removeIf(cidadeAtual -> 
                cidadeAtual.getNome().equals(this.cidadeAtual.getNome()) &&
                cidadeAtual.getEstado().equals(this.cidadeAtual.getEstado())
            );
        }

        criar(cidade, bind);
       
        return "redirect:/";
    }

    @GetMapping("teste/gerarToken")
    public ResponseEntity<Token> geraToken() {

        return new ResponseEntity<Token>(jwtService.generateToken("PEDRO.LOIS"), HttpStatusCode.valueOf(200));
        
    }
    

    @GetMapping("teste/validarToken")
    public ResponseEntity<DecodedJWT> validaToken(@RequestParam String param) {
        var valida = jwtService.validaToken(param);
        HttpStatus retorno;
        retorno = Objects.nonNull(valida) ? HttpStatus.ACCEPTED : HttpStatus.I_AM_A_TEAPOT ;

    
        return new ResponseEntity<DecodedJWT>(valida, retorno);
    
       
    }
    
    
    @PostMapping("login")
    public ResponseEntity<Token> postMethodName(@RequestBody Usuario user) {
        //TODO: process POST request
        Authentication auth = null;
        Token token = null;        
        try{
            auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getNome(), user.getPassword()));    
            token =  jwtService.generateToken(auth.getName());
        }catch( AuthenticationException e){
            System.err.println(e.getCause());
            return new ResponseEntity<>(new Token(null, null, null), HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<>(token, HttpStatus.ACCEPTED);
    }
    
    @GetMapping("admin/teste")
    public ResponseEntity<String> getMethodName() {
        return new ResponseEntity<>("Funcionou, voce está aunteticado", HttpStatus.ACCEPTED);
    }

    @GetMapping("chupacu/teste")
    public ResponseEntity<String> getChupacu() {
        return new ResponseEntity<>("Funcionou, voce está aunteticado", HttpStatus.ACCEPTED);
    }
    
    
}
