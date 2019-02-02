package br.pucpr.gss.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ConsultarSolicitacoesEvent extends GwtEvent<ConsultarSolicitacoesEventHandler> {
    public final static Type<ConsultarSolicitacoesEventHandler> TYPE = new Type<>();

    public Type<ConsultarSolicitacoesEventHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(ConsultarSolicitacoesEventHandler handler) {
        handler.onConsultarSolicitacoes(this);
    }
}
