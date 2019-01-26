package br.pucpr.gss.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface CadastroView {

    interface Presenter {
        void onCadastrarButtonClicked(String email, String senha, String confirmarSenha);

        void onVoltarClicked();
    }

    void setPresenter(Presenter presenter);

    Widget asWidget();
}
