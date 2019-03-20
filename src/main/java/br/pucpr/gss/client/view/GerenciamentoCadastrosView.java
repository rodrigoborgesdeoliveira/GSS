package br.pucpr.gss.client.view;

import com.google.gwt.user.client.ui.Widget;

import java.util.List;

public interface GerenciamentoCadastrosView {

    interface Presenter {
        /**
         * Marca um usuário como administrador ou não.
         *
         * @param indiceListaUsuarios Índice do usuário na lista.
         * @param asAdmin             true, se for setar como administrador, false, caso contrário.
         */
        void setAdmin(int indiceListaUsuarios, boolean asAdmin);
    }

    void setPresenter(Presenter presenter);

    Widget asWidget();

    MenuView getMenuView();

    void carregarListaUsuarios(List<String> nomeUsuarios, List<Boolean> isUsuariosAdmin);
}
