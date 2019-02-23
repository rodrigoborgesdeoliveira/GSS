package br.pucpr.gss.server.dao;

import br.pucpr.gss.server.util.Util;
import br.pucpr.gss.shared.model.Evento;

import java.sql.*;
import java.util.ArrayList;
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
            stmt.setTimestamp(2, new Timestamp(evento.getDataOcorrencia().getTime()));
            stmt.setInt(3, evento.getIdSolicitacao());
            stmt.setInt(4, evento.getIdUsuario());

            Conexao.getInstance().executeSQLUpdate(stmt);

            logger.log(Level.INFO, "Evento: " + evento.getNome());
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
    public ArrayList<Evento> getEventosByIdSolicitacao(int idSolicitacao) throws IllegalStateException {
        // language=MySQL
        String sql = "SELECT * FROM evento WHERE solicitacao_id = ? ORDER BY data_ocorrencia DESC;";

        Connection conexao = Conexao.getInstance().getConexaoGSS();

        PreparedStatement stmt = null;
        ResultSet resultado = null;
        ArrayList<Evento> eventos = new ArrayList<>();

        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idSolicitacao);

            resultado = Conexao.getInstance().executeSQLQuery(stmt);
            if (resultado != null) {
                while (resultado.next()) {
                    int id = resultado.getInt("id");
                    String nome = resultado.getString("nome");
                    java.util.Date dataOcorrencia = resultado.getTimestamp("data_ocorrencia");
                    int idUsuario = resultado.getInt("usuario_id");

                    eventos.add(new Evento(id, nome, dataOcorrencia, Util.stringDataHoraFromDate(dataOcorrencia), idSolicitacao,
                            idUsuario));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Não foi possível executar statement", e);

            throw new IllegalStateException("Ocorreu um erro inesperado, tente novamente mais tarde");
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Erro com o banco de dados", ex);

            throw new IllegalStateException("Ocorreu um erro inesperado, tente novamente mais tarde");
        } finally {
            Conexao.getInstance().closeConnection(conexao, stmt, resultado);
        }

        return eventos;
    }
}
