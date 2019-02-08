package br.pucpr.gss.shared.model.prioridade;

import br.pucpr.gss.shared.fabrica.FabricaPrioridade;

public class PrioridadeAlta extends Prioridade {
    @Override
    public String getNome() {
        return "Alta";
    }

    @Override
    public int getIndice() {
        return FabricaPrioridade.ALTA;
    }
}
