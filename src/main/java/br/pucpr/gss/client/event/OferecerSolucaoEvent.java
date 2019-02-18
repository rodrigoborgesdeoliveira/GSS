package br.pucpr.gss.client.event;

import br.pucpr.gss.shared.model.Solicitacao;
import com.google.gwt.event.shared.GwtEvent;

public class OferecerSolucaoEvent extends GwtEvent<OferecerSolucaoEventHandler> {
    public final static Type<OferecerSolucaoEventHandler> TYPE = new Type<>();

    private Solicitacao solicitacao;

    public OferecerSolucaoEvent(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
    }

    public Solicitacao getSolicitacao() {
        return solicitacao;
    }

    public Type<OferecerSolucaoEventHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(OferecerSolucaoEventHandler handler) {
        handler.onOferecerSolucao(this);
    }
}
