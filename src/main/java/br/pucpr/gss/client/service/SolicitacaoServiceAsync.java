package br.pucpr.gss.client.service;

import br.pucpr.gss.shared.model.Setor;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.ArrayList;

public interface SolicitacaoServiceAsync {
    void getListaSetores(AsyncCallback<ArrayList<Setor>> async);
}
