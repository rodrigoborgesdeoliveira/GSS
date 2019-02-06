package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.event.DetalhesSolicitacaoEvent;
import br.pucpr.gss.client.event.VoltarEvent;
import br.pucpr.gss.client.service.SolicitacaoService;
import br.pucpr.gss.client.view.ConsultaSolicitacoesView;
import br.pucpr.gss.shared.model.Solicitacao;
import br.pucpr.gss.shared.model.Usuario;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import java.util.ArrayList;

public class ConsultaSolicitacoesPresenter implements Presenter, ConsultaSolicitacoesView.Presenter {

    private HandlerManager eventBus;
    private ConsultaSolicitacoesView view;
    private Usuario usuario;
    ArrayList<Solicitacao> listaSolicitacoes;

    public ConsultaSolicitacoesPresenter(HandlerManager eventBus, ConsultaSolicitacoesView view, Usuario usuario) {
        this.eventBus = eventBus;
        this.view = view;
        this.usuario = usuario;

        this.view.setPresenter(this);
        new MenuPresenter(eventBus, this.view.getMenuView(), usuario.isAdmin());

        fetchSolicitacoes();
    }

    @Override
    public void go(HasWidgets container) {
        container.clear();
        container.add(view.asWidget());
    }

    private void fetchSolicitacoes() {
        SolicitacaoService.RPC.getInstance().consultarSolicitacoes(usuario.getIdFuncionario(),
                new AsyncCallback<ArrayList<Solicitacao>>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        GWT.log("Erro ao carregar lista de solicitações", caught);

                        Window.alert("Não foi possível carregar lista de solicitações, tente novamente.");
                    }

                    @Override
                    public void onSuccess(ArrayList<Solicitacao> result) {
                        listaSolicitacoes = result;
                        ArrayList<String> solicitacoesNome = new ArrayList<>();

                        for (Solicitacao solicitacao : listaSolicitacoes) {
                            solicitacoesNome.add(solicitacao.getTitulo());
                        }

                        view.carregarListaSolicitacoes(solicitacoesNome);
                    }
                });
    }

    @Override
    public void onCancelarButtonClicked() {
        eventBus.fireEvent(new VoltarEvent());
    }

    @Override
    public void onConsultarButtonClicked(int indiceSolicitacao) {
        Solicitacao solicitacao = listaSolicitacoes.get(indiceSolicitacao);

        eventBus.fireEvent(new DetalhesSolicitacaoEvent(solicitacao));
    }
}
