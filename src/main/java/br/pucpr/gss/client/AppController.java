package br.pucpr.gss.client;

import br.pucpr.gss.client.presenter.LoginPresenter;
import br.pucpr.gss.client.presenter.Presenter;
import br.pucpr.gss.client.view.uibinder.LoginViewImpl;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

public class AppController implements Presenter, ValueChangeHandler<String> {
    private final HandlerManager eventBus;
    private HasWidgets container;
    private LoginViewImpl loginView;

    public AppController(HandlerManager eventBus) {
        this.eventBus = eventBus;
        bind();
    }

    /**
     * Controla os eventos recebidos pelo event bus.
     */
    private void bind() {
        History.addValueChangeHandler(this);
    }

    @Override
    public void go(final HasWidgets container) {
        this.container = container;

        if (History.getToken().isEmpty()) {
            History.newItem("login");
        } else {
            History.fireCurrentHistoryState();
        }
    }

    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
        String token = event.getValue();

        if (token != null) {
            switch (token) {
                case "login":
                    GWT.runAsync(new RunAsyncCallback() {
                        @Override
                        public void onFailure(Throwable reason) {

                        }

                        @Override
                        public void onSuccess() {
                            if (loginView == null) {
                                loginView = new LoginViewImpl();
                            }

                            new LoginPresenter(eventBus, loginView).go(container);
                        }
                    });
                    break;
            }
        }
    }
}
