package br.pucpr.gss.client;

import br.pucpr.gss.client.event.CadastrarEvent;
import br.pucpr.gss.client.event.LoginEvent;
import br.pucpr.gss.client.event.LoginSucesso;
import br.pucpr.gss.client.event.VoltarEvent;
import br.pucpr.gss.client.presenter.CadastroPresenter;
import br.pucpr.gss.client.presenter.LoginPresenter;
import br.pucpr.gss.client.presenter.Presenter;
import br.pucpr.gss.client.view.uibinder.CadastroViewImpl;
import br.pucpr.gss.client.view.uibinder.LoginViewImpl;
import br.pucpr.gss.shared.model.Usuario;
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
    public static final String DASHBOARD = "dashboard";

    private final HandlerManager eventBus;
    private HasWidgets container;

    private Usuario usuario;

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

        eventBus.addHandler(LoginSucesso.TYPE, event -> doLoginSucesso(event.getUsuario()));

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
     * Processar o login sucedido.
     *
     * @param usuario usuário que está logado no sistema.
     */
    private void doLoginSucesso(Usuario usuario) {
        this.usuario = usuario;
        History.newItem(DASHBOARD);
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
                            if (usuario == null) {
                                // Usuário não está logado
                                new LoginPresenter(eventBus, new LoginViewImpl()).go(container);
                            } else {
                                // Usuário está logado, redirecionar para a dashboard
                                History.newItem(DASHBOARD);
                            }
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
                            if (usuario == null) {
                                // Usuário não está logado
                                new CadastroPresenter(eventBus, new CadastroViewImpl()).go(container);
                            } else {
                                // Usuário está logado, redirecionar para a dashboard
                                History.newItem(DASHBOARD);
                            }
                        }
                    });
                    break;
                case DASHBOARD:
                    // TODO: 29/01/2019 Implementar Dashboard view e redirecionar para a dashboard aqui
                    break;
            }
        }
    }
}
