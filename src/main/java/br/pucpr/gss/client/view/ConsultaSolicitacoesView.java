package br.pucpr.gss.client.view;

import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;

public interface ConsultaSolicitacoesView {

    interface Presenter {
        void onCancelarButtonClicked();

        void onConsultarButtonClicked(int indiceSolicitacao);
    }

    void setPresenter(Presenter presenter);

    Widget asWidget();

    MenuView getMenuView();

    void carregarListaSolicitacoes(ArrayList<String> solicitacoes, ArrayList<String> papelUsuario,
                                   ArrayList<String> estado, ArrayList<String> prioridade);
}
