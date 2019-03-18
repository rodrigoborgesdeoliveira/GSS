package br.pucpr.gss.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class RelatorioEvent extends GwtEvent<RelatorioEventHandler> {
    public final static Type<RelatorioEventHandler> TYPE = new Type<>();

    private String htmlRelatorio;

    public RelatorioEvent(String htmlRelatorio) {
        this.htmlRelatorio = htmlRelatorio;
    }

    public String getHtmlRelatorio() {
        return htmlRelatorio;
    }

    public Type<RelatorioEventHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(RelatorioEventHandler handler) {
        handler.onRelatorio(this);
    }
}
