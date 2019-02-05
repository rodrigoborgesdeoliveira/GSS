package br.pucpr.gss.server.dao;

import java.util.ArrayList;

public interface GssDao {

    interface Usuario {
        br.pucpr.gss.shared.model.Usuario getUsuarioById(int id);

        br.pucpr.gss.shared.model.Usuario getUsuarioByIdFuncionario(int idFuncionario);

        br.pucpr.gss.shared.model.Usuario getUsuarioByIdFuncionarioESenha(int idFuncionario, String senha);

        int getQuantidadeUsuarios();

        void insertUsuario(br.pucpr.gss.shared.model.Usuario usuario);
    }

    interface Solicitacao {
        void insertSolicitacao(br.pucpr.gss.shared.model.Solicitacao solicitacao);

        ArrayList<br.pucpr.gss.shared.model.Solicitacao> getSolicitacoesByIdUsuario(int idUsuario);
    }
}
