package br.pucpr.gss.client.controller;

import br.pucpr.gss.shared.model.UsuarioLogin;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;
import br.pucpr.gss.client.view.uibinder.LoginView;

public class LoginController implements EntryPoint {

    public void onModuleLoad() {
        RootPanel.get().add(new LoginView());
    }

    public void processarLogin(UsuarioLogin login) throws IllegalArgumentException{
        if (!login.isEmailValido()) {
            throw new IllegalArgumentException("Email inválido");
        }

        if (!login.isSenhaValida()) {
            throw new IllegalArgumentException("Senha inválida");
        }


    }
}
