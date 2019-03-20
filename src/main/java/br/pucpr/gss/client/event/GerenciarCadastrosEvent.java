package br.pucpr.gss.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class GerenciarCadastrosEvent extends GwtEvent<GerenciarCadastrosEventHandler> {
    public final static Type<GerenciarCadastrosEventHandler> TYPE = new Type<>();

    public Type<GerenciarCadastrosEventHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(GerenciarCadastrosEventHandler handler) {
        handler.onGerenciarCadastro(this);
    }
}
