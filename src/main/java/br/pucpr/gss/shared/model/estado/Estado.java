package br.pucpr.gss.shared.model.estado;

import com.google.gwt.user.client.rpc.IsSerializable;

public abstract class Estado implements IsSerializable {

    /**
     * Construtor para o serializable.
     */
    public Estado() {
    }

    public abstract String getNome();

    public abstract int getIndice();

    public abstract Estado iniciarAtendimento();

    public abstract Estado pausarAtendimento();

    public abstract Estado continuarAtendimento();

    public abstract Estado oferecerSolucao();

    public abstract Estado aceitarSolucao();

    public abstract Estado rejeitarSolucao();

    public abstract Estado requisitarInformacoesAdicionais();

    public abstract Estado registrarInformacoesAdicionais();
}
