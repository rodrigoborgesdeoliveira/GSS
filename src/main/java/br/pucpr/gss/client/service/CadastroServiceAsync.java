package br.pucpr.gss.client.service;

import br.pucpr.gss.shared.model.Resposta;
import br.pucpr.gss.shared.model.UsuarioLogin;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CadastroServiceAsync {
    void cadastrar(UsuarioLogin usuarioLogin, AsyncCallback<Resposta> async);
}
