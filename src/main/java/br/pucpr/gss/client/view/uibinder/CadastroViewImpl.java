package br.pucpr.gss.client.view.uibinder;

import br.pucpr.gss.client.view.CadastroView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

public class CadastroViewImpl extends Composite implements CadastroView {

    interface CadastroViewUiBinder extends UiBinder<HTMLPanel, CadastroViewImpl> {
    }

    private static CadastroViewUiBinder uiBinder = GWT.create(CadastroViewUiBinder.class);

    @UiField
    TextBox email;
    @UiField
    PasswordTextBox senha;
    @UiField
    PasswordTextBox confirmarSenha;
    @UiField
    Label labelErro;
    @UiField
    Button buttonCadastrar;
    @UiField
    Hyperlink linkVoltar;

    private Presenter presenter;

    public CadastroViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));

        linkVoltar.setTargetHistoryToken("");
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @UiHandler("buttonCadastrar")
    void onClickCadastrar(ClickEvent event) {
        if (presenter != null) {
            presenter.onCadastrarButtonClicked(email.getText(), senha.getText(), confirmarSenha.getText());
        }
    }

    @UiHandler("linkVoltar")
    void onClickVoltar(ClickEvent event) {
        if (presenter != null) {
            presenter.onVoltarClicked();
        }
    }

    /**
     * Exibe uma mensagem de erro indicando que o email é inválido.
     */
    @Override
    public void setEmailInvalido() {
        setErro("Email inválido");
    }

    /**
     * Exibe uma mensagem de erro indicando que a senha é inválida.
     */
    @Override
    public void setSenhaInvalida() {
        setErro("Senha inválida");
    }

    /**
     * Exibe uma mensagem de erro indicando que a confirmação de senha é inválida.
     */
    @Override
    public void setConfirmarSenhaInvalida() {
        setErro("Confirmação de senha inválida");
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