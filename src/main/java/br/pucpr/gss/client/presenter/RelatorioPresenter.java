package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.view.RelatorioView;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

public class RelatorioPresenter implements Presenter, RelatorioView.Presenter {

    private HandlerManager eventBus;
    private RelatorioView view;

    public RelatorioPresenter(HandlerManager eventBus, RelatorioView view, String html) {
        this.eventBus = eventBus;
        this.view = view;

        this.view.setHtml(html);
    }

    @Override
    public void go(HasWidgets container) {
        container.clear();
        container.add(view.asWidget());
    }
}
