package br.pucpr.gss.server.dao;

import br.pucpr.gss.shared.fabrica.Fabrica;
import br.pucpr.gss.shared.fabrica.FabricaEstado;
import br.pucpr.gss.shared.fabrica.FabricaPrioridade;
import br.pucpr.gss.shared.model.Solicitacao;
import br.pucpr.gss.shared.model.estado.Estado;
import br.pucpr.gss.shared.model.prioridade.Prioridade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
            stmt.setInt(3, FabricaPrioridade.NORMAL);
            stmt.setInt(4, FabricaEstado.AGUARDANDO_ATENDIMENTO);
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

    @Override
    public ArrayList<Solicitacao> getSolicitacoesByIdUsuario(int idUsuario) {
        // language=MySQL
        String sql = String.format("SELECT * FROM gss.solicitacao WHERE " +
                "solicitante_id = %d OR atendente_id = %d OR gestor_id = %d;", idUsuario, idUsuario, idUsuario);
        ResultSet resultado = null;
        ArrayList<Solicitacao> solicitacoes = new ArrayList<>();

        try {
            resultado = Conexao.getInstance().executeSQLQueryGSS(sql);
            if (resultado != null) {
                Fabrica fabricaEstado = new FabricaEstado();
                Fabrica fabricaPrioriadade = new FabricaPrioridade();
                while (resultado.next()) {
                    int id = resultado.getInt("id");
                    String titulo = resultado.getString("titulo");
                    String descricao = resultado.getString("descricao");
                    Prioridade prioridade = fabricaPrioriadade.criarPrioridade(resultado.getInt("prioridade"));
                    Estado estado = fabricaEstado.criarEstado(resultado.getInt("estado"));
                    Date dataCriacao = resultado.getDate("data_criacao");
                    Date prazo = resultado.getDate("prazo");
                    String descricaoSolucao = resultado.getString("descricao_solucao");
                    int idSetor = resultado.getInt("setor_id");
                    int idSolicitante = resultado.getInt("solicitante_id");
                    int idAtendente = resultado.getInt("atendente_id");
                    int idGestor = resultado.getInt("gestor_id");

                    solicitacoes.add(new Solicitacao(id, titulo, descricao, prioridade, estado, dataCriacao, prazo,
                            descricaoSolucao, idSetor, idSolicitante, idAtendente, idGestor));
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

        return solicitacoes;
    }
}
