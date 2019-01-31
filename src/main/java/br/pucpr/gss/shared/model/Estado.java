package br.pucpr.gss.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

public abstract class Estado implements IsSerializable {

    /**
     * Construtor para o serializable.
     */
    public Estado() {
    }

    abstract Estado iniciarAtendimento();

    abstract Estado pausarAtendimento();

    abstract Estado continuarAtendimento();

    abstract Estado oferecerSolucao();

    abstract Estado aceitarSolucao();

    abstract Estado rejeitarSolucao();

    abstract Estado requisitarInformacoesAdicionais();

    abstract Estado registrarInformacoesAdicionais();
}
