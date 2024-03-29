package br.pucpr.gss.client.view.uibinder;

import br.pucpr.gss.client.view.MenuView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialRow;

public class MenuViewImpl extends Composite implements MenuView {

    interface MenuViewImplUiBinder extends UiBinder<Widget, MenuViewImpl> {
    }

    private static MenuViewImplUiBinder uiBinder = GWT.create(MenuViewImplUiBinder.class);

    private Presenter presenter;

    @UiField
    MaterialLink dashboard, cadastrarSolicitacao, consultarSolicitacoes, gerarIndicadores, gerenciarCadastros, sair;
    @UiField
    MaterialRow grupoCadastros;

    public MenuViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;

        if (this.presenter != null) {
            dashboard.setTargetHistoryToken(this.presenter.getOnDashboardClickedToken());
            cadastrarSolicitacao.setTargetHistoryToken(this.presenter.getOnCadastrarSolicitacaoClickedToken());
            consultarSolicitacoes.setTargetHistoryToken(this.presenter.getOnConsultarSolicitacoesClickedToken());
            gerarIndicadores.setTargetHistoryToken(this.presenter.getOnGerarIndicadoresClickedToken());
            gerenciarCadastros.setTargetHistoryToken(this.presenter.getOnGerenciarCadastrosClickedToken());
            sair.setTargetHistoryToken(this.presenter.getOnSairClickedToken());
        }
    }

    @UiHandler("sair")
    void onClickSair(ClickEvent event) {
        if (presenter != null) {
            presenter.onSairClicked();
        }
    }

    @Override
    public void setUIAdmin() {
        grupoCadastros.setVisible(true);
    }
}