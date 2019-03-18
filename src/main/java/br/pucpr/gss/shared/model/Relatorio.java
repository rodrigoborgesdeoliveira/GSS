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
    private ArrayList<Solicitacao> papelSolicitante = new CountPapelSolicitacao<>();
    private ArrayList<Solicitacao> papelAtendente = new CountPapelSolicitacao<>();
    private ArrayList<Solicitacao> papelGestor = new CountPapelSolicitacao<>();

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

    public int getIndicadorSolicitanteEstadoAguardandoAtendimento() {
        return ((CountPapelSolicitacao) estadoAguardandoAtendimento).countSolicitante;
    }

    public int getIndicadorSolicitanteEstadoEmAndamento() {
        return ((CountPapelSolicitacao) estadoEmAndamento).countSolicitante;
    }

    public int getIndicadorSolicitanteEstadoPausada() {
        return ((CountPapelSolicitacao) estadoPausada).countSolicitante;
    }

    public int getIndicadorSolicitanteEstadoEncerramentoProposto() {
        return ((CountPapelSolicitacao) estadoEncerramentoProposto).countSolicitante;
    }

    public int getIndicadorSolicitanteEstadoEncerramentoRejeitado() {
        return ((CountPapelSolicitacao) estadoEncerramentoRejeitado).countSolicitante;
    }

    public int getIndicadorSolicitanteEstadoEncerrada() {
        return ((CountPapelSolicitacao) estadoEncerrada).countSolicitante;
    }

    public int getIndicadorSolicitanteEstadoRespondida() {
        return ((CountPapelSolicitacao) estadoRespondida).countSolicitante;
    }

    public int getIndicadorSolicitanteEstadoAguardandoInformacoesAdicionais() {
        return ((CountPapelSolicitacao) estadoAguardandoInformacoesAdicionais).countSolicitante;
    }

    public int getIndicadorSolicitantePrioridadeBaixa() {
        return ((CountPapelSolicitacao) prioridadeBaixa).countSolicitante;
    }

    public int getIndicadorSolicitantePrioridadeNormal() {
        return ((CountPapelSolicitacao) prioridadeNormal).countSolicitante;
    }

    public int getIndicadorSolicitantePrioridadeAlta() {
        return ((CountPapelSolicitacao) prioridadeAlta).countSolicitante;
    }

    public int getIndicadorAtendenteEstadoAguardandoAtendimento() {
        return ((CountPapelSolicitacao) estadoAguardandoAtendimento).countAtendente;
    }

    public int getIndicadorAtendenteEstadoEmAndamento() {
        return ((CountPapelSolicitacao) estadoEmAndamento).countAtendente;
    }

    public int getIndicadorAtendenteEstadoPausada() {
        return ((CountPapelSolicitacao) estadoPausada).countAtendente;
    }

    public int getIndicadorAtendenteEstadoEncerramentoProposto() {
        return ((CountPapelSolicitacao) estadoEncerramentoProposto).countAtendente;
    }

    public int getIndicadorAtendenteEstadoEncerramentoRejeitado() {
        return ((CountPapelSolicitacao) estadoEncerramentoRejeitado).countAtendente;
    }

    public int getIndicadorAtendenteEstadoEncerrada() {
        return ((CountPapelSolicitacao) estadoEncerrada).countAtendente;
    }

    public int getIndicadorAtendenteEstadoRespondida() {
        return ((CountPapelSolicitacao) estadoRespondida).countAtendente;
    }

    public int getIndicadorAtendenteEstadoAguardandoInformacoesAdicionais() {
        return ((CountPapelSolicitacao) estadoAguardandoInformacoesAdicionais).countAtendente;
    }

    public int getIndicadorAtendentePrioridadeBaixa() {
        return ((CountPapelSolicitacao) prioridadeBaixa).countAtendente;
    }

    public int getIndicadorAtendentePrioridadeNormal() {
        return ((CountPapelSolicitacao) prioridadeNormal).countAtendente;
    }

    public int getIndicadorAtendentePrioridadeAlta() {
        return ((CountPapelSolicitacao) prioridadeAlta).countAtendente;
    }

    public int getIndicadorGestorEstadoAguardandoAtendimento() {
        return ((CountPapelSolicitacao) estadoAguardandoAtendimento).countGestor;
    }

    public int getIndicadorGestorEstadoEmAndamento() {
        return ((CountPapelSolicitacao) estadoEmAndamento).countGestor;
    }

    public int getIndicadorGestorEstadoPausada() {
        return ((CountPapelSolicitacao) estadoPausada).countGestor;
    }

    public int getIndicadorGestorEstadoEncerramentoProposto() {
        return ((CountPapelSolicitacao) estadoEncerramentoProposto).countGestor;
    }

    public int getIndicadorGestorEstadoEncerramentoRejeitado() {
        return ((CountPapelSolicitacao) estadoEncerramentoRejeitado).countGestor;
    }

    public int getIndicadorGestorEstadoEncerrada() {
        return ((CountPapelSolicitacao) estadoEncerrada).countGestor;
    }

    public int getIndicadorGestorEstadoRespondida() {
        return ((CountPapelSolicitacao) estadoRespondida).countGestor;
    }

    public int getIndicadorGestorEstadoAguardandoInformacoesAdicionais() {
        return ((CountPapelSolicitacao) estadoAguardandoInformacoesAdicionais).countGestor;
    }

    public int getIndicadorGestorPrioridadeBaixa() {
        return ((CountPapelSolicitacao) prioridadeBaixa).countGestor;
    }

    public int getIndicadorGestorPrioridadeNormal() {
        return ((CountPapelSolicitacao) prioridadeNormal).countGestor;
    }

    public int getIndicadorGestorPrioridadeAlta() {
        return ((CountPapelSolicitacao) prioridadeAlta).countGestor;
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
