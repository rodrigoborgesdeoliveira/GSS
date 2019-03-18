package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.service.SolicitacaoService;
import br.pucpr.gss.client.view.DashboardView;
import br.pucpr.gss.shared.model.Relatorio;
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
                        Relatorio relatorio = new Relatorio(usuario, result);

                        view.setIndicadores(relatorio.getIndicadorTotalSolicitacoes(),
                                relatorio.getIndicadorSolicitacoesAbertas(),
                                relatorio.getIndicadorSolicitacoesEstadoEncerrada(),
                                relatorio.getIndicadorSolicitacoesPrioridadeBaixa(),
                                relatorio.getIndicadorSolicitacoesPrioridadeNormal(),
                                relatorio.getIndicadorSolicitacoesPrioridadeAlta(),
                                relatorio.getIndicadorSolicitacoesPapelSolicitante(),
                                relatorio.getIndicadorSolicitacoesPapelAtendente(),
                                relatorio.getIndicadorSolicitacoesPapelGestor());

                        view.setIndicadoresEstadoSolicitacoesAbertas(
                                relatorio.getIndicadorSolicitacoesEstadoAguardandoAtendimento(),
                                relatorio.getIndicadorSolicitacoesEstadoEmAndamento(),
                                relatorio.getIndicadorSolicitacoesEstadoPausada(),
                                relatorio.getIndicadorSolicitacoesEstadoEncerramentoProposto(),
                                relatorio.getIndicadorSolicitacoesEstadoEncerramentoRejeitado(),
                                relatorio.getIndicadorSolicitacoesEstadoRespondida(),
                                relatorio.getIndicadorSolicitacoesEstadoAguardandoInformacoesAdicionais());

                        view.setIndicadoresPrioridadeSolicitacoesAbertas(
                                relatorio.getIndicadorSolicitacoesAbertasPrioridadeBaixa(),
                                relatorio.getIndicadorSolicitacoesAbertasPrioridadeNormal(),
                                relatorio.getIndicadorSolicitacoesAbertasPrioridadeAlta());

                        view.setIndicadoresEstadoSolicitacoesSolicitante(
                                relatorio.getIndicadorSolicitanteEstadoAguardandoAtendimento(),
                                relatorio.getIndicadorSolicitanteEstadoEmAndamento(),
                                relatorio.getIndicadorSolicitanteEstadoPausada(),
                                relatorio.getIndicadorSolicitanteEstadoEncerramentoProposto(),
                                relatorio.getIndicadorSolicitanteEstadoEncerramentoRejeitado(),
                                relatorio.getIndicadorSolicitanteEstadoRespondida(),
                                relatorio.getIndicadorSolicitanteEstadoAguardandoInformacoesAdicionais(),
                                relatorio.getIndicadorSolicitanteEstadoEncerrada());

                        view.setIndicadoresPrioridadeSolicitante(
                                relatorio.getIndicadorSolicitantePrioridadeBaixa(),
                                relatorio.getIndicadorSolicitantePrioridadeNormal(),
                                relatorio.getIndicadorSolicitantePrioridadeAlta());

                        view.setIndicadoresEstadoSolicitacoesAtendente(
                                relatorio.getIndicadorAtendenteEstadoAguardandoAtendimento(),
                                relatorio.getIndicadorAtendenteEstadoEmAndamento(),
                                relatorio.getIndicadorAtendenteEstadoPausada(),
                                relatorio.getIndicadorAtendenteEstadoEncerramentoProposto(),
                                relatorio.getIndicadorAtendenteEstadoEncerramentoRejeitado(),
                                relatorio.getIndicadorAtendenteEstadoRespondida(),
                                relatorio.getIndicadorAtendenteEstadoAguardandoInformacoesAdicionais(),
                                relatorio.getIndicadorAtendenteEstadoEncerrada());

                        view.setIndicadoresPrioridadeAtendente(
                                relatorio.getIndicadorAtendentePrioridadeBaixa(),
                                relatorio.getIndicadorAtendentePrioridadeNormal(),
                                relatorio.getIndicadorAtendentePrioridadeAlta());

                        view.setIndicadoresEstadoSolicitacoesGestor(
                                relatorio.getIndicadorGestorEstadoAguardandoAtendimento(),
                                relatorio.getIndicadorGestorEstadoEmAndamento(),
                                relatorio.getIndicadorGestorEstadoPausada(),
                                relatorio.getIndicadorGestorEstadoEncerramentoProposto(),
                                relatorio.getIndicadorGestorEstadoEncerramentoRejeitado(),
                                relatorio.getIndicadorGestorEstadoRespondida(),
                                relatorio.getIndicadorGestorEstadoAguardandoInformacoesAdicionais(),
                                relatorio.getIndicadorGestorEstadoEncerrada());

                        view.setIndicadoresPrioridadeGestor(
                                relatorio.getIndicadorGestorPrioridadeBaixa(),
                                relatorio.getIndicadorGestorPrioridadeNormal(),
                                relatorio.getIndicadorGestorPrioridadeAlta());
                    }
                });
    }
}
