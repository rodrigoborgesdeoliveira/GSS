package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.event.DetalhesSolicitacaoEvent;
import br.pucpr.gss.client.event.VoltarEvent;
import br.pucpr.gss.client.service.SolicitacaoService;
import br.pucpr.gss.client.view.VisualizacaoSolucaoView;
import br.pucpr.gss.shared.model.Solicitacao;
import br.pucpr.gss.shared.model.Usuario;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class VisualizacaoSolucaoPresenter implements Presenter, VisualizacaoSolucaoView.Presenter {

    private HandlerManager eventBus;
    private VisualizacaoSolucaoView view;
    private Usuario usuario;
    private Solicitacao solicitacao;

    public VisualizacaoSolucaoPresenter(HandlerManager eventBus, VisualizacaoSolucaoView view, Usuario usuario,
                                        Solicitacao solicitacao) {
        this.eventBus = eventBus;
        this.view = view;
        this.usuario = usuario;
        this.solicitacao = solicitacao;

        this.view.setPresenter(this);

        new MenuPresenter(this.eventBus, this.view.getMenuView(), this.usuario.isAdmin());

        this.view.setDescricaoSolucao(solicitacao.getDescricaoSolucao());
    }

    @Override
    public void go(HasWidgets container) {
        container.clear();
        container.add(view.asWidget());
    }

    @Override
    public void onCancelarButtonClicked() {
        eventBus.fireEvent(new VoltarEvent());
    }

    @Override
    public void onRejeitarButtonClicked() {
        solicitacao.setEstado(solicitacao.getEstado().rejeitarSolucao());

        SolicitacaoService.RPC.getInstance().updateSolicitacao(solicitacao, usuario, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Falha ao rejeitar a solução");
            }

            @Override
            public void onSuccess(Void result) {
                Window.alert("Solução rejeitada com sucesso.");

                eventBus.fireEvent(new DetalhesSolicitacaoEvent(solicitacao));
            }
        });
    }

    @Override
    public void onAceitarButtonClicked() {
        solicitacao.setEstado(solicitacao.getEstado().aceitarSolucao());

        SolicitacaoService.RPC.getInstance().updateSolicitacao(solicitacao, usuario, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Falha ao aceitar a solução");
            }

            @Override
            public void onSuccess(Void result) {
                Window.alert("Solução aceita com sucesso. A solicitação foi encerrada");

                eventBus.fireEvent(new DetalhesSolicitacaoEvent(solicitacao));
            }
        });
    }
}
