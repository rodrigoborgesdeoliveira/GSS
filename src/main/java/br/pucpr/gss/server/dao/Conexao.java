package br.pucpr.gss.server.dao;

import com.sun.istack.internal.Nullable;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexao {
    private static Conexao instance = new Conexao();

    private final String DRIVER = "com.mysql.cj.jdbc.Driver"; //Driver do SGBD MySQL.
    private final String URL = "jdbc:mysql://localhost:3306/"; //Caminho para o MySQL.
    private final String DB_GSS = "gss";
    private final String DB_RH = "rh";
    private final String DB_OPCOES = "?useLegacyDatetimeCode=false&serverTimezone=America/Sao_Paulo";
    private final String USUARIO_GSS = "root";
    private final String SENHA_GSS = "password";
    private final String USUARIO_RH = "root";
    private final String SENHA_RH = "password";

    private static Logger logger = Logger.getLogger("Conexao");

    public static synchronized Conexao getInstance() {
        return instance;
    }

    private Conexao() {
    }

    /**
     * Cria o banco de dados de solicitações (GSS) caso não exista ainda.
     *
     * @return true, se a execução do SQL ocorreu bem, false, se algo deu errado.
     */
    public void criarBancoDeDados() {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL + DB_OPCOES, USUARIO_GSS, SENHA_GSS);
            // language=MySQL
            final String sql = "CREATE DATABASE IF NOT EXISTS " + DB_GSS + ";";
            stmt = con.prepareStatement(sql);
            int resultado = stmt.executeUpdate();

            logger.log(Level.INFO, sql + " retornou " + resultado);

            criarTabelas();
        } catch (ClassNotFoundException | SQLException ex) {
            if (ex.getMessage().contains("Access denied")) {
                logger.log(Level.INFO, "Acesso ao banco de dados negado", ex);
            } else {
                logger.log(Level.INFO, "Não foi possível criar o banco de dados", ex);
            }

        } finally {
            closeConnection(con, stmt);
        }
    }

    /**
     * Cria as tabelas do banco de dados de solicitações (GSS) caso não existam ainda.
     */
    private void criarTabelas() {
        // language=MySQL
        String sqlUsuario = "CREATE TABLE IF NOT EXISTS usuario (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "PRIMARY KEY (id), " +
                "nome VARCHAR(50) NOT NULL, " +
                "senha VARCHAR(71) NOT NULL, " +
                "isAdmin BOOLEAN DEFAULT FALSE, " + // BCrypt tem um limite de 71 bytes + 1 byte reservado
                "funcionario_id INT NOT NULL, " +
                "FOREIGN KEY (funcionario_id) REFERENCES rh.funcionario(id), " +
                "UNIQUE INDEX funcionario_id_UNIQUE (funcionario_id ASC) INVISIBLE);";
        // language=MySQL
        String sqlSolicitacao = "CREATE TABLE IF NOT EXISTS solicitacao (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "PRIMARY KEY (id), " +
                "titulo VARCHAR(45) NOT NULL, " +
                "descricao VARCHAR(250), " +
                "prioridade INT NOT NULL, " +
                "estado INT NOT NULL, " +
                "data_criacao DATE NOT NULL, " +
                "prazo DATE, " +
                "descricao_solucao VARCHAR(250), " +
                "setor_id INT NOT NULL, " +
                "solicitante_id INT NOT NULL, " +
                "atendente_id INT, " +
                "gestor_id INT NOT NULL, " +
                "FOREIGN KEY (setor_id) REFERENCES rh.setor(id), " +
                "FOREIGN KEY (solicitante_id) REFERENCES rh.funcionario(id), " +
                "FOREIGN KEY (atendente_id) REFERENCES rh.funcionario(id), " +
                "FOREIGN KEY (gestor_id) REFERENCES rh.funcionario(id));";
        // language=MySQL
        String sqlEvento = "CREATE TABLE IF NOT EXISTS evento (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "PRIMARY KEY (id), " +
                "nome VARCHAR(50) NOT NULL, " +
                "data_ocorrencia DATE NOT NULL, " +
                "solicitacao_id INT NOT NULL, " +
                "usuario_id INT NOT NULL, " +
                "FOREIGN KEY (solicitacao_id) REFERENCES solicitacao(id), " +
                "FOREIGN KEY (usuario_id) REFERENCES usuario(id));";
        // language=MySQL
        String sqlInformacaoAdicional = "CREATE TABLE IF NOT EXISTS informacao_adicional (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "PRIMARY KEY (id), " +
                "descricao_requisicao VARCHAR(250) NOT NULL, " +
                "resposta_requisicao VARCHAR(250), " +
                "solicitacao_id INT NOT NULL, " +
                "FOREIGN KEY (solicitacao_id) REFERENCES solicitacao(id));";

        executeSQLUpdateGSS(sqlUsuario);
        executeSQLUpdateGSS(sqlSolicitacao);
        executeSQLUpdateGSS(sqlEvento);
        executeSQLUpdateGSS(sqlInformacaoAdicional);
    }

    /**
     * Gera uma conexão com o banco de dados do GSS.
     *
     * @return a conexão com o banco de dados do GSS.
     * @throws RuntimeException se algum erro ocorrer ao obter a conexão.
     */
    private Connection getConexaoGSS() throws RuntimeException {
        return getConexao(DB_GSS, USUARIO_GSS, SENHA_GSS);
    }

    /**
     * Gera uma conexão com o banco de dados do RH.
     *
     * @return a conexão com o banco de dados do RH.
     * @throws RuntimeException se algum erro ocorrer ao obter a conexão.
     */
    private Connection getConexaoRH() throws RuntimeException {
        return getConexao(DB_RH, USUARIO_RH, SENHA_RH);
    }

    /**
     * Gera uma conexão com o banco de dados.
     *
     * @param db      nome do banco de dados.
     * @param usuario usuário root do banco de dados.
     * @param senha   senha do usuário do banco de dados.
     * @return a conexão com o banco de dados solicitado.
     */
    private Connection getConexao(String db, String usuario, String senha) {
        try {
            Class.forName(DRIVER);

            return DriverManager.getConnection(URL + db + DB_OPCOES, usuario, senha);
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException("Erro na conexão: ", ex);
        }
    }

    private void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            logger.log(Level.INFO, "Não foi possível fechar a conexão com o banco de dados", ex);
        }
    }

    private void closeConnection(Connection con, PreparedStatement stmt) {
        closeConnection(con);

        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            logger.log(Level.INFO, "Não foi possível fechar a conexão com o banco de dados", ex);
        }
    }

    public void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) {
        closeConnection(con, stmt);

        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            logger.log(Level.INFO, "Não foi possível fechar a conexão com o banco de dados", ex);
        }
    }

    /**
     * Executa um comando SQL INSERT, UPDATE ou DELETE no banco de dados do GSS.
     *
     * @param sql comando SQL a ser executado.
     */
    public void executeSQLUpdateGSS(String sql) {
        Connection conexao = null;
        PreparedStatement statement = null;

        try {
            conexao = getConexaoGSS();
            statement = conexao.prepareStatement(sql);

            int resultado = statement.executeUpdate();

            logger.log(Level.INFO, sql + " retornou " + resultado);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            closeConnection(conexao, statement);
        }
    }

    /**
     * Executa um comando SQL que retorna um resultado, como o comando SELECT, no banco de dados GSS.
     *
     * @param sql comando SQL a ser executado.
     * @return um {@link ResultSet} com o resultado da consulta ou null se ocorrer algum erro.
     */
    @Nullable
    public ResultSet executeSQLQueryGSS(String sql) {
        return executeSQLQuery(getConexaoGSS(), sql);
    }

    /**
     * Executa um comando SQL que retorna um resultado, como o comando SELECT, no banco de dados RH.
     *
     * @param sql comando SQL a ser executado.
     * @return um {@link ResultSet} com o resultado da consulta ou null se ocorrer algum erro.
     */
    @Nullable
    public ResultSet executeSQLQueryRH(String sql) {
        return executeSQLQuery(getConexaoRH(), sql);
    }

    /**
     * Executa um comando SQL que retorna um resultado, como o comando SELECT.
     *
     * @param conexao conexão com o banco de dados que será executado o comando SQL.
     * @param sql     comando SQL a ser executado.
     * @return um {@link ResultSet} com o resultado da consulta ou null se ocorrer algum erro.
     */
    @Nullable
    private ResultSet executeSQLQuery(Connection conexao, String sql) {
        PreparedStatement statement = null;

        try {
            statement = conexao.prepareStatement(sql);

            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();

            return null;
        } finally {
//            closeConnection(conexao, statement);
        }
    }
}
