package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.view.DashboardView;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

public class DashboardPresenter implements Presenter, DashboardView.Presenter {

    private HandlerManager eventBus;
    private DashboardView view;

    public DashboardPresenter(HandlerManager eventBus, DashboardView view) {
        this.eventBus = eventBus;
        this.view = view;

        this.view.setPresenter(this);
    }

    @Override
    public void go(HasWidgets container) {
        container.clear();
        container.add(view.asWidget());
    }
}
