package br.pucpr.gss.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class LoginEvent extends GwtEvent<LoginEventHandler> {
    public static Type<LoginEventHandler> TYPE = new Type<>();

    public Type<LoginEventHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(LoginEventHandler handler) {
        handler.onLogin(this);
    }
}
