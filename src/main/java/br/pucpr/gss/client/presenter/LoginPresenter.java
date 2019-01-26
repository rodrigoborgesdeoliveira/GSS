package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.AppController;
import br.pucpr.gss.client.event.CadastrarEvent;
import br.pucpr.gss.client.view.LoginView;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

public class LoginPresenter implements Presenter, LoginView.Presenter {
    private final HandlerManager eventBus;
    private final LoginView view;

    public LoginPresenter(HandlerManager eventBus, LoginView view) {
        this.eventBus = eventBus;
        this.view = view;

        this.view.setPresenter(this);
    }

    @Override
    public void go(final HasWidgets container) {
        container.clear();
        container.add(view.asWidget());
    }

    @Override
    public void onLoginButtonClicked(String email, String senha) {
        // TODO: 25/01/2019 Validar campos
    }

    @Override
    public String getOnCadastrarClickedToken() {
        return AppController.CADASTRAR_TOKEN;
    }
}
