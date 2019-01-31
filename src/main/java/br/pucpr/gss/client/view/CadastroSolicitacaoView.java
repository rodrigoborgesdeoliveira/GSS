package br.pucpr.gss.client.view;

import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;

public interface CadastroSolicitacaoView {

    interface Presenter {
        void onCadastrarButtonClicked(String titulo, int indiceSetor, String descricao);

        void onCancelarButtonClicked();
    }

    void setPresenter(Presenter presenter);

    Widget asWidget();

    MenuView getMenuView();

    void carregarListaSetores(ArrayList<String> setores);
}
