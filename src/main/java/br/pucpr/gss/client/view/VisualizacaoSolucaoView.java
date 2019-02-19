package br.pucpr.gss.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface VisualizacaoSolucaoView {

    interface Presenter {
        void onCancelarButtonClicked();

        void onRejeitarButtonClicked();

        void onAceitarButtonClicked();
    }

    void setPresenter(Presenter presenter);

    Widget asWidget();

    MenuView getMenuView();

    void setDescricaoSolucao(String solucao);
}
