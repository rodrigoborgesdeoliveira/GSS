package br.pucpr.gss.shared.fabrica;

import br.pucpr.gss.shared.model.estado.*;

public class FabricaEstado extends Fabrica {
    public static final int AGUARDANDO_ATENDIMENTO = 0;
    public static final int EM_ANDAMENTO = 1;
    public static final int PAUSADA = 2;
    public static final int ENCERRAMENTO_PROPOSTO = 3;
    public static final int ENCERRAMENTO_REJEITADO = 4;
    public static final int ENCERRADA = 5;
    public static final int RESPONDIDA = 6;
    public static final int AGUARDANDO_INFORMACOES_ADICIONAIS = 7;

    /**
     * Cria um estado correspondente ao índice indicado.
     *
     * @param indiceEstado Valor de 0 a 7. Qualquer outro valor criará um estado padrão correspondente ao índice 0.
     * @return {@link Estado} correspondente ao índice.
     */
    @Override
    public Estado criarEstado(int indiceEstado) {
        switch (indiceEstado) {
            case AGUARDANDO_ATENDIMENTO:
            default:
                return new AguardandoAtendimento();
            case EM_ANDAMENTO:
                return new EmAndamento();
            case PAUSADA:
                return new Pausada();
            case ENCERRAMENTO_PROPOSTO:
                return new EncerramentoProposto();
            case ENCERRAMENTO_REJEITADO:
                return new EncerramentoRejeitado();
            case ENCERRADA:
                return new Encerrada();
            case RESPONDIDA:
                return new Respondida();
            case AGUARDANDO_INFORMACOES_ADICIONAIS:
                return new AguardandoInformacoesAdicionais();
        }
    }
}
