package br.pucpr.gss.server.dao;

public interface RhDao {

    interface Funcionario {
        br.pucpr.gss.server.model.Funcionario getFuncionarioByEmail(String email);
    }

    interface Setor {

    }
}
