package br.pucpr.gss.server.dao;

import com.google.gwt.core.client.GWT;
import com.sun.istack.internal.Nullable;

import java.sql.*;

public class Conexao {
    private static final String DRIVER = "com.mysql.jdbc.Driver"; //Driver do SGBD MySQL.
    private static final String URL = "jdbc:mysql://localhost:3306/"; //Caminho para o MySQL.
    private static final String DB_GSS = "gss";
    private static final String DB_RH = "rh";
    private static final String DB_OPCOES = "?useLegacyDatetimeCode=false&serverTimezone=America/Sao_Paulo";
    private static final String USUARIO_GSS = "root";
    private static final String SENHA_GSS = "password";
    private static final String USUARIO_RH = "root";
    private static final String SENHA_RH = "password";

    /**
     * Cria o banco de dados de solicitações (GSS) caso não exista ainda.
     *
     * @return true, se a execução do SQL ocorreu bem, false, se algo deu errado.
     */
    public static boolean criarBancoDeDados() {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USUARIO_GSS, SENHA_GSS);
            // language=MySQL
            final String sql = "CREATE DATABASE IF NOT EXISTS " + DB_GSS + ";";
            stmt = con.prepareStatement(sql);
            int resultado = stmt.executeUpdate();

            GWT.log(sql + " retornou " + resultado);

            criarTabelas();

            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            if (ex.getMessage().contains("Access denied")) {
                GWT.log("Acesso para criar o banco de dados negado", ex);
            } else {
                GWT.log("Não foi possível criar o banco de dados", ex);
            }

            return false;
        } finally {
            closeConnection(con, stmt);
        }
    }

    /**
     * Cria as tabelas do banco de dados de solicitações (GSS) caso não existam ainda.
     */
    private static void criarTabelas() {
        // language=MySQL
        String sqlUsuario = "CREATE TABLE IF NOT EXISTS usuario (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "PRIMARY KEY (id), " +
                "nome VARCHAR(50) NOT NULL, " +
                "senha VARCHAR(71) NOT NULL, " +
                "isAdmin BOOLEAN DEFAULT FALSE, " + // BCrypt tem um limite de 71 bytes + 1 byte reservado
                "funcionario_id INT NOT NULL, " +
                "FOREIGN KEY (funcionario_id) REFERENCES rh.funcionario(id));";
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
                "nome VARCHAR(50) NOT NULL, " +
                "data_ocorrencia DATE NOT NULL, " +
                "solicitacao_id INT NOT NULL, " +
                "usuario_id INT NOT NULL, " +
                "FOREIGN KEY (solicitacao_id) REFERENCES solicitacao(id), " +
                "FOREIGN KEY (usuario_id) REFERENCES usuario(id));";
        // language=MySQL
        String sqlInformacaoAdicional = "CREATE TABLE IF NOT EXISTS informacao_adicional (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
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
    private static Connection getConexaoGSS() throws RuntimeException {
        return getConexao(DB_GSS, USUARIO_GSS, SENHA_GSS);
    }

    /**
     * Gera uma conexão com o banco de dados do RH.
     *
     * @return a conexão com o banco de dados do RH.
     * @throws RuntimeException se algum erro ocorrer ao obter a conexão.
     */
    private static Connection getConexaoRH() throws RuntimeException {
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
    private static Connection getConexao(String db, String usuario, String senha) {
        try {
            Class.forName(DRIVER);

            return DriverManager.getConnection(URL + db + DB_OPCOES, usuario, senha);
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException("Erro na conexão: ", ex);
        }
    }

    private static void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            GWT.log("Não foi possível fechar a conexão com o banco de dados", ex);
        }
    }

    private static void closeConnection(Connection con, PreparedStatement stmt) {
        closeConnection(con);

        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            GWT.log("Não foi possível fechar a conexão com o banco de dados", ex);
        }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) {
        closeConnection(con, stmt);

        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            GWT.log("Não foi possível fechar a conexão com o banco de dados", ex);
        }
    }

    /**
     * Executa um comando SQL INSERT, UPDATE ou DELETE no banco de dados do GSS.
     *
     * @param sql comando SQL a ser executado.
     */
    public static void executeSQLUpdateGSS(String sql) {
        Connection conexao = null;
        PreparedStatement statement = null;

        try {
            conexao = getConexaoGSS();
            statement = conexao.prepareStatement(sql);

            int resultado = statement.executeUpdate();

            GWT.log(sql + " retornou " + resultado);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conexao, statement);
        }
    }

    /**
     * Executa um comando SQL que retorna um resultado, como o comando SELECT, no banco de dados GSS.
     *
     * @param sql comando SQL a ser executado.
     * @return um {@link ResultSet} com o resultado da consulta ou null se ocorrer algum erro.
     */
    @Nullable
    public static ResultSet executeSQLQueryGSS(String sql) {
        return executeSQLQuery(getConexaoGSS(), sql);
    }

    /**
     * Executa um comando SQL que retorna um resultado, como o comando SELECT, no banco de dados RH.
     *
     * @param sql comando SQL a ser executado.
     * @return um {@link ResultSet} com o resultado da consulta ou null se ocorrer algum erro.
     */
    @Nullable
    public static ResultSet executeSQLQueryRH(String sql) {
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
    private static ResultSet executeSQLQuery(Connection conexao, String sql) {
        PreparedStatement statement = null;

        try {
            statement = conexao.prepareStatement(sql);

            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();

            return null;
        } finally {
            closeConnection(conexao, statement);
        }
    }
}
