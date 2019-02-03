package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.view.DetalhesSolicitacaoView;
import br.pucpr.gss.shared.model.Solicitacao;
import br.pucpr.gss.shared.model.Usuario;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

public class DetalhesSolicitacaoPresenter implements Presenter, DetalhesSolicitacaoView.Presenter {

    private HandlerManager eventBus;
    private DetalhesSolicitacaoView view;
    private Usuario usuario;
    private Solicitacao solicitacao;

    public DetalhesSolicitacaoPresenter(HandlerManager eventBus, DetalhesSolicitacaoView view, Usuario usuario,
                                        Solicitacao solicitacao) {
        this.eventBus = eventBus;
        this.view = view;
        this.usuario = usuario;
        this.solicitacao = solicitacao;

        this.view.setPresenter(this);

        new MenuPresenter(eventBus, this.view.getMenuView(), usuario.isAdmin());
    }

    @Override
    public void go(HasWidgets container) {
        container.clear();
        container.add(view.asWidget());
    }
}
