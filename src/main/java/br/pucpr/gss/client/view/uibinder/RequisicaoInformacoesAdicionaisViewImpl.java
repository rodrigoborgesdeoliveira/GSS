package br.pucpr.gss.client.view.uibinder;

import br.pucpr.gss.client.view.MenuView;
import br.pucpr.gss.client.view.RequisicaoInformacoesAdicionaisView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class RequisicaoInformacoesAdicionaisViewImpl extends Composite implements RequisicaoInformacoesAdicionaisView {
    interface RequisicaoInformacoesAdicionaisUiBinder extends UiBinder<Widget, RequisicaoInformacoesAdicionaisViewImpl> {
    }

    private static RequisicaoInformacoesAdicionaisUiBinder uiBinder = GWT.create(RequisicaoInformacoesAdicionaisUiBinder.class);

    public RequisicaoInformacoesAdicionaisViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    private Presenter presenter;

    @UiField
    MenuView menu;

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public MenuView getMenuView() {
        return menu;
    }
}