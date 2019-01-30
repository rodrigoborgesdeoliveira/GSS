package br.pucpr.gss.client.view.uibinder;

import br.pucpr.gss.client.view.DashboardView;
import br.pucpr.gss.client.view.MenuView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class DashboardViewImpl extends Composite implements DashboardView {

    interface DashboardViewImplUiBinder extends UiBinder<Widget, DashboardViewImpl> {
    }

    private static DashboardViewImplUiBinder uiBinder = GWT.create(DashboardViewImplUiBinder.class);

    private Presenter presenter;

    @UiField
    MenuViewImpl menu;

    public DashboardViewImpl() {
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