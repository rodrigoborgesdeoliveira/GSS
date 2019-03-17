package br.pucpr.gss.client.view;

import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;

public interface ConsultaSolicitacoesView {

    interface Presenter {
        void onCancelarButtonClicked();

        void onConsultarButtonClicked(int indiceSolicitacao);

        /**
         * Filtra a lista de solicitações por papel.
         *
         * @param showSolicitante true, se deseja exibir solicitações em que o usuário é solicitante.
         * @param showAtendente   true, se deseja exibir solicitações em que o usuário é atendente.
         * @param showGestor      true, se deseja exibir solicitações em que o usuário é gestor.
         */
        void filtrarPapel(boolean showSolicitante, boolean showAtendente, boolean showGestor);
    }

    void setPresenter(Presenter presenter);

    Widget asWidget();

    MenuView getMenuView();

    void carregarListaSolicitacoes(ArrayList<String> solicitacoes, ArrayList<String> papelUsuario,
                                   ArrayList<String> estado, ArrayList<String> prioridade);
}
