package br.pucpr.gss.client.view.uibinder;

import br.pucpr.gss.client.view.LoginView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTextBox;

public class LoginViewImpl extends Composite implements LoginView {

    interface LoginViewUiBinder extends UiBinder<Widget, LoginViewImpl> {
    }

    private static LoginViewUiBinder uiBinder = GWT.create(LoginViewUiBinder.class);

    @UiField
    MaterialTextBox textBoxEmail, textBoxSenha;
    @UiField
    MaterialLink linkCadastrar;

    private Presenter presenter;

    public LoginViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));

        // Fazer com que o label seja animado ao ganhar e perder foco
        textBoxEmail.addFocusHandler(event -> textBoxEmail.setFocus(true));
        textBoxEmail.addBlurHandler(event -> textBoxEmail.setFocus(false));
        textBoxSenha.addFocusHandler(event -> textBoxSenha.setFocus(true));
        textBoxSenha.addBlurHandler(event -> textBoxSenha.setFocus(false));

        KeyDownHandler enterKeyLogin = event -> {
            if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                onClickLogin(null);
            }
        };

        textBoxEmail.addKeyDownHandler(enterKeyLogin);
        textBoxSenha.addKeyDownHandler(enterKeyLogin);
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

    @Override
    public void setEmailInvalido() {
        textBoxEmail.setErrorText("Email é obrigatório");
        textBoxEmail.setFocus(true);
    }

    @Override
    public void setSenhaInvalida() {
        textBoxSenha.setErrorText("Senha é obrigatória");
        textBoxSenha.setFocus(true);
    }

    @Override
    public void ocultarLabelErro() {
        textBoxEmail.clearErrorText();
        textBoxEmail.removeErrorModifiers();
        textBoxSenha.clearErrorText();
        textBoxSenha.removeErrorModifiers();
    }
}