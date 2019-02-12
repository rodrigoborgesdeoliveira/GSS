package br.pucpr.gss.client;

import br.pucpr.gss.client.event.*;
import br.pucpr.gss.client.presenter.*;
import br.pucpr.gss.client.view.uibinder.*;
import br.pucpr.gss.shared.model.Solicitacao;
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
    public static final String DASHBOARD_TOKEN = "dashboard";
    public static final String CADASTRAR_SOLICITACAO_TOKEN = "cadastrarSolicitacao";
    public static final String CONSULTAR_SOLICITACOES_TOKEN = "consultarSolicitacao";
    public static final String DETALHES_SOLICITACAO_TOKEN = "detalhesSolicitacao";
    public static final String REQUISICAO_INFORMACOES_ADICIONAIS = "requisicaoInformacoesAdicionais";

    private final HandlerManager eventBus;
    private HasWidgets container;

    private Usuario usuario;
    private Solicitacao solicitacao;

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

        eventBus.addHandler(VoltarEvent.TYPE, event -> doVoltar());

        eventBus.addHandler(LogoutEvent.TYPE, event -> doLogout());

        eventBus.addHandler(CadastrarEvent.TYPE, event -> doCarregarCadastro());

        eventBus.addHandler(DashboardEvent.TYPE, event -> doCarregarDashboard());

        eventBus.addHandler(CadastrarEvent.TYPE, event -> doCarregarCadastroSolicitacao());

        eventBus.addHandler(ConsultarSolicitacoesEvent.TYPE, event -> doCarregarConsultaSolicitacoes());

        eventBus.addHandler(DetalhesSolicitacaoEvent.TYPE, event -> doCarregarDetalhesSolicitacao(event.getSolicitacao()));

        eventBus.addHandler(RequisitarInformacoesAdicionaisEvent.TYPE, event ->
                doCarregarRequisicaoInformacoesAdicionais(event.getSolicitacao()));
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
        doCarregarDashboard();
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

    private void doLogout() {
        usuario = null;
    }

    private void doCarregarDashboard() {
        History.newItem(DASHBOARD_TOKEN);
    }

    private void doCarregarCadastroSolicitacao() {
        History.newItem(CADASTRAR_SOLICITACAO_TOKEN);
    }

    private void doCarregarConsultaSolicitacoes() {
        History.newItem(CONSULTAR_SOLICITACOES_TOKEN);
    }

    private void doCarregarDetalhesSolicitacao(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;

        History.newItem(DETALHES_SOLICITACAO_TOKEN);
    }

    private void doCarregarRequisicaoInformacoesAdicionais(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
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
                                History.newItem(DASHBOARD_TOKEN);
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
                                History.newItem(DASHBOARD_TOKEN);
                            }
                        }
                    });
                    break;
                case DASHBOARD_TOKEN:
                    if (usuario == null) {
                        // Usuário não logado
                        History.newItem(LOGIN_TOKEN);
                    } else {
                        new DashboardPresenter(eventBus, new DashboardViewImpl(), usuario).go(container);
                    }

                    break;
                case CADASTRAR_SOLICITACAO_TOKEN:
                    if (usuario == null) {
                        // Usuário não logado
                        History.newItem(LOGIN_TOKEN);
                    } else {
                        new CadastroSolicitacaoPresenter(eventBus, new CadastroSolicitacaoViewImpl(), usuario)
                                .go(container);
                    }

                    break;
                case CONSULTAR_SOLICITACOES_TOKEN:
                    if (usuario == null) {
                        // Usuário não logado
                        History.newItem(LOGIN_TOKEN);
                    } else {
                        new ConsultaSolicitacoesPresenter(eventBus, new ConsultaSolicitacoesViewImpl(), usuario)
                                .go(container);
                    }
                    break;
                case DETALHES_SOLICITACAO_TOKEN:
                    if (usuario == null) {
                        // Usuário não logado
                        History.newItem(LOGIN_TOKEN);
                    } else {
                        new DetalhesSolicitacaoPresenter(eventBus, new DetalhesSolicitacaoViewImpl(), usuario, solicitacao)
                                .go(container);
                    }

                    break;
                case REQUISICAO_INFORMACOES_ADICIONAIS:
                    if (usuario == null) {
                        // Usuário não logado
                        History.newItem(LOGIN_TOKEN);
                    } else {
                        new RequisicaoInformacoesAdicionaisPresenter(eventBus, new RequisicaoInformacoesAdicionaisViewImpl(),
                                usuario, solicitacao).go(container);
                    }

                    break;
            }
        }
    }
}
