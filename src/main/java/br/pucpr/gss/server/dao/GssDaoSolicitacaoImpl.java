package br.pucpr.gss.server.dao;

import br.pucpr.gss.shared.model.Solicitacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GssDaoSolicitacaoImpl implements GssDao.Solicitacao {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public void insertSolicitacao(Solicitacao solicitacao) {
        Connection conexao = Conexao.getInstance().getConexaoGSS();

        // language=MySQL
        String sql = "INSERT INTO solicitacao (titulo, descricao, prioridade,estado,data_criacao,setor_id," +
                "solicitante_id,gestor_id) VALUES (?,?,?,?,?,?,?,?);";

        PreparedStatement stmt = null;

        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, solicitacao.getTitulo());
            stmt.setString(2, solicitacao.getDescricao());
            stmt.setInt(3, 1); // 1 = Prioridade normal
            stmt.setInt(4, 0); // 0 = Estado aguardando atendimento
            stmt.setDate(5, new java.sql.Date(solicitacao.getDataCriacao().getTime()));
            stmt.setInt(6, solicitacao.getIdSetor());
            stmt.setInt(7, solicitacao.getIdSolicitante());
            stmt.setInt(8, solicitacao.getIdGestor());

            Conexao.getInstance().executeSQLUpdate(stmt);
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Não foi possível ler resultado", e);

            throw new IllegalStateException("Ocorreu um erro inesperado, tente novamente mais tarde");
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Erro com o banco de dados", ex);

            throw new IllegalStateException("Ocorreu um erro inesperado, tente novamente mais tarde");
        } finally {
            Conexao.getInstance().closeConnection(conexao, stmt, null);
        }
    }
}
