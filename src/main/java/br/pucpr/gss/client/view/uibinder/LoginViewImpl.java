package br.pucpr.gss.client.view.uibinder;

import br.pucpr.gss.client.AppController;
import br.pucpr.gss.client.view.LoginView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

public class LoginViewImpl extends Composite implements LoginView {

    interface LoginViewUiBinder extends UiBinder<Widget, LoginViewImpl> {
    }

    private static LoginViewUiBinder uiBinder = GWT.create(LoginViewUiBinder.class);

    @UiField
    TextBox textBoxEmail;
    @UiField
    TextBox textBoxSenha;
    @UiField
    Button buttonLogin;
    @UiField
    Hyperlink linkCadastrar;

    private Presenter presenter;

    public LoginViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;

        if (presenter != null) {
            // Dizer ao link cadastrar para onde ele deve apontar
            linkCadastrar.setTargetHistoryToken(presenter.getOnCadastrarClickedToken());
        }
    }

    @UiHandler("buttonLogin")
    void onClickLogin(ClickEvent event) {
        if (presenter != null) {
            presenter.onLoginButtonClicked(textBoxEmail.getText(), textBoxSenha.getText());
        }
    }
}