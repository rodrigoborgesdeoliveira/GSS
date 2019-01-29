package br.pucpr.gss.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface LoginSucessoHandler extends EventHandler {
    void onLoginSucesso(LoginSucesso event);
}
