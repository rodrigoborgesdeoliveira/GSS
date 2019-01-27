package br.pucpr.gss.server.dao;

import br.pucpr.gss.shared.model.UsuarioLogin;

import java.sql.ResultSet;

public interface LoginDAO {
    ResultSet getUsuarioByCredenciais(UsuarioLogin login);
}
