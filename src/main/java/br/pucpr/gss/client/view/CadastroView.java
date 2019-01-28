package br.pucpr.gss.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface CadastroView {

    interface Presenter {
        void onCadastrarButtonClicked(String email, String senha, String confirmarSenha);

        void onVoltarClicked();
    }

    void setPresenter(Presenter presenter);

    Widget asWidget();

    void setEmailInvalido();

    void setSenhaInvalida();

    void setConfirmarSenhaInvalida();

    void ocultarLabelErro();

    void setErro(String mensagemErro);
}
