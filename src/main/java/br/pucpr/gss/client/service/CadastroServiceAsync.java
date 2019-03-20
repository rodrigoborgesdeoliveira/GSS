package br.pucpr.gss.client.service;

import br.pucpr.gss.shared.model.Usuario;
import br.pucpr.gss.shared.model.UsuarioLogin;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface CadastroServiceAsync {
    void cadastrar(UsuarioLogin usuarioLogin, AsyncCallback<Usuario> async);

    /**
     * Retorna uma lista de todos os usuários cadastrados no sistema, desde que o usuário requisitando seja um
     * administrador.
     *
     * @param idUsuario O id do usuário realizando a requisição.
     * @return Lista de usuários cadastrados no sistema.
     */
    void getCadastros(int idUsuario, AsyncCallback<List<Usuario>> async);

    void setAdmin(int idUsuarioAtual, int idUsuarioCadastro, boolean asAdmin, AsyncCallback<Void> async);
}
