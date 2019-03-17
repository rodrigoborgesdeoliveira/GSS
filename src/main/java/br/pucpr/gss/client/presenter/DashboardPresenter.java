package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.service.SolicitacaoService;
import br.pucpr.gss.client.view.DashboardView;
import br.pucpr.gss.shared.fabrica.FabricaEstado;
import br.pucpr.gss.shared.fabrica.FabricaPrioridade;
import br.pucpr.gss.shared.model.Solicitacao;
import br.pucpr.gss.shared.model.Usuario;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import java.util.ArrayList;

public class DashboardPresenter implements Presenter, DashboardView.Presenter {

    private HandlerManager eventBus;
    private DashboardView view;
    private Usuario usuario;

    public DashboardPresenter(HandlerManager eventBus, DashboardView view, Usuario usuario) {
        this.eventBus = eventBus;
        this.view = view;
        this.usuario = usuario;

        this.view.setPresenter(this);

        new MenuPresenter(eventBus, this.view.getMenuView(), usuario.isAdmin());

        fetchSolicitacoes();
    }

    @Override
    public void go(HasWidgets container) {
        container.clear();
        container.add(view.asWidget());
    }

    private void fetchSolicitacoes() {
        SolicitacaoService.RPC.getInstance().consultarSolicitacoes(usuario.getIdFuncionario(),
                new AsyncCallback<ArrayList<Solicitacao>>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        GWT.log("Erro ao carregar lista de solicitações", caught);

                        Window.alert("Não foi possível carregar solicitações, tente novamente.");
                    }

                    @Override
                    public void onSuccess(ArrayList<Solicitacao> result) {
                        int total = result.size();

                        // Por estado
                        ArrayList<Solicitacao> estadoAguardandoAtendimento = new CountPapelSolicitacao<>();
                        ArrayList<Solicitacao> estadoEmAndamento = new CountPapelSolicitacao<>();
                        ArrayList<Solicitacao> estadoPausada = new CountPapelSolicitacao<>();
                        ArrayList<Solicitacao> estadoEncerramentoProposto = new CountPapelSolicitacao<>();
                        ArrayList<Solicitacao> estadoEncerramentoRejeitado = new CountPapelSolicitacao<>();
                        ArrayList<Solicitacao> estadoEncerrada = new CountPapelSolicitacao<>();
                        ArrayList<Solicitacao> estadoRespondida = new CountPapelSolicitacao<>();
                        ArrayList<Solicitacao> estadoAguardandoInformacoesAdicionais = new CountPapelSolicitacao<>();

                        // Por prioridade
                        ArrayList<Solicitacao> prioridadeBaixa = new CountPapelSolicitacao<>();
                        ArrayList<Solicitacao> prioridadeNormal = new CountPapelSolicitacao<>();
                        ArrayList<Solicitacao> prioridadeAlta = new CountPapelSolicitacao<>();

                        // Por papel
                        ArrayList<Solicitacao> papelSolicitante = new ArrayList<>();
                        ArrayList<Solicitacao> papelAtendente = new ArrayList<>();
                        ArrayList<Solicitacao> papelGestor = new ArrayList<>();

                        result.forEach(solicitacao -> {
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

                        view.setIndicadores(total, total - estadoEncerrada.size(),
                                estadoEncerrada.size(), prioridadeBaixa.size(), prioridadeNormal.size(), prioridadeAlta.size(),
                                papelSolicitante.size(), papelAtendente.size(), papelGestor.size());

                        view.setIndicadoresEstadoSolicitacoesAbertas(estadoAguardandoAtendimento.size(),
                                estadoEmAndamento.size(), estadoPausada.size(), estadoEncerramentoProposto.size(),
                                estadoEncerramentoRejeitado.size(), estadoRespondida.size(), estadoAguardandoInformacoesAdicionais.size());

                        view.setIndicadoresPrioridadeSolicitacoesAbertas(prioridadeBaixa.size(), prioridadeNormal.size(),
                                prioridadeAlta.size());

                        view.setIndicadoresEstadoSolicitacoesSolicitante(
                                ((CountPapelSolicitacao<Solicitacao>) estadoAguardandoAtendimento).getCountSolicitante(),
                                ((CountPapelSolicitacao<Solicitacao>) estadoEmAndamento).getCountSolicitante(),
                                ((CountPapelSolicitacao<Solicitacao>) estadoPausada).getCountSolicitante(),
                                ((CountPapelSolicitacao<Solicitacao>) estadoEncerramentoProposto).getCountSolicitante(),
                                ((CountPapelSolicitacao<Solicitacao>) estadoEncerramentoRejeitado).getCountSolicitante(),
                                ((CountPapelSolicitacao<Solicitacao>) estadoRespondida).getCountSolicitante(),
                                ((CountPapelSolicitacao<Solicitacao>) estadoAguardandoInformacoesAdicionais).getCountSolicitante(),
                                ((CountPapelSolicitacao<Solicitacao>) estadoEncerrada).getCountSolicitante());

                        view.setIndicadoresPrioridadeSolicitante(
                                ((CountPapelSolicitacao<Solicitacao>) prioridadeBaixa).getCountSolicitante(),
                                ((CountPapelSolicitacao<Solicitacao>) prioridadeNormal).getCountSolicitante(),
                                ((CountPapelSolicitacao<Solicitacao>) prioridadeAlta).getCountSolicitante());

                        view.setIndicadoresEstadoSolicitacoesAtendente(
                                ((CountPapelSolicitacao<Solicitacao>) estadoAguardandoAtendimento).getCountAtendente(),
                                ((CountPapelSolicitacao<Solicitacao>) estadoEmAndamento).getCountAtendente(),
                                ((CountPapelSolicitacao<Solicitacao>) estadoPausada).getCountAtendente(),
                                ((CountPapelSolicitacao<Solicitacao>) estadoEncerramentoProposto).getCountAtendente(),
                                ((CountPapelSolicitacao<Solicitacao>) estadoEncerramentoRejeitado).getCountAtendente(),
                                ((CountPapelSolicitacao<Solicitacao>) estadoRespondida).getCountAtendente(),
                                ((CountPapelSolicitacao<Solicitacao>) estadoAguardandoInformacoesAdicionais).getCountAtendente(),
                                ((CountPapelSolicitacao<Solicitacao>) estadoEncerrada).getCountAtendente());

                        view.setIndicadoresPrioridadeAtendente(
                                ((CountPapelSolicitacao<Solicitacao>) prioridadeBaixa).getCountAtendente(),
                                ((CountPapelSolicitacao<Solicitacao>) prioridadeNormal).getCountAtendente(),
                                ((CountPapelSolicitacao<Solicitacao>) prioridadeAlta).getCountAtendente());

                        view.setIndicadoresEstadoSolicitacoesGestor(
                                ((CountPapelSolicitacao<Solicitacao>) estadoAguardandoAtendimento).getCountGestor(),
                                ((CountPapelSolicitacao<Solicitacao>) estadoEmAndamento).getCountGestor(),
                                ((CountPapelSolicitacao<Solicitacao>) estadoPausada).getCountGestor(),
                                ((CountPapelSolicitacao<Solicitacao>) estadoEncerramentoProposto).getCountGestor(),
                                ((CountPapelSolicitacao<Solicitacao>) estadoEncerramentoRejeitado).getCountGestor(),
                                ((CountPapelSolicitacao<Solicitacao>) estadoRespondida).getCountGestor(),
                                ((CountPapelSolicitacao<Solicitacao>) estadoAguardandoInformacoesAdicionais).getCountGestor(),
                                ((CountPapelSolicitacao<Solicitacao>) estadoEncerrada).getCountGestor());

                        view.setIndicadoresPrioridadeGestor(
                                ((CountPapelSolicitacao<Solicitacao>) prioridadeBaixa).getCountGestor(),
                                ((CountPapelSolicitacao<Solicitacao>) prioridadeNormal).getCountGestor(),
                                ((CountPapelSolicitacao<Solicitacao>) prioridadeAlta).getCountGestor());
                    }
                });
    }

    /**
     * Classe para categorizar os itens da lista por papel (Solicitante, atendente ou gestor).
     *
     * @param <I> Tipo do item na lista.
     */
    private class CountPapelSolicitacao<I> extends ArrayList<I> {
        private int countSolicitante = 0;
        private int countAtendente = 0;
        private int countGestor = 0;

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
            }

            return resultado;
        }

        public int getCountSolicitante() {
            return countSolicitante;
        }

        public int getCountAtendente() {
            return countAtendente;
        }

        public int getCountGestor() {
            return countGestor;
        }
    }
}
