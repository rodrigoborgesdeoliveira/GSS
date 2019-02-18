package br.pucpr.gss.client.view.uibinder;

import br.pucpr.gss.client.view.MenuView;
import br.pucpr.gss.client.view.RegistroSolucaoView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class RegistroSolucaoViewImpl extends Composite implements RegistroSolucaoView {
    interface RegistroSolucaoUiBinder extends UiBinder<Widget, RegistroSolucaoViewImpl> {
    }

    private static RegistroSolucaoUiBinder uiBinder = GWT.create(RegistroSolucaoUiBinder.class);

    private Presenter presenter;
    @UiField
    MenuView menu;

    public RegistroSolucaoViewImpl() {
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
}