package com.pucpr.gss.client.controller;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;
import com.pucpr.gss.client.view.uibinder.LoginView;
import com.pucpr.gss.shared.model.UsuarioLogin;

public class LoginController implements EntryPoint {

    public void onModuleLoad() {
        RootPanel.get().add(new LoginView());
    }

    public void processarLogin(UsuarioLogin login) throws IllegalArgumentException{
        if (!login.isEmailValido()) {
            throw new IllegalArgumentException("Email inválido");
        }
    }
}
