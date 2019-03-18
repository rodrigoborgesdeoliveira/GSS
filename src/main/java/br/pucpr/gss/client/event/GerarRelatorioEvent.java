package br.pucpr.gss.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class GerarRelatorioEvent extends GwtEvent<GerarRelatorioEventHandler> {
    public final static Type<GerarRelatorioEventHandler> TYPE = new Type<>();

    public Type<GerarRelatorioEventHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(GerarRelatorioEventHandler handler) {
        handler.onGerarRelatorio(this);
    }
}
