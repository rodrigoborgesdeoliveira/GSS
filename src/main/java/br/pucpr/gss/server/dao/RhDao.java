package br.pucpr.gss.server.dao;

import java.util.ArrayList;

public interface RhDao {

    interface Funcionario {
        br.pucpr.gss.server.model.Funcionario getFuncionarioByEmail(String email) throws IllegalStateException;

        ArrayList<br.pucpr.gss.server.model.Funcionario> getFuncionariosByIdSetor(int idSetor) throws IllegalStateException;
    }

    interface Setor {
        ArrayList<br.pucpr.gss.shared.model.Setor> getSetores() throws IllegalStateException;

        /**
         * Retorna todos os setores, exceto aquele ao qual o funcionário pertence.
         *
         * @return Lista de setores, exceto o setor do funcionário.
         */
        ArrayList<br.pucpr.gss.shared.model.Setor> getSetoresExcluindoFuncionario(int idFuncionario) throws IllegalStateException;

        br.pucpr.gss.shared.model.Setor getSetorById(int idSetor) throws IllegalStateException;
    }
}
