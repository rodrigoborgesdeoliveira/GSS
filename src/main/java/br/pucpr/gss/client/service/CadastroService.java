package br.pucpr.gss.client.service;

import br.pucpr.gss.shared.model.Usuario;
import br.pucpr.gss.shared.model.UsuarioLogin;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("Cadastro")
public interface CadastroService extends RemoteService {
    Usuario cadastrar(UsuarioLogin usuarioLogin) throws IllegalStateException, IllegalArgumentException;

    /**
     * Classe para chamar os métodos de CadastroService.
     * Use CadastroService.RPC.getInstance() para acessar uma instância de CadastroServiceAsync
     */
    class RPC {
        private static CadastroServiceAsync instance = GWT.create(CadastroService.class);

        public static synchronized CadastroServiceAsync getInstance() {
            return instance;
        }
    }
}
