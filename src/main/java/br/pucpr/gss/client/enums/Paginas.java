package br.pucpr.gss.client.enums;

import br.pucpr.gss.client.view.uibinder.CadastroView;
import br.pucpr.gss.client.view.uibinder.LoginView;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public enum Paginas implements IsSerializable, ValueChangeHandler<String> {
    LOGIN("0") {
        @Override
        public void carregarPagina() {
            inicializarPagina(new LoginView());
        }
    },
    CADASTRO("1") {
        @Override
        public void carregarPagina() {
            inicializarPagina(new CadastroView());
        }
    };

    private static void inicializarPagina(Widget widget) {
        RootPanel.get().clear();
        RootPanel.get().add(widget);
    }

    private String paginaIndex;

    Paginas(String paginaIndex) {
        this.paginaIndex = paginaIndex;
    }

    public String getPaginaIndex() {
        return paginaIndex;
    }

    public abstract void carregarPagina();

    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
        String tokenHistorico = event.getValue();

        if (tokenHistorico.length() > 0) {
            values()[Integer.parseInt(tokenHistorico)].carregarPagina();
        } else {
            LOGIN.carregarPagina();
        }
    }
}
