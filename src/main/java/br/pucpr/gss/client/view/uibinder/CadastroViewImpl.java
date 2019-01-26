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
}