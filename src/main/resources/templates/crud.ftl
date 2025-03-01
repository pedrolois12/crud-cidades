<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CRUD Cidades</title>


    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
</head>
<body>
    <nav class="navbar navbar-expand-sm bg-dark">
        <ul class="navbar-nav ml auto">
            <li class="nav-item"> 
                <a href="/logout" class="nav-link btn btn-secondary">Sair da aplicação</a>
            </li>
        </ul>
    
    </nav>

    <div class="container-fluid">
        <div class="jumbotron mt-5">
            <H1> GERENCIAMENTO DE CIDADES</H1>
            <p> UM CRUD PARA CRIAR, ALTERAR EXCLUIR E LISTAR CIDADES</p>
        </div>
    </div>
    <#if cidadeAtual??> <h1> ${(cidadeAtual.nome)!}</h1> </#if>
    <#if cidadeAtual??>
        <form action="/alterar">
        <input type="hidden" name="nomeAtual" value="${(cidadeAtual.nome)!}">
        <input type="hidden" name="estadoAtual" value="${(cidadeAtual.estado)!}">
    <#else>
        <form action="/criar" method="POST">
    </#if>
        <div class="form-group">
            <label for="nome">Cidade: </label>
            <input value= "${(cidadeAtual.nome)!}" name="nome" type="text" class="form-control" placeholder="informe o nome da cidade" id="cidade">
        </div> 
        <div class="form-gropup">
            <label for="estado"> Estado:</label>
            <input value= "${(cidadeAtual.estado)!}" name ="estado" type="text" class="form-control" placeholder="Informe o estado ao qual a cidade pertence" id="estado">
        </div>


         <#if cidadeAtual??>
            <button type="submit" class="btn btn-primary">CONCLUIR ALTERARAÇÃO </button>
        <#else>
            <button type="submit" class="btn btn-primary">CRIAR </button>
        </#if>
        
        <#if erros??>
            <#list erros as erro>
                <div class="alert alert-danger" role="alert">
                    ${erro}
                </div>
            </#list> 
        </#if>


     </form>   
     <table class="table table-striped table-hover mt-5">
        <thead class="table-dark">
            <tr>
                <th scope="col">Nome</th>
                <th scope="col">Estado</th>
                <th scope="col">Ações</th>
            </tr>
        </thead>
        <tbody>
            <#list listaCidades as cidade>
            <tr>
                <td>${cidade.nome}</td>
                <td>${cidade.estado}</td>
                <td>
                    <div class="d-flex d-justify-content-center">
                        <a href="/preparaAlterar?nome=${cidade.nome}&estado=${cidade.estado}" class="btn btn-warning mr-3">ALTERAR</a>
                        <a href="/excluir?nome=${cidade.nome}&estado=${cidade.estado}" class="btn btn-danger mr-3">EXCLUIR</a>
                    </div>
                </td>
            </tr>
            </#list>
        </tbody>
     </table>
</body>
</html>