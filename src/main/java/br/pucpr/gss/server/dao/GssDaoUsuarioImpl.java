package br.pucpr.gss.server.dao;

import br.pucpr.gss.shared.model.Usuario;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GssDaoUsuarioImpl implements GssDao.Usuario {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Override
    @Nullable
    public Usuario getUsuarioById(int id) {
        // language=MySQL
        String sql = String.format("SELECT * FROM usuario WHERE id = %d;", id);
        return getUsuario(sql);
    }

    @Override
    @Nullable
    public Usuario getUsuarioByIdFuncionario(int idFuncionario) throws IllegalStateException {
        // language=MySQL
        String sql = String.format("SELECT * FROM usuario WHERE (funcionario_id) = ('%s');", idFuncionario);
        return getUsuario(sql);
    }

    @Override
    @Nullable
    public Usuario getUsuarioByIdFuncionarioESenha(int idFuncionario, String senha) throws IllegalStateException {
        // language=MySQL
        String sql = String.format("SELECT * FROM usuario WHERE (funcionario_id, senha) = ('%s', SHA2('%s', 256));",
                idFuncionario, senha);
        return getUsuario(sql);
    }

    @Nullable
    private Usuario getUsuario(String sql) throws IllegalStateException {
        ResultSet resultado = null;
        try {
            resultado = Conexao.getInstance().executeSQLQueryGSS(sql);

            if (resultado != null && resultado.next()) {
                int id = resultado.getInt("id");
                String nome = resultado.getString("nome");
                boolean isAdmin = resultado.getBoolean("isAdmin");
                int idFuncionario = resultado.getInt("funcionario_id");

                return new Usuario(id, nome, isAdmin, idFuncionario);
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Não foi possível ler resultado", e);

            throw new IllegalStateException("Ocorreu um erro inesperado, tente novamente mais tarde");
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Erro com o banco de dados", ex);

            throw new IllegalStateException("Ocorreu um erro inesperado, tente novamente mais tarde");
        } finally {
            Conexao.getInstance().closeConnection(null, null, resultado);
        }

        return null;
    }

    @Override
    public int getQuantidadeUsuarios() throws IllegalStateException {
        // language=MySQL
        String sql = "SELECT COUNT(id) FROM usuario;";
        ResultSet resultado = null;
        try {
            resultado = Conexao.getInstance().executeSQLQueryGSS(sql);

            if (resultado != null && resultado.next()) {
                return resultado.getInt(1);
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Não foi possível ler resultado", e);

            throw new IllegalStateException("Ocorreu um erro inesperado, tente novamente mais tarde");
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Erro com o banco de dados", ex);

            throw new IllegalStateException("Ocorreu um erro inesperado, tente novamente mais tarde");
        } finally {
            Conexao.getInstance().closeConnection(null, null, resultado);
        }

        return 0;
    }

    @Override
    public void insertUsuario(Usuario usuario) throws IllegalStateException {
        // language=MySQL
        String sql = String.format("INSERT INTO usuario (nome, senha, isAdmin, funcionario_id) VALUES " +
                        "('%s', SHA2('%s', 256), %b, %d);", usuario.getNome(), usuario.getSenha(), usuario.isAdmin(),
                usuario.getIdFuncionario());
        try {
            Conexao.getInstance().executeSQLUpdateGSS(sql);
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Erro com o banco de dados", ex);

            throw new IllegalStateException("Ocorreu um erro inesperado, tente novamente mais tarde");
        }
    }

    @Override
    public List<Usuario> getUsuarios() throws IllegalStateException {
        // language=MySQL
        String sql = "SELECT * FROM usuario;";

        ResultSet resultado = null;
        List<Usuario> usuarios = new ArrayList<>();
        try {
            resultado = Conexao.getInstance().executeSQLQueryGSS(sql);

            if (resultado != null) {
                while (resultado.next()) {
                    int id = resultado.getInt("id");
                    String nome = resultado.getString("nome");
                    boolean isAdmin = resultado.getBoolean("isAdmin");
                    int idFuncionario = resultado.getInt("funcionario_id");

                    usuarios.add(new Usuario(id, nome, isAdmin, idFuncionario));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Não foi possível ler resultado", e);

            throw new IllegalStateException("Ocorreu um erro inesperado, tente novamente mais tarde");
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Erro com o banco de dados", ex);

            throw new IllegalStateException("Ocorreu um erro inesperado, tente novamente mais tarde");
        } finally {
            Conexao.getInstance().closeConnection(null, null, resultado);
        }

        return usuarios;
    }

    @Override
    public void setAdmin(int idUsuarioCadastro, boolean asAdmin) throws IllegalStateException {
        // language=MySQL
        String sql = String.format("UPDATE usuario SET isAdmin = %b WHERE id = %d;", asAdmin, idUsuarioCadastro);

        try {
            Conexao.getInstance().executeSQLUpdateGSS(sql);
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Erro com o banco de dados", ex);

            throw new IllegalStateException("Ocorreu um erro inesperado, tente novamente mais tarde");
        }
    }
}
