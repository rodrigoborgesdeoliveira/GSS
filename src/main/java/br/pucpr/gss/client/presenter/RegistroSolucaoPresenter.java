package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.event.DetalhesSolicitacaoEvent;
import br.pucpr.gss.client.event.VoltarEvent;
import br.pucpr.gss.client.service.SolicitacaoService;
import br.pucpr.gss.client.view.RegistroSolucaoView;
import br.pucpr.gss.shared.model.Solicitacao;
import br.pucpr.gss.shared.model.Usuario;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class RegistroSolucaoPresenter implements Presenter, RegistroSolucaoView.Presenter {

    private HandlerManager eventBus;
    private RegistroSolucaoView view;
    private Solicitacao solicitacao;
    private Usuario usuario;

    public RegistroSolucaoPresenter(HandlerManager eventBus, RegistroSolucaoView view, Usuario usuario,
                                    Solicitacao solicitacao) {
        this.eventBus = eventBus;
        this.view = view;
        this.usuario = usuario;
        this.solicitacao = solicitacao;

        this.view.setPresenter(this);

        new MenuPresenter(eventBus, this.view.getMenuView(), this.usuario.isAdmin());
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
    public void onSalvarButtonClicked(String solucao) {
        solicitacao.setDescricaoSolucao(solucao);
        solicitacao.setEstado(solicitacao.getEstado().oferecerSolucao());
        SolicitacaoService.RPC.getInstance().updateSolicitacao(solicitacao, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Não foi possível registrar a solução");
            }

            @Override
            public void onSuccess(Void result) {
                Window.alert("Solução registrada com sucesso");

                eventBus.fireEvent(new DetalhesSolicitacaoEvent(solicitacao));
            }
        });
    }
}
