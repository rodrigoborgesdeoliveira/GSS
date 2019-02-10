package br.pucpr.gss.shared.model.estado;

import br.pucpr.gss.shared.fabrica.FabricaEstado;

public class EncerramentoProposto extends Estado {
    @Override
    public String getNome() {
        return "Encerramento proposto";
    }

    @Override
    public int getIndice() {
        return FabricaEstado.ENCERRAMENTO_PROPOSTO;
    }

    @Override
    public Estado iniciarAtendimento() {
        return null;
    }

    @Override
    public Estado pausarAtendimento() {
        return null;
    }

    @Override
    public Estado continuarAtendimento() {
        return null;
    }

    @Override
    public Estado oferecerSolucao() {
        return this;
    }

    @Override
    public Estado aceitarSolucao() {
        return new FabricaEstado().criarEstado(FabricaEstado.ENCERRADA);
    }

    @Override
    public Estado rejeitarSolucao() {
        return new FabricaEstado().criarEstado(FabricaEstado.ENCERRAMENTO_REJEITADO);
    }

    @Override
    public Estado requisitarInformacoesAdicionais() {
        return null;
    }

    @Override
    public Estado registrarInformacoesAdicionais() {
        return null;
    }
}
