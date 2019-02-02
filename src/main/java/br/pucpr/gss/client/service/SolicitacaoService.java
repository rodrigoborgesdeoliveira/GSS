package br.pucpr.gss.client.service;

import br.pucpr.gss.shared.model.Setor;
import br.pucpr.gss.shared.model.Solicitacao;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.ArrayList;

@RemoteServiceRelativePath("Solicitacao")
public interface SolicitacaoService extends RemoteService {
    ArrayList<Setor> getListaSetores() throws IllegalStateException;

    void cadastrarSolicitacao(Solicitacao solicitacao) throws IllegalStateException;

    ArrayList<Solicitacao> consultarSolicitacoes(int idUsuario);

    /**
     * Classe para chamar os métodos de SolicitacaoService.
     * Use SolicitacaoService.RPC.getInstance() para acessar uma instância de SolicitacaoServiceAsync
     */
    class RPC {
        private static final SolicitacaoServiceAsync instance = GWT.create(SolicitacaoService.class);

        public static SolicitacaoServiceAsync getInstance() {
            return instance;
        }
    }
}
