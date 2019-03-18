package br.pucpr.gss.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface RelatorioView {

    interface Presenter {

    }

    void setPresenter(Presenter presenter);

    Widget asWidget();

    void setHtml(String html);
}
