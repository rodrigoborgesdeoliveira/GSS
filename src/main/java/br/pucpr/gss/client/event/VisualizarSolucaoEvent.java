package br.pucpr.gss.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class VisualizarSolucaoEvent extends GwtEvent<VisualizarSolucaoEventHandler> {
    public final static Type<VisualizarSolucaoEventHandler> TYPE = new Type<>();

    public Type<VisualizarSolucaoEventHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(VisualizarSolucaoEventHandler handler) {
        handler.onVisualizarSolucao(this);
    }
}
