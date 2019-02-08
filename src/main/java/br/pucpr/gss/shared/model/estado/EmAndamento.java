package br.pucpr.gss.shared.model.estado;

import br.pucpr.gss.shared.fabrica.FabricaEstado;

public class EmAndamento extends Estado {
    @Override
    public String getNome() {
        return "Em andamento";
    }

    @Override
    public int getIndice() {
        return FabricaEstado.EM_ANDAMENTO;
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
        return null;
    }

    @Override
    public Estado aceitarSolucao() {
        return null;
    }

    @Override
    public Estado rejeitarSolucao() {
        return null;
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
