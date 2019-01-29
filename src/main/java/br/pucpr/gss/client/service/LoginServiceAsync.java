package br.pucpr.gss.client.service;

import br.pucpr.gss.shared.model.Usuario;
import br.pucpr.gss.shared.model.UsuarioLogin;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {
    void login(UsuarioLogin usuarioLogin, AsyncCallback<Usuario> async);
}
