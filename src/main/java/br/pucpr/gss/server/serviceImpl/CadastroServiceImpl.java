package br.pucpr.gss.server.serviceImpl;

import br.pucpr.gss.client.service.CadastroService;
import br.pucpr.gss.server.dao.Conexao;
import br.pucpr.gss.shared.model.Resposta;
import br.pucpr.gss.shared.model.Usuario;
import br.pucpr.gss.shared.model.UsuarioLogin;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class CadastroServiceImpl extends RemoteServiceServlet implements CadastroService {

    @Override
    public Resposta<Usuario> cadastrar(UsuarioLogin usuarioLogin) {
        // Como é possível que o banco de dados não exista ainda, tentar criá-lo.
        GWT.log("Banco de dados criado: " + Conexao.criarBancoDeDados());
        // Buscar se o usuário já não está cadastrado no banco de dados GSS
        // language=MySQL
        String sql = String.format("SELECT * FROM funcionario WHERE (email) = (%s);", usuarioLogin.getEmail());

        // TODO: 26/01/2019 Buscar o usuário no banco RH através do email, pegar o id e buscar no banco GSS
        //  se funcionario_id existe
        return null;
    }
}