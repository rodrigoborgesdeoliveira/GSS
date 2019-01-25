package br.pucpr.gss.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class CadastrarEvent extends GwtEvent<CadastrarEventHandler> {
    public static Type<CadastrarEventHandler> TYPE = new Type<>();

    public Type<CadastrarEventHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(CadastrarEventHandler handler) {
        handler.onCadastrar(this);
    }
}
