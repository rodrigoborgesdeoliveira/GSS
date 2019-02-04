package br.pucpr.gss.shared.fabrica;

import br.pucpr.gss.shared.model.estado.Estado;
import br.pucpr.gss.shared.model.prioridade.Prioridade;
import br.pucpr.gss.shared.model.prioridade.PrioridadeAlta;
import br.pucpr.gss.shared.model.prioridade.PrioridadeBaixa;
import br.pucpr.gss.shared.model.prioridade.PrioridadeNormal;

public class FabricaPrioridade extends Fabrica {
    public static final int BAIXA = 0;
    public static final int NORMAL = 1;
    public static final int ALTA = 2;


    /**
     * Anular método.
     *
     * @return null.
     */
    @Override
    public Estado criarEstado(int indiceEstado) {
        return null;
    }


    /**
     * Cria uma prioridade correspondente ao índice indicado.
     *
     * @param indicePrioridade Valor de 0 a 2. Qualquer outro valor será considerado como prioridade normal.
     * @return {@link Prioridade} correspondente ao índice.
     */
    @Override
    public Prioridade criarPrioridade(int indicePrioridade) {
        switch (indicePrioridade) {
            case BAIXA:
                return new PrioridadeBaixa();
            case NORMAL:
            default:
                return new PrioridadeNormal();
            case ALTA:
                return new PrioridadeAlta();
        }
    }
}
