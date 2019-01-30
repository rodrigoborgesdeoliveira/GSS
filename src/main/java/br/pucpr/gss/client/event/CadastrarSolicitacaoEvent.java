package br.pucpr.gss.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class CadastrarSolicitacaoEvent extends GwtEvent<CadastrarSolicitacaoEventHandler> {
    public final static Type<CadastrarSolicitacaoEventHandler> TYPE = new Type<>();

    public Type<CadastrarSolicitacaoEventHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(CadastrarSolicitacaoEventHandler handler) {
        handler.onCadastrarSolicitacao(this);
    }
}
