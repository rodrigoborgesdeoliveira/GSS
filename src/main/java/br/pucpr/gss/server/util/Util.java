package br.pucpr.gss.server.util;

import br.pucpr.gss.shared.model.Estado;

public class Util {

    /**
     * Uso dos métodos deve ser na forma Util.metodo().
     */
    private Util() {
    }

    /**
     * Cria um estado correspondente ao índice indicado.
     *
     * @param indiceEstado Valor entre 0 e 7. Qualquer outro valor criará um estado <em>Aguardando atendimento</em>.
     * @return Estado correspondente ao índice.
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
}
