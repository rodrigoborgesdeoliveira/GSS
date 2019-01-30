package br.pucpr.gss.client.view.uibinder;

import br.pucpr.gss.client.view.DashboardView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

public class DashboardViewImpl extends Composite implements DashboardView {

    interface DashboardViewImplUiBinder extends UiBinder<HTMLPanel, DashboardViewImpl> {
    }

    private static DashboardViewImplUiBinder uiBinder = GWT.create(DashboardViewImplUiBinder.class);

    private Presenter presenter;

    public DashboardViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}