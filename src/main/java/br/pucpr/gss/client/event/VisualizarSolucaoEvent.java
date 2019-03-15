package br.pucpr.gss.client.event;

import br.pucpr.gss.shared.model.Solicitacao;
import com.google.gwt.event.shared.GwtEvent;

public class VisualizarSolucaoEvent extends GwtEvent<VisualizarSolucaoEventHandler> {
    public final static Type<VisualizarSolucaoEventHandler> TYPE = new Type<>();

    private Solicitacao solicitacao;

    public VisualizarSolucaoEvent(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
    }

    public Solicitacao getSolicitacao() {
        return solicitacao;
    }

    public Type<VisualizarSolucaoEventHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(VisualizarSolucaoEventHandler handler) {
        handler.onVisualizarSolucao(this);
    }
}
