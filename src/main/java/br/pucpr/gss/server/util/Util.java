package br.pucpr.gss.server.util;

import br.pucpr.gss.shared.model.estado.Estado;
import br.pucpr.gss.shared.model.prioridade.Prioridade;

public class Util {

    /**
     * Uso dos métodos deve ser na forma Util.metodo().
     */
    private Util() {
    }

    /**
     * Cria um estado correspondente ao índice indicado.
     *
     * @param indiceEstado Valor de 0 a 7. Qualquer outro valor criará um estado <em>Aguardando atendimento</em>.
     * @return {@link Estado} correspondente ao índice.
     */
    public static Estado recuperarEstado(int indiceEstado) {
        switch (indiceEstado) {
            case 0:
            default:
                // Aguardando atendimento
                return null;
            case 1:
                // Em andamento
                return null;
            case 2:
                // Pausada
                return null;
            case 3:
                // Encerramento proposto
                return null;
            case 4:
                // Encerramento rejeitado
                return null;
            case 5:
                // Encerrada
                return null;
            case 6:
                // Respondida
                return null;
            case 7:
                // Aguardando informações adicionais
                return null;
        }
    }

    /**
     * Cria uma prioridade correspondente ao índice indicado.
     *
     * @param indicePrioridade Valor de 0 a 2. Qualquer outro valor será considerado como prioridade normal.
     * @return {@link Prioridade} correspondente ao índice.
     */
    public static Prioridade recuperarPrioridade(int indicePrioridade) {
        switch (indicePrioridade) {
            case 0:
                // Prioridade baixa
                return null;
            case 1:
            default:
                // Prioridade normal
                return null;
            case 2:
                // Prioridade alta
                return null;
        }
    }
}
