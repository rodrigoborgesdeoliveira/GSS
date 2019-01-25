package br.pucpr.gss.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface LoginView {

    interface Presenter {
        void onLoginButtonClicked(String email, String senha);

        void onCadastrarClicked();
    }

    void setPresenter(Presenter presenter);

    Widget asWidget();
}
