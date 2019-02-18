package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.event.VoltarEvent;
import br.pucpr.gss.client.view.RegistroSolucaoView;
import br.pucpr.gss.shared.model.Solicitacao;
import br.pucpr.gss.shared.model.Usuario;
import com.google.gwt.event.shared.HandlerManager;
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

    }
}
