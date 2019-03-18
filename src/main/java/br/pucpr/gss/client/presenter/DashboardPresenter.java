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
                                relatorio.getIndicadorSolicitacoesEncerradas(),
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

//                        view.setIndicadoresEstadoSolicitacoesSolicitante(
//                                ((CountPapelSolicitacao<Solicitacao>) estadoAguardandoAtendimento).getCountSolicitante(),
//                                ((CountPapelSolicitacao<Solicitacao>) estadoEmAndamento).getCountSolicitante(),
//                                ((CountPapelSolicitacao<Solicitacao>) estadoPausada).getCountSolicitante(),
//                                ((CountPapelSolicitacao<Solicitacao>) estadoEncerramentoProposto).getCountSolicitante(),
//                                ((CountPapelSolicitacao<Solicitacao>) estadoEncerramentoRejeitado).getCountSolicitante(),
//                                ((CountPapelSolicitacao<Solicitacao>) estadoRespondida).getCountSolicitante(),
//                                ((CountPapelSolicitacao<Solicitacao>) estadoAguardandoInformacoesAdicionais).getCountSolicitante(),
//                                ((CountPapelSolicitacao<Solicitacao>) estadoEncerrada).getCountSolicitante());
//
//                        view.setIndicadoresPrioridadeSolicitante(
//                                ((CountPapelSolicitacao<Solicitacao>) prioridadeBaixa).getCountSolicitante(),
//                                ((CountPapelSolicitacao<Solicitacao>) prioridadeNormal).getCountSolicitante(),
//                                ((CountPapelSolicitacao<Solicitacao>) prioridadeAlta).getCountSolicitante());
//
//                        view.setIndicadoresEstadoSolicitacoesAtendente(
//                                ((CountPapelSolicitacao<Solicitacao>) estadoAguardandoAtendimento).getCountAtendente(),
//                                ((CountPapelSolicitacao<Solicitacao>) estadoEmAndamento).getCountAtendente(),
//                                ((CountPapelSolicitacao<Solicitacao>) estadoPausada).getCountAtendente(),
//                                ((CountPapelSolicitacao<Solicitacao>) estadoEncerramentoProposto).getCountAtendente(),
//                                ((CountPapelSolicitacao<Solicitacao>) estadoEncerramentoRejeitado).getCountAtendente(),
//                                ((CountPapelSolicitacao<Solicitacao>) estadoRespondida).getCountAtendente(),
//                                ((CountPapelSolicitacao<Solicitacao>) estadoAguardandoInformacoesAdicionais).getCountAtendente(),
//                                ((CountPapelSolicitacao<Solicitacao>) estadoEncerrada).getCountAtendente());
//
//                        view.setIndicadoresPrioridadeAtendente(
//                                ((CountPapelSolicitacao<Solicitacao>) prioridadeBaixa).getCountAtendente(),
//                                ((CountPapelSolicitacao<Solicitacao>) prioridadeNormal).getCountAtendente(),
//                                ((CountPapelSolicitacao<Solicitacao>) prioridadeAlta).getCountAtendente());
//
//                        view.setIndicadoresEstadoSolicitacoesGestor(
//                                ((CountPapelSolicitacao<Solicitacao>) estadoAguardandoAtendimento).getCountGestor(),
//                                ((CountPapelSolicitacao<Solicitacao>) estadoEmAndamento).getCountGestor(),
//                                ((CountPapelSolicitacao<Solicitacao>) estadoPausada).getCountGestor(),
//                                ((CountPapelSolicitacao<Solicitacao>) estadoEncerramentoProposto).getCountGestor(),
//                                ((CountPapelSolicitacao<Solicitacao>) estadoEncerramentoRejeitado).getCountGestor(),
//                                ((CountPapelSolicitacao<Solicitacao>) estadoRespondida).getCountGestor(),
//                                ((CountPapelSolicitacao<Solicitacao>) estadoAguardandoInformacoesAdicionais).getCountGestor(),
//                                ((CountPapelSolicitacao<Solicitacao>) estadoEncerrada).getCountGestor());
//
//                        view.setIndicadoresPrioridadeGestor(
//                                ((CountPapelSolicitacao<Solicitacao>) prioridadeBaixa).getCountGestor(),
//                                ((CountPapelSolicitacao<Solicitacao>) prioridadeNormal).getCountGestor(),
//                                ((CountPapelSolicitacao<Solicitacao>) prioridadeAlta).getCountGestor());
                    }
                });
    }
}
