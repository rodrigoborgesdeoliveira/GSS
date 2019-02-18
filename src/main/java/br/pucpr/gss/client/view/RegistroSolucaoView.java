package br.pucpr.gss.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface RegistroSolucaoView {

    interface Presenter {
        void onCancelarButtonClicked();

        void onSalvarButtonClicked(String solucao);
    }

    void setPresenter(Presenter presenter);

    Widget asWidget();

    MenuView getMenuView();
}
