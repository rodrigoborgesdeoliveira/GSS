package br.pucpr.gss.client.view.uibinder;

import br.pucpr.gss.client.view.CadastroView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialTextBox;

public class CadastroViewImpl extends Composite implements CadastroView {

    interface CadastroViewUiBinder extends UiBinder<Widget, CadastroViewImpl> {
    }

    private static CadastroViewUiBinder uiBinder = GWT.create(CadastroViewUiBinder.class);

    @UiField
    MaterialTextBox textBoxEmail, textBoxSenha, textBoxConfirmarSenha;
    @UiField
    MaterialButton buttonCadastrar, buttonCancelar;

    private Presenter presenter;

    public CadastroViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));

        // Fazer com que o label seja animado ao ganhar e perder foco
        textBoxEmail.addFocusHandler(event -> textBoxEmail.setFocus(true));
        textBoxEmail.addBlurHandler(event -> textBoxEmail.setFocus(false));
        textBoxSenha.addFocusHandler(event -> textBoxSenha.setFocus(true));
        textBoxSenha.addBlurHandler(event -> textBoxSenha.setFocus(false));
        textBoxConfirmarSenha.addFocusHandler(event -> textBoxConfirmarSenha.setFocus(true));
        textBoxConfirmarSenha.addBlurHandler(event -> textBoxConfirmarSenha.setFocus(false));

        KeyDownHandler enterKeyCadastrar = event -> {
            if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                onClickCadastrar(null);
            }
        };

        textBoxEmail.addKeyDownHandler(enterKeyCadastrar);
        textBoxSenha.addKeyDownHandler(enterKeyCadastrar);
        textBoxConfirmarSenha.addKeyDownHandler(enterKeyCadastrar);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @UiHandler("buttonCadastrar")
    void onClickCadastrar(ClickEvent event) {
        if (presenter != null) {
            presenter.onCadastrarButtonClicked(textBoxEmail.getText(), textBoxSenha.getText(),
                    textBoxConfirmarSenha.getText());
        }
    }

    @UiHandler("buttonCancelar")
    void onClickVoltar(ClickEvent event) {
        if (presenter != null) {
            presenter.onCancelarButtonClicked();
        }
    }

    /**
     * Exibe uma mensagem de erro indicando que o Email é inválido.
     */
    @Override
    public void setEmailInvalido() {
        textBoxEmail.setErrorText("Email inválido");
        textBoxEmail.setFocus(true);
    }

    /**
     * Exibe uma mensagem de erro indicando que a Senha é inválida.
     */
    @Override
    public void setSenhaInvalida() {
        textBoxSenha.setErrorText("Senha inválida");
        textBoxSenha.setFocus(true);
    }

    /**
     * Exibe uma mensagem de erro indicando que a Confirmação de Senha é inválida.
     */
    @Override
    public void setConfirmarSenhaInvalida() {
        textBoxConfirmarSenha.setErrorText("Confirmação de senha inválida");
        textBoxConfirmarSenha.setFocus(true);
    }

    @Override
    public void ocultarLabelErro() {
        textBoxEmail.clearErrorText();
        textBoxEmail.removeErrorModifiers();
        textBoxSenha.clearErrorText();
        textBoxSenha.removeErrorModifiers();
        textBoxConfirmarSenha.clearErrorText();
        textBoxConfirmarSenha.removeErrorModifiers();
    }
}