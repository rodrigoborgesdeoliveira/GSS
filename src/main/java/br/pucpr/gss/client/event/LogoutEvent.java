package br.pucpr.gss.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class LogoutEvent extends GwtEvent<LogoutEventHandler> {
    public final static Type<LogoutEventHandler> TYPE = new Type<>();

    public Type<LogoutEventHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(LogoutEventHandler handler) {
        handler.onLogout(this);
    }
}
