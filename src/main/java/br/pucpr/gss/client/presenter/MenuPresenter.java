package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.AppController;
import br.pucpr.gss.client.view.MenuView;
import com.google.gwt.event.shared.HandlerManager;

public class MenuPresenter implements MenuView.Presenter {

    private HandlerManager eventBus;
    private MenuView view;

    public MenuPresenter(HandlerManager eventBus, MenuView view) {
        this.eventBus = eventBus;
        this.view = view;

        this.view.setPresenter(this);
    }

    @Override
    public String getOnDashboardClickedToken() {
        return AppController.DASHBOARD;
    }

    @Override
    public String getOnCadastrarSolicitacaoClickedToken() {
        return null;
    }

    @Override
    public String getOnConsultarSolicitacoesClickedToken() {
        return null;
    }

    @Override
    public String getOnGerarIndicadoresClickedToken() {
        return null;
    }

    @Override
    public String getOnGerenciarCadastrosClickedToken() {
        return null;
    }
}
