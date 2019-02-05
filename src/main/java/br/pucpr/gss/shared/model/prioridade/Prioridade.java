package br.pucpr.gss.shared.model.prioridade;

import com.google.gwt.user.client.rpc.IsSerializable;

public abstract class Prioridade implements IsSerializable {

    /**
     * Construtor para o serializable.
     */
    public Prioridade() {
    }

    public abstract String getNome();
}
