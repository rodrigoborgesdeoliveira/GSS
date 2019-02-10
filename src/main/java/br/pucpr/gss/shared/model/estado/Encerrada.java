package br.pucpr.gss.shared.model.estado;

import br.pucpr.gss.shared.fabrica.FabricaEstado;

public class Encerrada extends Estado {
    @Override
    public String getNome() {
        return "Encerrada";
    }

    @Override
    public int getIndice() {
        return FabricaEstado.ENCERRADA;
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
        return this;
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
