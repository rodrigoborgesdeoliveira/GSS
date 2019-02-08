package br.pucpr.gss.shared.model.prioridade;

import br.pucpr.gss.shared.fabrica.FabricaPrioridade;

public class PrioridadeNormal extends Prioridade {
    @Override
    public String getNome() {
        return "Normal";
    }

    @Override
    public int getIndice() {
        return FabricaPrioridade.NORMAL;
    }
}
