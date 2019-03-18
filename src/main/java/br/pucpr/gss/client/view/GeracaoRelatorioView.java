package br.pucpr.gss.client.view;

import com.google.gwt.user.client.ui.Widget;

import java.util.Date;

public interface GeracaoRelatorioView {

    interface Presenter {
        /**
         * Gera um relatório com os filtros dos parâmetros.
         *
         * @param filtroDataInicial Intervalo inicial da data de criação.
         * @param filtroDataFinal   Intervalo final da data de criação.
         * @param showSolicitante   Exibir solicitações com papel de solicitante.
         * @param showAtendente     Exibir solicitações com papel de atendente.
         * @param showGestor        Exibir solicitações com papel de gestor.
         * @return Dados em forma de texto para ser exibido no relatório.
         */
        String gerarRelatorio(Date filtroDataInicial, Date filtroDataFinal, boolean showSolicitante, boolean showAtendente,
                              boolean showGestor);
    }

    void setPresenter(Presenter presenter);

    Widget asWidget();

    MenuView getMenuView();
}
