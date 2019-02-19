package br.pucpr.gss.client.view.uibinder;

import br.pucpr.gss.client.view.MenuView;
import br.pucpr.gss.client.view.VisualizacaoSolucaoView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class VisualizacaoSolucaoViewImpl extends Composite implements VisualizacaoSolucaoView {
    interface VisualizacaoSolucaoViewImplUiBinder extends UiBinder<Widget, VisualizacaoSolucaoViewImpl> {
    }

    private static VisualizacaoSolucaoViewImplUiBinder uiBinder = GWT.create(VisualizacaoSolucaoViewImplUiBinder.class);

    private Presenter presenter;

    @UiField
    MenuView menu;
    @UiField
    TextArea textAreaDescricao;

    public VisualizacaoSolucaoViewImpl() {
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
    public void setDescricaoSolucao(String solucao) {
        textAreaDescricao.setText(solucao);
    }

    @UiHandler("buttonCancelar")
    void onClickCancelar(ClickEvent event) {
        if (presenter != null) {
            presenter.onCancelarButtonClicked();
        }
    }

    @UiHandler("buttonRejeitar")
    void onClickRejeitar(ClickEvent event) {
        if (presenter != null) {
            presenter.onRejeitarButtonClicked();
        }
    }

    @UiHandler("buttonAceitar")
    void onClickAceitar(ClickEvent event) {
        if (presenter != null) {
            presenter.onAceitarButtonClicked();
        }
    }
}