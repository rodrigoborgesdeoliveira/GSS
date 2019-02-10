package br.pucpr.gss.shared.model.estado;

import br.pucpr.gss.shared.fabrica.FabricaEstado;

public class Respondida extends Estado {
    @Override
    public String getNome() {
        return "Respondida";
    }

    @Override
    public int getIndice() {
        return FabricaEstado.RESPONDIDA;
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
        return this;
    }
}
