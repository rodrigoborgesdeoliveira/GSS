package br.pucpr.gss.server.dao;

import java.util.ArrayList;

public interface RhDao {

    interface Funcionario {
        br.pucpr.gss.server.model.Funcionario getFuncionarioByEmail(String email);

        ArrayList<br.pucpr.gss.server.model.Funcionario> getFuncionarioByIdSetor(int idSetor);
    }

    interface Setor {
        ArrayList<br.pucpr.gss.shared.model.Setor> getSetores();

        br.pucpr.gss.shared.model.Setor getSetorById(int idSetor);
    }
}
