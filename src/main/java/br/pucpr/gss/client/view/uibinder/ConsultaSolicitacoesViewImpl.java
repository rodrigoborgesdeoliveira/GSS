package br.pucpr.gss.client.view.uibinder;

import br.pucpr.gss.client.view.ConsultaSolicitacoesView;
import br.pucpr.gss.client.view.MenuView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.ui.MaterialListBox;

import java.util.ArrayList;

public class ConsultaSolicitacoesViewImpl extends Composite implements ConsultaSolicitacoesView {
    interface ConsultaSolicitacaoUiBinder extends UiBinder<Widget, ConsultaSolicitacoesViewImpl> {
    }

    private static ConsultaSolicitacaoUiBinder uiBinder = GWT.create(ConsultaSolicitacaoUiBinder.class);

    private Presenter presenter;

    @UiField
    MenuView menu;
    @UiField
    MaterialListBox listaSolicitacoes;

    public ConsultaSolicitacoesViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public MenuView getMenuView() {
        return menu;
    }

    @Override
    public void carregarListaSolicitacoes(ArrayList<String> solicitacoes) {
        for (String solicitacao : solicitacoes) {
            listaSolicitacoes.addItem(solicitacao);
        }
    }

    @UiHandler("cancelar")
    void onClickCancelar(ClickEvent event) {
        if (presenter != null) {
            presenter.onCancelarButtonClicked();
        }
    }

    @UiHandler("consultar")
    void onClickConsultar(ClickEvent event) {
        if (presenter != null) {
            presenter.onConsultarButtonClicked(listaSolicitacoes.getSelectedIndex());
        }
    }
}