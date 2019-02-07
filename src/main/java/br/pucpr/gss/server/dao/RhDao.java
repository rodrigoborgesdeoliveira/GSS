package br.pucpr.gss.server.dao;

import java.util.ArrayList;

public interface RhDao {

    interface Funcionario {
        br.pucpr.gss.server.model.Funcionario getFuncionarioByEmail(String email);

        ArrayList<br.pucpr.gss.server.model.Funcionario> getFuncionarioByIdSetor(int idSetor);
    }

    interface Setor {
        ArrayList<br.pucpr.gss.shared.model.Setor> getSetores();

        /**
         * Retorna todos os setores, exceto aquele ao qual o funcionário pertence.
         *
         * @return Lista de setores, exceto o setor do funcionário.
         */
        ArrayList<br.pucpr.gss.shared.model.Setor> getSetoresExcluindoFuncionario(int idFuncionario);

        br.pucpr.gss.shared.model.Setor getSetorById(int idSetor);
    }
}
