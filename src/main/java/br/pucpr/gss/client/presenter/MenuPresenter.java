package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.AppController;
import br.pucpr.gss.client.event.LogoutEvent;
import br.pucpr.gss.client.view.MenuView;
import com.google.gwt.event.shared.HandlerManager;

public class MenuPresenter implements MenuView.Presenter {

    private HandlerManager eventBus;
    private MenuView view;

    public MenuPresenter(HandlerManager eventBus, MenuView view, boolean isAdmin) {
        this.eventBus = eventBus;
        this.view = view;

        this.view.setPresenter(this);

        if (isAdmin) {
            this.view.setUIAdmin();
        }
    }

    @Override
    public String getOnDashboardClickedToken() {
        return AppController.DASHBOARD_TOKEN;
    }

    @Override
    public String getOnCadastrarSolicitacaoClickedToken() {
        return AppController.CADASTRAR_SOLICITACAO_TOKEN;
    }

    @Override
    public String getOnConsultarSolicitacoesClickedToken() {
        return AppController.CONSULTAR_SOLICITACOES_TOKEN;
    }

    @Override
    public String getOnGerarIndicadoresClickedToken() {
        return AppController.GERAR_INDICADORES_TOKEN;
    }

    @Override
    public String getOnGerenciarCadastrosClickedToken() {
        return AppController.GERENCIAR_CADASTROS_TOKEN;
    }

    @Override
    public String getOnSairClickedToken() {
        return AppController.LOGIN_TOKEN;
    }

    @Override
    public void onSairClicked() {
        eventBus.fireEvent(new LogoutEvent());
    }
}
