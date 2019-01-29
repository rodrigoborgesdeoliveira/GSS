package br.pucpr.gss.server.dao;

import br.pucpr.gss.shared.model.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GssDaoUsuarioImpl implements GssDao.Usuario {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public Usuario getUsuarioByIdFuncionario(int idFuncionario) {
        // language=MySQL
        String sql = String.format("SELECT * FROM usuario WHERE (funcionario_id) = ('%s');", idFuncionario);
        ResultSet resultado = Conexao.getInstance().executeSQLQueryGSS(sql);

        try {
            if (resultado != null && resultado.next()) {
                int id = resultado.getInt(1);
                String nome = resultado.getString(2);
                String senha = resultado.getString(3);
                boolean isAdmin = resultado.getBoolean(4);

                return new Usuario(id, nome, senha, isAdmin, idFuncionario);
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Não foi possível ler resultado", e);

            throw new IllegalStateException("Ocorreu um erro inesperado, tente novamente mais tarde");
        } finally {
            Conexao.getInstance().closeConnection(null, null, resultado);
        }

        return null;
    }

    @Override
    public int getQuantidadeUsuarios() {
        // language=MySQL
        String sql = "SELECT COUNT(id) FROM usuario;";
        ResultSet resultado = Conexao.getInstance().executeSQLQueryGSS(sql);

        try {
            if (resultado != null && resultado.next()) {
                return resultado.getInt(1);
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Não foi possível ler resultado", e);

            throw new IllegalStateException("Ocorreu um erro inesperado, tente novamente mais tarde");
        } finally {
            Conexao.getInstance().closeConnection(null, null, resultado);
        }

        return 0;
    }

    @Override
    public void insertUsuario(Usuario usuario) {
        // language=MySQL
        String sql = String.format("INSERT INTO usuario (nome, senha, isAdmin, funcionario_id) VALUES " +
                "('%s', SHA2('%s', 256), %b, %d);", usuario.getNome(), usuario.getSenha(), usuario.isAdmin(), usuario.getIdFuncionario());
        Conexao.getInstance().executeSQLUpdateGSS(sql);
    }
}
