package br.pucpr.gss.client;

import br.pucpr.gss.client.event.CadastrarEvent;
import br.pucpr.gss.client.event.CadastrarEventHandler;
import br.pucpr.gss.client.event.LoginEvent;
import br.pucpr.gss.client.event.LoginEventHandler;
import br.pucpr.gss.client.presenter.CadastroPresenter;
import br.pucpr.gss.client.presenter.LoginPresenter;
import br.pucpr.gss.client.presenter.Presenter;
import br.pucpr.gss.client.view.uibinder.CadastroViewImpl;
import br.pucpr.gss.client.view.uibinder.LoginViewImpl;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

public class AppController implements Presenter, ValueChangeHandler<String> {

    // Tokens do histórico
    public static final String LOGIN_TOKEN = "login";
    public static final String CADASTRAR_TOKEN = "cadastrar";

    private final HandlerManager eventBus;
    private HasWidgets container;

    private LoginViewImpl loginView;
    private CadastroViewImpl cadastroView;

    public AppController(HandlerManager eventBus) {
        this.eventBus = eventBus;
        bind();
    }

    /**
     * Registra handlers para os eventos recebidos pelo event bus.
     */
    private void bind() {
        History.addValueChangeHandler(this);

        eventBus.addHandler(LoginEvent.TYPE, new LoginEventHandler() {
            @Override
            public void onLogin(LoginEvent event) {
                doLogin();
            }
        });

        eventBus.addHandler(CadastrarEvent.TYPE, new CadastrarEventHandler() {
            @Override
            public void onCadastrar(CadastrarEvent event) {
                doCadastrar();
            }
        });
    }

    private void doLogin() {
        History.newItem(LOGIN_TOKEN);
    }

    private void doCadastrar() {
        History.newItem(CADASTRAR_TOKEN);
    }

    @Override
    public void go(final HasWidgets container) {
        this.container = container;

        if (History.getToken().isEmpty()) {
            History.newItem(LOGIN_TOKEN);
        } else {
            History.fireCurrentHistoryState();
        }
    }

    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
        String token = event.getValue();

        if (token != null) {
            switch (token) {
                case LOGIN_TOKEN:
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
                case CADASTRAR_TOKEN:
                    GWT.runAsync(new RunAsyncCallback() {
                        @Override
                        public void onFailure(Throwable reason) {

                        }

                        @Override
                        public void onSuccess() {
                            if (cadastroView == null) {
                                cadastroView = new CadastroViewImpl();
                            }

                            new CadastroPresenter(eventBus, cadastroView).go(container);
                        }
                    });
                    break;
            }
        }
    }
}
