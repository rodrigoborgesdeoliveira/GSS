package br.pucpr.gss.shared.fabrica;

import br.pucpr.gss.shared.model.estado.Estado;
import br.pucpr.gss.shared.model.prioridade.Prioridade;

public abstract class Fabrica {

    /**
     * Cria um estado correspondente ao índice indicado.
     *
     * @return {@link Estado} correspondente ao índice.
     */
    public abstract Estado criarEstado(int indiceEstado);

    /**
     * Cria uma prioridade correspondente ao índice indicado.
     *
     * @return {@link Prioridade} correspondente ao índice.
     */
    public abstract Prioridade criarPrioridade(int indicePrioridade);
}
