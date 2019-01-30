package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.view.DashboardView;
import br.pucpr.gss.client.view.MenuView;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

public class DashboardPresenter implements Presenter, DashboardView.Presenter {

    private HandlerManager eventBus;
    private DashboardView view;
    private MenuView.Presenter menuPresenter;

    public DashboardPresenter(HandlerManager eventBus, DashboardView view) {
        this.eventBus = eventBus;
        this.view = view;

        this.view.setPresenter(this);

        menuPresenter = new MenuPresenter(eventBus, this.view.getMenuView());
    }

    @Override
    public void go(HasWidgets container) {
        container.clear();
        container.add(view.asWidget());
    }
}
