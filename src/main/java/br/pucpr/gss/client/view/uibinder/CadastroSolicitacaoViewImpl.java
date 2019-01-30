package br.pucpr.gss.client.view.uibinder;

import br.pucpr.gss.client.view.CadastroSolicitacaoView;
import br.pucpr.gss.client.view.MenuView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CadastroSolicitacaoViewImpl extends Composite implements CadastroSolicitacaoView {
    interface CadastroSolicitacaoViewImplUiBinder extends UiBinder<Widget, CadastroSolicitacaoViewImpl> {
    }

    private static CadastroSolicitacaoViewImplUiBinder uiBinder = GWT.create(CadastroSolicitacaoViewImplUiBinder.class);

    public CadastroSolicitacaoViewImpl() {
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