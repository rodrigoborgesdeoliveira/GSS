package br.pucpr.gss.client.event;

import br.pucpr.gss.shared.model.Solicitacao;
import com.google.gwt.event.shared.GwtEvent;

public class RequisitarInformacoesAdicionaisEvent extends GwtEvent<RequisitarInformacoesAdicionaisEventHandler> {
    public final static Type<RequisitarInformacoesAdicionaisEventHandler> TYPE = new Type<>();

    private Solicitacao solicitacao;

    public RequisitarInformacoesAdicionaisEvent(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
    }

    public Solicitacao getSolicitacao() {
        return solicitacao;
    }

    public Type<RequisitarInformacoesAdicionaisEventHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(RequisitarInformacoesAdicionaisEventHandler handler) {
        handler.onRequisitarInformacoesAdicionais(this);
    }
}
