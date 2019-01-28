package br.pucpr.gss.server.dao;

import br.pucpr.gss.server.model.Funcionario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RhDaoFuncionarioImpl implements RhDao.Funcionario {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public Funcionario getFuncionarioByEmail(String email) {
        // language=MySQL
        String sql = String.format("SELECT id, nome, setor_id FROM funcionario WHERE (email) = ('%s');", email);

        ResultSet resultado = Conexao.getInstance().executeSQLQueryRH(sql);

        try {
            if (resultado.next()) {
                int id = resultado.getInt(1);
                String nome = resultado.getString(2);
                int setorId = resultado.getInt(3);

                return new Funcionario(id, email, nome, setorId);
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Não foi possível ler resultado", e);

            throw new IllegalStateException("Ocorreu um erro inesperado, tente novamente mais tarde");
        } finally {
            Conexao.getInstance().closeConnection(null, null, resultado);
        }

        return null;
    }
}
