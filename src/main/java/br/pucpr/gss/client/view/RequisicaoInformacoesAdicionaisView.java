package br.pucpr.gss.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface RequisicaoInformacoesAdicionaisView {

    interface Presenter {

    }

    void setPresenter(Presenter presenter);

    Widget asWidget();

    MenuView getMenuView();

}
