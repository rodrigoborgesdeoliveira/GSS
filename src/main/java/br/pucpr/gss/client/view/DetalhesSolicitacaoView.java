package br.pucpr.gss.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface DetalhesSolicitacaoView {

    interface Presenter {

    }

    void setPresenter(Presenter presenter);

    Widget asWidget();

    MenuView getMenuView();
}
