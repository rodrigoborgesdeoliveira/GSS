package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.view.CadastroSolicitacaoView;
import br.pucpr.gss.shared.model.Usuario;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

public class CadastroSolicitacaoPresenter implements Presenter, CadastroSolicitacaoView.Presenter {

    private HandlerManager eventBus;
    private CadastroSolicitacaoView view;
    private Usuario usuario;

    public CadastroSolicitacaoPresenter(HandlerManager eventBus, CadastroSolicitacaoView view, Usuario usuario) {
        this.eventBus = eventBus;
        this.view = view;
        this.usuario = usuario;

        this.view.setPresenter(this);

        new MenuPresenter(eventBus, this.view.getMenuView(), usuario.isAdmin());
    }

    @Override
    public void go(HasWidgets container) {
        container.clear();
        container.add(view.asWidget());
    }
}
