package br.pucpr.gss.server.dao;

import br.pucpr.gss.shared.model.Setor;

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
                    int id = resultado.getInt(1);
                    String nome = resultado.getString(2);
                    int idGestor = resultado.getInt(3);

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
}
