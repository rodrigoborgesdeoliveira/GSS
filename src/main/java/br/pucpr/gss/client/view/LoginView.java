package br.pucpr.gss.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface LoginView {

    interface Presenter {
        void onLoginButtonClicked(String email, String senha);

        String getOnCadastrarClickedToken();
    }

    void setPresenter(Presenter presenter);

    Widget asWidget();

    void setEmailInvalido();

    void setSenhaInvalida();

    void ocultarLabelErro();

    void setErro(String mensagemErro);
}
