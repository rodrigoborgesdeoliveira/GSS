package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.view.MenuView;
import com.google.gwt.event.shared.HandlerManager;

public class MenuPresenter implements MenuView.Presenter {

    private HandlerManager eventBus;
    private MenuView view;

    public MenuPresenter(HandlerManager eventBus, MenuView view) {
        this.eventBus = eventBus;
        this.view = view;

        this.view.setPresenter(this);
    }
}
