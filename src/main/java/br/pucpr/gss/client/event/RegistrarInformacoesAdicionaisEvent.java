package br.pucpr.gss.client.event;

import br.pucpr.gss.shared.model.InformacaoAdicional;
import com.google.gwt.event.shared.GwtEvent;

public class RegistrarInformacoesAdicionaisEvent extends GwtEvent<RegistrarInformacoesAdicionaisEventHandler> {
    public static Type<RegistrarInformacoesAdicionaisEventHandler> TYPE = new Type<>();

    public Type<RegistrarInformacoesAdicionaisEventHandler> getAssociatedType() {
        return TYPE;
    }

    private InformacaoAdicional informacaoAdicional;

    public RegistrarInformacoesAdicionaisEvent(InformacaoAdicional informacaoAdicional) {
        this.informacaoAdicional = informacaoAdicional;
    }

    public InformacaoAdicional getInformacaoAdicional() {
        return informacaoAdicional;
    }

    protected void dispatch(RegistrarInformacoesAdicionaisEventHandler handler) {
        handler.onRegistroInformacoesAdicionais(this);
    }
}
