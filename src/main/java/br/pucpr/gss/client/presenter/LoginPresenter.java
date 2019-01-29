package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.AppController;
import br.pucpr.gss.client.event.LoginSucesso;
import br.pucpr.gss.client.service.LoginService;
import br.pucpr.gss.client.view.LoginView;
import br.pucpr.gss.shared.model.Usuario;
import br.pucpr.gss.shared.model.UsuarioLogin;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
        view.ocultarLabelErro();

        // Validar apenas se os campos não estão vazios
        if (email.isEmpty()) {
            GWT.log("Email não preenchido");
            view.setEmailInvalido();

            return;
        }

        if (senha.isEmpty()) {
            GWT.log("Senha não preenchida");
            view.setSenhaInvalida();

            return;
        }

        UsuarioLogin usuarioLogin = new UsuarioLogin(email, senha);

        LoginService.RPC.getInstance().login(usuarioLogin, new AsyncCallback<Usuario>() {
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Erro ao fazer login", caught);


            }

            @Override
            public void onSuccess(Usuario result) {
                GWT.log("Login realizado com sucesso");

                eventBus.fireEvent(new LoginSucesso(result));
            }
        });
    }

    @Override
    public String getOnCadastrarClickedToken() {
        return AppController.CADASTRAR_TOKEN;
    }
}
