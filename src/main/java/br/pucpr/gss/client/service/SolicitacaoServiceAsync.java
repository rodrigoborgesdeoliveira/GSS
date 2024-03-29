package br.pucpr.gss.client.service;

import br.pucpr.gss.shared.model.*;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.ArrayList;

public interface SolicitacaoServiceAsync {
    void getListaSetores(AsyncCallback<ArrayList<Setor>> async);

    void getListaOutrosSetores(int idFuncionario, AsyncCallback<ArrayList<Setor>> async);

    void getSetorById(int idSetor, AsyncCallback<Setor> async);

    void cadastrarSolicitacao(Solicitacao solicitacao, Usuario usuario, AsyncCallback<Void> async);

    void consultarSolicitacoes(int idFuncionario, AsyncCallback<ArrayList<Solicitacao>> async);

    void getAtendenteById(int idAtendente, AsyncCallback<Usuario> async);

    void getListaAtendentesByIdSetorExcetoGestor(int idSetor, int idGestor, AsyncCallback<ArrayList<Usuario>> async);

    void updateSolicitacao(Solicitacao solicitacao, Usuario usuario, AsyncCallback<Void> async);

    void requisitarInformacoesAdicionais(InformacaoAdicional informacaoAdicional, Solicitacao solicitacao,
                                         Usuario usuario, AsyncCallback<Void> async);

    void getInformacaoAdicionalByIdSolicitacao(int idSolicitacao, AsyncCallback<InformacaoAdicional> async);

    void registrarInformacoesAdicionais(InformacaoAdicional informacaoAdicional, Solicitacao solicitacao,
                                        Usuario usuario, AsyncCallback<Void> async);

    void removerInformacoesAdicionaisByIdSolicitacao(int idSolicitacao, AsyncCallback<Void> async);

    void getEventosSolicitacao(int idSolicitacao, AsyncCallback<ArrayList<Evento>> async);

    void registrarEvento(Solicitacao solicitacao, Usuario usuario, String nomeEvento, AsyncCallback<Void> async);
}
