package br.pucpr.gss.shared.model;

import br.pucpr.gss.shared.fabrica.FabricaEstado;
import br.pucpr.gss.shared.fabrica.FabricaPrioridade;
import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.ArrayList;
import java.util.List;

public class Relatorio implements IsSerializable {

    private Usuario usuario;
    private List<Solicitacao> listaSolicitacoes;

    // Por estado
    private ArrayList<Solicitacao> estadoAguardandoAtendimento = new CountPapelSolicitacao<>();
    private ArrayList<Solicitacao> estadoEmAndamento = new CountPapelSolicitacao<>();
    private ArrayList<Solicitacao> estadoPausada = new CountPapelSolicitacao<>();
    private ArrayList<Solicitacao> estadoEncerramentoProposto = new CountPapelSolicitacao<>();
    private ArrayList<Solicitacao> estadoEncerramentoRejeitado = new CountPapelSolicitacao<>();
    private ArrayList<Solicitacao> estadoEncerrada = new CountPapelSolicitacao<>();
    private ArrayList<Solicitacao> estadoRespondida = new CountPapelSolicitacao<>();
    private ArrayList<Solicitacao> estadoAguardandoInformacoesAdicionais = new CountPapelSolicitacao<>();

    // Por prioridade
    private ArrayList<Solicitacao> prioridadeBaixa = new CountPapelSolicitacao<>();
    private ArrayList<Solicitacao> prioridadeNormal = new CountPapelSolicitacao<>();
    private ArrayList<Solicitacao> prioridadeAlta = new CountPapelSolicitacao<>();

    // Por papel
    private ArrayList<Solicitacao> papelSolicitante = new ArrayList<>();
    private ArrayList<Solicitacao> papelAtendente = new ArrayList<>();
    private ArrayList<Solicitacao> papelGestor = new ArrayList<>();

    /**
     * Construtor para o serializable.
     */
    public Relatorio() {
    }

    public Relatorio(Usuario usuario, List<Solicitacao> listaSolicitacoes) {
        this.usuario = usuario;
        this.listaSolicitacoes = listaSolicitacoes;

        gerarIndicadores();
    }

    private void gerarIndicadores() {
        listaSolicitacoes.forEach(solicitacao -> {
            // Categorizar por estado
            switch (solicitacao.getEstado().getIndice()) {
                case FabricaEstado.AGUARDANDO_ATENDIMENTO:
                    estadoAguardandoAtendimento.add(solicitacao);
                    break;
                case FabricaEstado.EM_ANDAMENTO:
                    estadoEmAndamento.add(solicitacao);
                    break;
                case FabricaEstado.PAUSADA:
                    estadoPausada.add(solicitacao);
                    break;
                case FabricaEstado.ENCERRAMENTO_PROPOSTO:
                    estadoEncerramentoProposto.add(solicitacao);
                    break;
                case FabricaEstado.ENCERRAMENTO_REJEITADO:
                    estadoEncerramentoRejeitado.add(solicitacao);
                    break;
                case FabricaEstado.ENCERRADA:
                    estadoEncerrada.add(solicitacao);
                    break;
                case FabricaEstado.RESPONDIDA:
                    estadoRespondida.add(solicitacao);
                    break;
                case FabricaEstado.AGUARDANDO_INFORMACOES_ADICIONAIS:
                    estadoAguardandoInformacoesAdicionais.add(solicitacao);
                    break;
            }

            // Categorizar por prioridade
            switch (solicitacao.getPrioridade().getIndice()) {
                case FabricaPrioridade.BAIXA:
                    prioridadeBaixa.add(solicitacao);
                    break;
                case FabricaPrioridade.NORMAL:
                    prioridadeNormal.add(solicitacao);
                    break;
                case FabricaPrioridade.ALTA:
                    prioridadeAlta.add(solicitacao);
                    break;
            }

            // Categorizar por papel
            if (usuario.getIdFuncionario() == solicitacao.getIdSolicitante()) {
                // Solicitante
                papelSolicitante.add(solicitacao);
            } else if (usuario.getIdFuncionario() == solicitacao.getIdAtendente()) {
                // Atendente
                papelAtendente.add(solicitacao);
            } else {
                // Gestor
                papelGestor.add(solicitacao);
            }
        });
    }

    public int getIndicadorTotalSolicitacoes() {
        return listaSolicitacoes.size();
    }

    public int getIndicadorSolicitacoesAbertas() {
        return listaSolicitacoes.size() - estadoEncerrada.size();
    }

    public int getIndicadorSolicitacoesEncerradas() {
        return estadoEncerrada.size();
    }

    public int getIndicadorSolicitacoesPrioridadeBaixa() {
        return prioridadeBaixa.size();
    }

    public int getIndicadorSolicitacoesPrioridadeNormal() {
        return prioridadeNormal.size();
    }

    public int getIndicadorSolicitacoesPrioridadeAlta() {
        return prioridadeAlta.size();
    }

