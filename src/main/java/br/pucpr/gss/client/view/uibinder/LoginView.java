package br.pucpr.gss.client.view.uibinder;

import br.pucpr.gss.shared.model.UsuarioLogin;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;

public class LoginView extends Composite {
    interface LoginUiBinder extends UiBinder<HTMLPanel, LoginView> {
    }

    private static LoginUiBinder ourUiBinder = GWT.create(LoginUiBinder.class);

    @UiField
    TextBox textBoxEmail;

    @UiField
    TextBox textBoxSenha;

    @UiField
    Button buttonLogin;

    public LoginView() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @UiHandler("buttonLogin")
    void onClickLogin(ClickEvent event) {
        UsuarioLogin login = new UsuarioLogin(textBoxEmail.getText(), textBoxSenha.getText());
        realizarLogin(login);
    }

    private void realizarLogin(UsuarioLogin login) {
        if (!login.isEmailValido()) {
            GWT.log("Campo Email inválido");
            return;
        }
        if (!login.isSenhaValida()) {
            GWT.log("Campo Senha inválida");

            return;
        }

        // TODO: 22/01/2019 Chamar login request
    }
}