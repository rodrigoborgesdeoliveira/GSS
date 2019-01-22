package br.pucpr.gss.server.dao;

import java.sql.*;

public class Conexao {
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/estoque?useLegacyDatetimeCode=false&serverTimezone=America/Sao_Paulo";
    private final String USUARIO = "root";
    private final String SENHA = "password";

    private Connection conexao;
    public Statement statement;
    public ResultSet resultSet;

    public boolean conectar() {
        boolean result = true;
        try {
            Class.forName(DRIVER);
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            statement = conexao.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }

    public void desconectar() {
        try {
            if (conexao != null) {
                conexao.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executarSQL(String sql) {
        try {
            statement = conexao.createStatement();

            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