    public int getIndicadorSolicitacoesPapelSolicitante() {
        return papelSolicitante.size();
    }

    public int getIndicadorSolicitacoesPapelAtendente() {
        return papelAtendente.size();
    }

    public int getIndicadorSolicitacoesPapelGestor() {
        return papelGestor.size();
    }

    public int getIndicadorSolicitacoesEstadoAguardandoAtendimento() {
        return estadoAguardandoAtendimento.size();
    }

    public int getIndicadorSolicitacoesEstadoEmAndamento() {
        return estadoEmAndamento.size();
    }

    public int getIndicadorSolicitacoesEstadoPausada() {
        return estadoPausada.size();
    }

    public int getIndicadorSolicitacoesEstadoEncerramentoProposto() {
        return estadoEncerramentoProposto.size();
    }

    public int getIndicadorSolicitacoesEstadoEncerramentoRejeitado() {
        return estadoEncerramentoRejeitado.size();
    }

    public int getIndicadorSolicitacoesEstadoEncerrada() {
        return estadoEncerrada.size();
    }

    public int getIndicadorSolicitacoesEstadoRespondida() {
        return estadoRespondida.size();
    }

    public int getIndicadorSolicitacoesEstadoAguardandoInformacoesAdicionais() {
        return estadoAguardandoInformacoesAdicionais.size();
    }

    public int getIndicadorSolicitacoesAbertasPrioridadeBaixa() {
        return prioridadeBaixa.size() - ((CountPapelSolicitacao) estadoEncerrada).countPrioridadeBaixa;
    }

    public int getIndicadorSolicitacoesAbertasPrioridadeNormal() {
        return prioridadeNormal.size() - ((CountPapelSolicitacao) estadoEncerrada).countPrioridadeNormal;
    }

    public int getIndicadorSolicitacoesAbertasPrioridadeAlta() {
        return prioridadeAlta.size() - ((CountPapelSolicitacao) estadoEncerrada).countPrioridadeAlta;
    }

    /**
     * Classe para categorizar os itens da lista por papel (Solicitante, atendente ou gestor).
     *
     * @param <I> Tipo do item na lista.
     */
    private class CountPapelSolicitacao<I> extends ArrayList<I> {

        // Contagem por papel
        private int countSolicitante = 0;
        private int countAtendente = 0;
        private int countGestor = 0;

        // Contagem por estado
        private int countEstadoAguardandoAtendimento = 0;
        private int countEstadoEstadoEmAndamento = 0;
        private int countEstadoPausada = 0;
        private int countEstadoEncerramentoProposto = 0;
        private int countEstadoEncerramentoRejeitado = 0;
        private int countEstadoEncerrada = 0;
        private int countEstadoRespondida = 0;
        private int countEstadoAguardandoInformacoesAdicionais = 0;

        // Contagem por prioridade
        private int countPrioridadeBaixa = 0;
        private int countPrioridadeNormal = 0;
        private int countPrioridadeAlta = 0;

        @Override
        public boolean add(I i) {
            boolean resultado = super.add(i);
            Solicitacao solicitacao = (Solicitacao) i;

            if (resultado) {
                // Categorizar por papel
                if (usuario.getIdFuncionario() == solicitacao.getIdSolicitante()) {
                    // Solicitante
                    countSolicitante++;
                } else if (usuario.getIdFuncionario() == solicitacao.getIdAtendente()) {
                    // Atendente
                    countAtendente++;
                } else {
                    // Gestor
                    countGestor++;
                }

                // Categorizar por estado
                switch (solicitacao.getEstado().getIndice()) {
                    case FabricaEstado.AGUARDANDO_ATENDIMENTO:
                        countEstadoAguardandoAtendimento++;
                        break;
                    case FabricaEstado.EM_ANDAMENTO:
                        countEstadoEstadoEmAndamento++;
                        break;
                    case FabricaEstado.PAUSADA:
                        countEstadoPausada++;
                        break;
                    case FabricaEstado.ENCERRAMENTO_PROPOSTO:
                        countEstadoEncerramentoProposto++;
                        break;
                    case FabricaEstado.ENCERRAMENTO_REJEITADO:
                        countEstadoEncerramentoRejeitado++;
                        break;
                    case FabricaEstado.ENCERRADA:
                        countEstadoEncerrada++;
                        break;
                    case FabricaEstado.RESPONDIDA:
                        countEstadoRespondida++;
                        break;
                    case FabricaEstado.AGUARDANDO_INFORMACOES_ADICIONAIS:
                        countEstadoAguardandoInformacoesAdicionais++;
                        break;
                }

                // Categorizar por prioridade
                switch (solicitacao.getPrioridade().getIndice()) {
                    case FabricaPrioridade.BAIXA:
                        countPrioridadeBaixa++;
                        break;
                    case FabricaPrioridade.NORMAL:
                        countPrioridadeNormal++;
                        break;
                    case FabricaPrioridade.ALTA:
                        countPrioridadeAlta++;
                        break;
                }
            }

            return resultado;
        }
    }
}
