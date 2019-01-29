package br.pucpr.gss.client.event;

import br.pucpr.gss.shared.model.Usuario;
import com.google.gwt.event.shared.GwtEvent;

public class LoginSucesso extends GwtEvent<LoginSucessoHandler> {
    public final static Type<LoginSucessoHandler> TYPE = new Type<>();

    private Usuario usuario;

    public LoginSucesso(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Type<LoginSucessoHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(LoginSucessoHandler handler) {
        handler.onLoginSucesso(this);
    }
}
