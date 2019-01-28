package br.pucpr.gss.server.serviceImpl;

import br.pucpr.gss.client.service.CadastroService;
import br.pucpr.gss.server.dao.Conexao;
import br.pucpr.gss.shared.model.Resposta;
import br.pucpr.gss.shared.model.Usuario;
import br.pucpr.gss.shared.model.UsuarioLogin;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.user.server.rpc.UnexpectedException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CadastroServiceImpl extends RemoteServiceServlet implements CadastroService {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public Resposta<Usuario> cadastrar(UsuarioLogin usuarioLogin) throws IllegalStateException, IllegalArgumentException {
        // Como é possível que o banco de dados não exista ainda, tentar criá-lo.
        Conexao.criarBancoDeDados();

        // Verificar se email existe no banco do RH
        // language=MySQL
        String sql = String.format("SELECT * FROM funcionario WHERE (email) = ('%s');", usuarioLogin.getEmail());

        ResultSet resultado = Conexao.executeSQLQueryRH(sql);
        try {
            if (resultado.next()) {
                // Usuário existe no banco do RH
                final int idFuncionario = resultado.getInt(1);
                final String nome = resultado.getString(3);
                Conexao.closeConnection(null, null, resultado);

                // Buscar se o usuário já está cadastrado no banco de dados GSS
                // language=MySQL
                sql = String.format("SELECT * FROM usuario WHERE (funcionario_id) = ('%s');", idFuncionario);
                resultado = Conexao.executeSQLQueryGSS(sql);

                if (resultado.next()) {
                    Conexao.closeConnection(null, null, resultado);
                    // Email já existe no banco do GSS
                    throw new IllegalArgumentException("Email já cadastrado no GSS");
                } else {
                    Conexao.closeConnection(null, null, resultado);

                    // Email não cadastrado no GSS ainda, verificar se esse é o primeiro cadastro para determinar se
                    // usuário é administrador
                    // language=MySQL
                    sql = String.format("SELECT * FROM usuario");
                    resultado = Conexao.executeSQLQueryGSS(sql);

                    boolean isAdmin;
                    if (resultado.next()) {
                        // Já existem usuários no banco de dados
                        isAdmin = false;
                    } else {
                        // Primeiro usuário, considerar como administrador
                        isAdmin = true;
                    }
                    Conexao.closeConnection(null, null, resultado);

                    // Cadastrar usuário
                    // language=MySQL
                    sql = String.format("INSERT INTO usuario (nome, senha, isAdmin, funcionario_id) " +
                            "VALUES ('%s','%s',%b, '%s');", nome, usuarioLogin.getSenha(), isAdmin, idFuncionario);
                    Conexao.executeSQLUpdateGSS(sql);
                }
            } else {
                Conexao.closeConnection(null, null, resultado);
                // Usuário não existe no banco do RH
                throw new IllegalArgumentException("Email não cadastrado no RH");
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Não foi possível ler resultado", e);

            throw new IllegalStateException("Ocorreu um erro inesperado, tente novamente mais tarde");
        }
        return new Resposta<>();
    }
}