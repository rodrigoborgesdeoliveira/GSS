package br.pucpr.gss.client;

import br.pucpr.gss.client.event.CadastrarEvent;
import br.pucpr.gss.client.event.LoginEvent;
import br.pucpr.gss.client.event.VoltarEvent;
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;

public class AppController implements Presenter, ValueChangeHandler<String> {

    // Tokens do histórico
    public static final String LOGIN_TOKEN = "login";
    public static final String CADASTRAR_TOKEN = "cadastrar";

    private final HandlerManager eventBus;
    private HasWidgets container;

    public AppController(HandlerManager eventBus) {
        this.eventBus = eventBus;
        bind();
    }

    /**
     * Registra handlers para os eventos recebidos pelo event bus.
     */
    private void bind() {
        History.addValueChangeHandler(this);

        eventBus.addHandler(LoginEvent.TYPE, event -> doCarregarLogin());

        eventBus.addHandler(CadastrarEvent.TYPE, event -> doCarregarCadastro());

        eventBus.addHandler(VoltarEvent.TYPE, event -> doVoltar());
    }

    /**
     * Carregar a tela de login.
     */
    private void doCarregarLogin() {
        History.newItem(LOGIN_TOKEN);
    }

    /**
     * Carregar a tela de cadastro.
     */
    private void doCarregarCadastro() {
        History.newItem(CADASTRAR_TOKEN);
    }

    /**
     * Voltar à tela anterior.
     */
    private void doVoltar() {
        History.back();
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
                            Window.alert("Falha ao carregar a página.");
                        }

                        @Override
                        public void onSuccess() {
                            new LoginPresenter(eventBus, new LoginViewImpl()).go(container);
                        }
                    });
                    break;
                case CADASTRAR_TOKEN:
                    GWT.runAsync(new RunAsyncCallback() {
                        @Override
                        public void onFailure(Throwable reason) {
                            Window.alert("Falha ao carregar a página.");
                        }

                        @Override
                        public void onSuccess() {
                            new CadastroPresenter(eventBus, new CadastroViewImpl()).go(container);
                        }
                    });
                    break;
            }
        }
    }
}
