package br.pucpr.gss.server.dao;

import java.sql.ResultSet;

public interface CadastroDAO {

    ResultSet getUsuarioByEmail(String email);
}
