package br.pucpr.gss.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface RequisicaoInformacoesAdicionaisView {

    interface Presenter {
        void onCancelarButtonClicked();

        void onSalvarButtonClicked(String descricao);
    }

    void setPresenter(Presenter presenter);

    Widget asWidget();

    MenuView getMenuView();

    void setDescricao(String descricao);

    void setInformacoesAdicionais(String informacoesAdicionais);
}
