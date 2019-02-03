package br.pucpr.gss.client.event;

import br.pucpr.gss.shared.model.Solicitacao;
import com.google.gwt.event.shared.GwtEvent;

public class DetalhesSolicitacaoEvent extends GwtEvent<DetalhesSolicitacaoEventHandler> {
    public final static Type<DetalhesSolicitacaoEventHandler> TYPE = new Type<>();

    private Solicitacao solicitacao;

    public DetalhesSolicitacaoEvent(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
    }

    public Solicitacao getSolicitacao() {
        return solicitacao;
    }

    public Type<DetalhesSolicitacaoEventHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(DetalhesSolicitacaoEventHandler handler) {
        handler.onDetalhesSolicitacao(this);
    }
}
