package br.pucpr.gss.client.service;

import br.pucpr.gss.shared.model.Usuario;
import br.pucpr.gss.shared.model.UsuarioLogin;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("Login")
public interface LoginService extends RemoteService {
    Usuario login(UsuarioLogin usuarioLogin) throws IllegalArgumentException, IllegalStateException;

    /**
     * Classe para chamar os métodos de LoginService.
     * Use LoginService.RPC.getInstance() para acessar uma instância de LoginServiceAsync
     */
    class RPC {
        private static final LoginServiceAsync instance = GWT.create(LoginService.class);

        public static LoginServiceAsync getInstance() {
            return instance;
        }
    }
}
