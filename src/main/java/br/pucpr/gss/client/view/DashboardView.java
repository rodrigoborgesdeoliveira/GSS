package br.pucpr.gss.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface DashboardView {

    interface Presenter {

    }

    void setPresenter(Presenter presenter);

    MenuView getMenuView();

    Widget asWidget();
}
