package br.pucpr.gss.shared.model.estado;

import br.pucpr.gss.shared.fabrica.FabricaEstado;

public class EncerramentoRejeitado extends Estado {
    @Override
    public String getNome() {
        return "Encerramento rejeitado";
    }

    @Override
    public int getIndice() {
        return FabricaEstado.ENCERRAMENTO_REJEITADO;
    }

    @Override
    public Estado iniciarAtendimento() {
        return null;
    }

    @Override
    public Estado pausarAtendimento() {
        return new FabricaEstado().criarEstado(FabricaEstado.PAUSADA);
    }

    @Override
    public Estado continuarAtendimento() {
        return new FabricaEstado().criarEstado(FabricaEstado.EM_ANDAMENTO);
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
        return this;
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
