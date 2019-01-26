package br.pucpr.gss.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class VoltarEvent extends GwtEvent<VoltarEventHandler> {
    public static Type<VoltarEventHandler> TYPE = new Type<VoltarEventHandler>();

    public Type<VoltarEventHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(VoltarEventHandler handler) {
        handler.onVoltar(this);
    }
}
