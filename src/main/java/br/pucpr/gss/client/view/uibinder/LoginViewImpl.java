package br.pucpr.gss.client.view.uibinder;

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
    @UiField
    Label labelErro;

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

    @Override
    public void setEmailInvalido() {
        setErro("Email é obrigatório");
    }

    @Override
    public void setSenhaInvalida() {
        setErro("Senha é obrigatória");
    }

    @Override
    public void ocultarLabelErro() {
        labelErro.setVisible(false);
        labelErro.setText("");
    }

    @Override
    public void setErro(String mensagemErro) {
        labelErro.setVisible(true);
        labelErro.setText(mensagemErro);
    }
}