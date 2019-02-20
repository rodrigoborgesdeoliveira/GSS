package br.pucpr.gss.server.dao;

import br.pucpr.gss.shared.model.Evento;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GssDaoEventoImpl implements GssDao.Evento {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public void insertEvento(Evento evento) throws IllegalStateException {
        // language=MySQL
        String sql = "INSERT INTO evento (nome, data_ocorrencia, solicitacao_id, usuario_id) VALUES (?,?,?,?);";

        Connection conexao = Conexao.getInstance().getConexaoGSS();

        PreparedStatement stmt = null;

        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, evento.getNome());
            stmt.setDate(2, new Date(evento.getDataOcorrencia().getTime()));
            stmt.setInt(3, evento.getIdSolicitacao());
            stmt.setInt(4, evento.getIdUsuario());

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
}
