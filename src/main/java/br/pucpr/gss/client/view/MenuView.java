package br.pucpr.gss.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface MenuView {

    interface Presenter {
        String getOnDashboardClickedToken();

        String getOnCadastrarSolicitacaoClickedToken();

        String getOnConsultarSolicitacoesClickedToken();

        String getOnGerarIndicadoresClickedToken();

        String getOnGerenciarCadastrosClickedToken();

        String getOnSairClickedToken();

        void onSairClicked();
    }

    void setPresenter(Presenter presenter);

    Widget asWidget();

    void setUIAdmin();
}
