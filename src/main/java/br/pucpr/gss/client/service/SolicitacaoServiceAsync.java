package br.pucpr.gss.client.service;

import br.pucpr.gss.shared.model.Setor;
import br.pucpr.gss.shared.model.Solicitacao;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.ArrayList;

public interface SolicitacaoServiceAsync {
    void getListaSetores(AsyncCallback<ArrayList<Setor>> async);

    void cadastrarSolicitacao(Solicitacao solicitacao, AsyncCallback<Void> async);

    void consultarSolicitacoes(int idUsuario, AsyncCallback<ArrayList<Solicitacao>> async);
}
