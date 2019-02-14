package br.pucpr.gss.client.event;

import br.pucpr.gss.shared.model.InformacaoAdicional;
import com.google.gwt.event.shared.GwtEvent;

public class RegistroInformacoesAdicionaisEvent extends GwtEvent<RegistroInformacoesAdicionaisEventHandler> {
    public static Type<RegistroInformacoesAdicionaisEventHandler> TYPE = new Type<>();

    public Type<RegistroInformacoesAdicionaisEventHandler> getAssociatedType() {
        return TYPE;
    }

    private InformacaoAdicional informacaoAdicional;

    public RegistroInformacoesAdicionaisEvent(InformacaoAdicional informacaoAdicional) {
        this.informacaoAdicional = informacaoAdicional;
    }

    public InformacaoAdicional getInformacaoAdicional() {
        return informacaoAdicional;
    }

    protected void dispatch(RegistroInformacoesAdicionaisEventHandler handler) {
        handler.onRegistroInformacoesAdicionais(this);
    }
}
