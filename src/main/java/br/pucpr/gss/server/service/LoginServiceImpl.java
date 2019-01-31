package br.pucpr.gss.server.service;

import br.pucpr.gss.client.service.LoginService;
import br.pucpr.gss.server.dao.*;
import br.pucpr.gss.server.model.Funcionario;
import br.pucpr.gss.shared.model.Usuario;
import br.pucpr.gss.shared.model.UsuarioLogin;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public Usuario login(UsuarioLogin usuarioLogin) throws IllegalStateException, IllegalArgumentException {
        // Como é possível que o banco de dados não exista ainda, tentar criá-lo.
        Conexao.getInstance().criarBancoDeDados();

        // Buscar usuário através do email no banco do RH
        RhDao.Funcionario rhDaoFuncionario = new RhDaoFuncionarioImpl();
        Funcionario funcionario = rhDaoFuncionario.getFuncionarioByEmail(usuarioLogin.getEmail());

        if (funcionario != null) {
            // Autenticar o usuário utilizando o id e a senha
            GssDao.Usuario gssDaoUsuario = new GssDaoUsuarioImpl();
            Usuario usuario = gssDaoUsuario.getUsuarioByIdFuncionarioESenha(funcionario.getId(), usuarioLogin.getSenha());

            if (usuario != null) {
                logger.log(Level.INFO, "Login realizado");
                return usuario;
            }
        }

        logger.log(Level.WARNING, "Falha no login");

        throw new IllegalArgumentException("Email e/ou senha inválidos");
    }
}