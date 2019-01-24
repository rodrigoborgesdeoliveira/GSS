package br.pucpr.gss.client.controller;

import br.pucpr.gss.client.enums.Paginas;
import br.pucpr.gss.client.view.uibinder.CadastroView;
import br.pucpr.gss.client.view.uibinder.LoginView;
import br.pucpr.gss.server.dao.Conexao;
import br.pucpr.gss.shared.model.UsuarioLogin;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;

public class LoginController implements EntryPoint {

    public void onModuleLoad() {
        RootPanel.get().add(new LoginView());

        History.addValueChangeHandler(Paginas.LOGIN);
//        History.fireCurrentHistoryState();
    }

    public void processarLogin(UsuarioLogin login) throws IllegalArgumentException {
        if (!login.isEmailValido()) {
            throw new IllegalArgumentException("Email inválido");
        }

        if (!login.isSenhaValida()) {
            throw new IllegalArgumentException("Senha inválida");
        }


    }

    public void handleClickCadastrar() {
        History.newItem(Paginas.CADASTRO.getPaginaIndex());
    }
}
