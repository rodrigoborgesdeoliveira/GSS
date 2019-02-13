package br.pucpr.gss.server.dao;

import br.pucpr.gss.shared.model.InformacaoAdicional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GssDaoInformacaoAdicionalImpl implements GssDao.InformacaoAdicional {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public void insertInformacaoAdicional(InformacaoAdicional informacaoAdicional) throws IllegalStateException {
        Connection conexao = Conexao.getInstance().getConexaoGSS();

        // language=MySQL
        String sql = "INSERT INTO informacao_adicional (descricao_requisicao, resposta_requisicao, solicitacao_id) " +
                "VALUES (?,?,?);";

        PreparedStatement stmt = null;

        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, informacaoAdicional.getDescricao());
            stmt.setString(2, informacaoAdicional.getResposta());
            stmt.setInt(3, informacaoAdicional.getIdSolicitacao());

            Conexao.getInstance().executeSQLUpdate(stmt);
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Não foi possível executar statement", e);

            throw new IllegalStateException("Ocorreu um erro inesperado, tente novamente mais tarde");
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Erro com o banco de dados", ex);

            throw new IllegalStateException("Ocorreu um erro inesperado, tente novamente mais tarde");
        } finally {
            Conexao.getInstance().closeConnection(conexao, stmt, null);
        }
    }

    @Override
    public void updateInformacaoAdicional(InformacaoAdicional informacaoAdicional) throws IllegalStateException {
        Connection conexao = Conexao.getInstance().getConexaoGSS();

        // language=MySQL
        String sql = "UPDATE informacao_adicional SET descricao_requisicao = ?, resposta_requisicao = ? WHERE (id = ?);";

        PreparedStatement stmt = null;

        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, informacaoAdicional.getDescricao());
            stmt.setString(2, informacaoAdicional.getResposta());
            stmt.setInt(3, informacaoAdicional.getId());

            Conexao.getInstance().executeSQLUpdate(stmt);
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Não foi possível executar statement", e);

            throw new IllegalStateException("Ocorreu um erro inesperado, tente novamente mais tarde");
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Erro com o banco de dados", ex);

            throw new IllegalStateException("Ocorreu um erro inesperado, tente novamente mais tarde");
        } finally {
            Conexao.getInstance().closeConnection(conexao, stmt, null);
        }
    }

    @Override
    public InformacaoAdicional getInformacaoAdicionalByIdSolicitacao(int idSolicitacao) throws IllegalStateException {
        Connection conexao = Conexao.getInstance().getConexaoGSS();

        // language=MySQL
        String sql = "SELECT * FROM informacao_adicional WHERE solicitacao_id = ?;";

        PreparedStatement stmt = null;

        ResultSet resultSet = null;
        try {
            stmt = conexao.prepareStatement(sql);

            stmt.setInt(1, idSolicitacao);

            resultSet = Conexao.getInstance().executeSQLQuery(stmt);

            if (resultSet != null && resultSet.next()) {
                int id = resultSet.getInt("id");
                String descricao = resultSet.getString("descricao_requisicao");
                String resposta = resultSet.getString("resposta_requisicao");

                return new InformacaoAdicional(id, descricao, resposta, idSolicitacao);
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Não foi possível ler resultado", e);

            throw new IllegalStateException("Ocorreu um erro inesperado, tente novamente mais tarde");
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Erro com o banco de dados", ex);

            throw new IllegalStateException("Ocorreu um erro inesperado, tente novamente mais tarde");
        } finally {
            Conexao.getInstance().closeConnection(conexao, stmt, resultSet);
        }

        return null;
    }
}
