package br.pucpr.gss.server.dao;

import br.pucpr.gss.server.model.Funcionario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RhDaoFuncionarioImpl implements RhDao.Funcionario {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public Funcionario getFuncionarioByEmail(String email) throws IllegalStateException {
        // language=MySQL
        String sql = String.format("SELECT id, nome, setor_id FROM funcionario WHERE (email) = ('%s');", email);

        ResultSet resultado = null;

        try {
            resultado = Conexao.getInstance().executeSQLQueryRH(sql);
            if (resultado != null && resultado.next()) {
                int id = resultado.getInt("id");
                String nome = resultado.getString("nome");
                int setorId = resultado.getInt("setor_id");

                return new Funcionario(id, email, nome, setorId);
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
    public ArrayList<Funcionario> getFuncionarioByIdSetor(int idSetor) {
        // language=MySQL
        String sql = String.format("SELECT * FROM funcionario WHERE setor_id = %d;", idSetor);

        ResultSet resultado = null;
        ArrayList<Funcionario> funcionarios = new ArrayList<>();

        try {
            resultado = Conexao.getInstance().executeSQLQueryRH(sql);
            if (resultado != null) {
                while (resultado.next()) {
                    int id = resultado.getInt("id");
                    String nome = resultado.getString("nome");
                    int setorId = resultado.getInt("setor_id");

                    funcionarios.add(new Funcionario(id, nome, setorId));
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

        return funcionarios;
    }
}
