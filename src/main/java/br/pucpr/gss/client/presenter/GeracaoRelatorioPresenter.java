package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.service.SolicitacaoService;
import br.pucpr.gss.client.view.GeracaoRelatorioView;
import br.pucpr.gss.shared.model.FiltroSolicitacao;
import br.pucpr.gss.shared.model.Solicitacao;
import br.pucpr.gss.shared.model.Usuario;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import java.util.ArrayList;
import java.util.Date;

public class GeracaoRelatorioPresenter implements Presenter, GeracaoRelatorioView.Presenter {

    private HandlerManager eventBus;
    private GeracaoRelatorioView view;
    private Usuario usuario;
    private ArrayList<Solicitacao> listaSolicitacoes;
    private FiltroSolicitacao filtroSolicitacao;

    public GeracaoRelatorioPresenter(HandlerManager eventBus, GeracaoRelatorioView view, Usuario usuario) {
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

    @Override
    public String gerarRelatorio(Date filtroDataInicial, Date filtroDataFinal, boolean showSolicitante,
                                 boolean showAtendente, boolean showGestor) {
        return null;
    }

    private void fetchSolicitacoes() {
        SolicitacaoService.RPC.getInstance().consultarSolicitacoes(usuario.getIdFuncionario(),
                new AsyncCallback<ArrayList<Solicitacao>>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        GWT.log("Erro ao carregar lista de solicitações", caught);

                        Window.alert("Não foi possível carregar lista de solicitações, tente novamente.");
                    }

                    @Override
                    public void onSuccess(ArrayList<Solicitacao> result) {
                        listaSolicitacoes = result;
                        filtroSolicitacao = new FiltroSolicitacao(usuario, listaSolicitacoes);
                    }
                });
    }
}
