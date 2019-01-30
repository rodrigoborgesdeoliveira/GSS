package br.pucpr.gss.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface CadastroSolicitacaoView {

    interface Presenter {

    }

    void setPresenter(Presenter presenter);

    Widget asWidget();

    MenuView getMenuView();
}
