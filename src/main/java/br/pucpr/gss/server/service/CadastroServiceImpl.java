package br.pucpr.gss.server.service;

import br.pucpr.gss.client.service.CadastroService;
import br.pucpr.gss.server.dao.*;
import br.pucpr.gss.server.model.Funcionario;
import br.pucpr.gss.shared.model.Usuario;
import br.pucpr.gss.shared.model.UsuarioLogin;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CadastroServiceImpl extends RemoteServiceServlet implements CadastroService {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public Usuario cadastrar(UsuarioLogin usuarioLogin) throws IllegalStateException, IllegalArgumentException {
        // Como é possível que o banco de dados não exista ainda, tentar criá-lo.
        Conexao.getInstance().criarBancoDeDados();

        // Verificar se email existe no banco do RH
        RhDao.Funcionario rhDaoFuncionario = new RhDaoFuncionarioImpl();
        Funcionario funcionario = rhDaoFuncionario.getFuncionarioByEmail(usuarioLogin.getEmail());

        if (funcionario != null) {
            // Usuário existe no banco do RH
            // Buscar se o usuário já está cadastrado no banco de dados GSS
            GssDao.Usuario gssDaoUsuario = new GssDaoUsuarioImpl();
            Usuario usuario = gssDaoUsuario.getUsuarioByIdFuncionario(funcionario.getId());

            if (usuario != null) {
                // Email já existe no banco do GSS
                throw new IllegalArgumentException("Email já cadastrado no GSS");
            } else {
                // Email não cadastrado no GSS ainda, verificar se esse é o primeiro cadastro para determinar se
                // usuário é administrador
                boolean isAdmin = gssDaoUsuario.getQuantidadeUsuarios() <= 0;

                usuario = new Usuario(funcionario.getNome(), usuarioLogin.getSenha(), isAdmin, funcionario.getId());
                gssDaoUsuario.insertUsuario(usuario);

                logger.log(Level.INFO, "Usuário cadastrado");

                return usuario;
            }
        } else {
            // Usuário não existe no banco do RH
            throw new IllegalArgumentException("Email não cadastrado no RH");
        }
    }
}