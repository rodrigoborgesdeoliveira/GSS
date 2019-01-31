package br.pucpr.gss.server.dao;

public interface GssDao {

    interface Usuario {
        br.pucpr.gss.shared.model.Usuario getUsuarioByIdFuncionario(int idFuncionario);

        br.pucpr.gss.shared.model.Usuario getUsuarioByIdFuncionarioESenha(int idFuncionario, String senha);

        int getQuantidadeUsuarios();

        void insertUsuario(br.pucpr.gss.shared.model.Usuario usuario);
    }

    interface Solicitacao {
        void insertSolicitacao(br.pucpr.gss.shared.model.Solicitacao solicitacao);
    }
}
