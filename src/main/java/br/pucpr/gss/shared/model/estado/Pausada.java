package br.pucpr.gss.shared.model.estado;

import br.pucpr.gss.shared.fabrica.FabricaEstado;

public class Pausada extends Estado {
    @Override
    public String getNome() {
        return "Pausada";
    }

    @Override
    public int getIndice() {
        return FabricaEstado.PAUSADA;
    }

    @Override
    public Estado iniciarAtendimento() {
        return null;
    }

    @Override
    public Estado pausarAtendimento() {
        return this;
    }

    @Override
    public Estado continuarAtendimento() {
        return new FabricaEstado().criarEstado(FabricaEstado.EM_ANDAMENTO);
    }

    @Override
    public Estado oferecerSolucao() {
        return new FabricaEstado().criarEstado(FabricaEstado.ENCERRAMENTO_PROPOSTO);
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
        return new FabricaEstado().criarEstado(FabricaEstado.AGUARDANDO_INFORMACOES_ADICIONAIS);
    }

    @Override
    public Estado registrarInformacoesAdicionais() {
        return null;
    }
}
