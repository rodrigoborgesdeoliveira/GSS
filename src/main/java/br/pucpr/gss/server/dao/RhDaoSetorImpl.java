package br.pucpr.gss.server.dao;

import br.pucpr.gss.shared.model.Setor;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RhDaoSetorImpl implements RhDao.Setor {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public ArrayList<Setor> getSetores() throws IllegalStateException {
        // language=MySQL
        String sql = "SELECT * FROM setor;";
        ResultSet resultado = null;
        ArrayList<Setor> setores = new ArrayList<>();

        try {
            resultado = Conexao.getInstance().executeSQLQueryRH(sql);
            if (resultado != null) {
                while (resultado.next()) {
                    int id = resultado.getInt("id");
                    String nome = resultado.getString("nome");
                    int idGestor = resultado.getInt("gestor_id");

                    setores.add(new Setor(id, nome, idGestor));
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

        return setores;
    }

    @Override
    @Nullable
    public Setor getSetorById(int idSetor) throws IllegalStateException {
        // language=MySQL
        String sql = String.format("SELECT * FROM setor WHERE id = %d;", idSetor);
        ResultSet resultado = null;

        try {
            resultado = Conexao.getInstance().executeSQLQueryRH(sql);
            if (resultado != null && resultado.next()) {
                int id = resultado.getInt("id");
                String nome = resultado.getString("nome");
                int idGestor = resultado.getInt("gestor_id");

                return new Setor(id, nome, idGestor);
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
}
