package br.pucpr.gss.shared.model.estado;

public class AguardandoInformacoesAdicionais extends Estado {
    @Override
    public String getNome() {
        return "Aguardando informações adicionais";
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
