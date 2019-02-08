package br.pucpr.gss.shared.model.prioridade;

import br.pucpr.gss.shared.fabrica.FabricaPrioridade;

public class PrioridadeBaixa extends Prioridade {
    @Override
    public String getNome() {
        return "Baixa";
    }

    @Override
    public int getIndice() {
        return FabricaPrioridade.BAIXA;
    }
}
