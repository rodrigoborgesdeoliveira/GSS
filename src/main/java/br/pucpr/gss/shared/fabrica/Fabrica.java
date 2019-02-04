package br.pucpr.gss.shared.fabrica;

import br.pucpr.gss.shared.model.estado.Estado;

public abstract class Fabrica {

    /**
     * Cria um estado correspondente ao índice indicado.
     *
     * @return {@link Estado} correspondente ao índice.
     */
    public abstract Estado criarEstado(int indiceEstado);
}
