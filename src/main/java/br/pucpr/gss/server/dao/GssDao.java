package br.pucpr.gss.server.dao;

public interface GssDao {

    interface Usuario {
        br.pucpr.gss.shared.model.Usuario getUsuarioByIdFuncionario(int idFuncionario);

        int getQuantidadeUsuarios();

        void insertUsuario(br.pucpr.gss.shared.model.Usuario usuario);
    }
}
