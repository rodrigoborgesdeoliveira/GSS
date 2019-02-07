package br.pucpr.gss.client.service;

import br.pucpr.gss.shared.model.Setor;
import br.pucpr.gss.shared.model.Solicitacao;
import br.pucpr.gss.shared.model.Usuario;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.ArrayList;

public interface SolicitacaoServiceAsync {
    void getListaSetores(AsyncCallback<ArrayList<Setor>> async);

    void getListaOutrosSetores(int idFuncionario, AsyncCallback<ArrayList<Setor>> async);

    void getSetorById(int idSetor, AsyncCallback<Setor> async);

    void cadastrarSolicitacao(Solicitacao solicitacao, AsyncCallback<Void> async);

    void consultarSolicitacoes(int idFuncionario, AsyncCallback<ArrayList<Solicitacao>> async);

    void getAtendenteById(int idAtendente, AsyncCallback<Usuario> async);

    void getListaAtendentesByIdSetorExcetoGestor(int idSetor, int idGestor, AsyncCallback<ArrayList<Usuario>> async);
}
