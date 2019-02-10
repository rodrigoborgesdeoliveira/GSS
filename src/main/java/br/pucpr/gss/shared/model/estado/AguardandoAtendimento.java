package br.pucpr.gss.shared.model.estado;

import br.pucpr.gss.shared.fabrica.FabricaEstado;

public class AguardandoAtendimento extends Estado {

    @Override
    public String getNome() {
        return "Aguardando atendimento";
    }

    @Override
    public int getIndice() {
        return FabricaEstado.AGUARDANDO_ATENDIMENTO;
    }

    @Override
    public Estado iniciarAtendimento() {
        return new FabricaEstado().criarEstado(FabricaEstado.EM_ANDAMENTO);
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
