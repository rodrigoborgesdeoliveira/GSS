package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.event.VoltarEvent;
import br.pucpr.gss.client.service.CadastroService;
import br.pucpr.gss.client.view.CadastroView;
import br.pucpr.gss.shared.model.Resposta;
import br.pucpr.gss.shared.model.UsuarioLogin;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class CadastroPresenter implements Presenter, CadastroView.Presenter {
    private HandlerManager eventBus;
    private CadastroView view;

    public CadastroPresenter(HandlerManager eventBus, CadastroView view) {
        this.eventBus = eventBus;
        this.view = view;

        this.view.setPresenter(this);
    }

    @Override
    public void go(HasWidgets container) {
        container.clear();
        container.add(view.asWidget());
    }

    @Override
    public void onCadastrarButtonClicked(String email, String senha, String confirmarSenha) {
        UsuarioLogin usuario = new UsuarioLogin(email, senha);

        // Validar formato do email
        if (!usuario.isEmailValido()) {
            GWT.log("Campo email inválido");
            view.setEmailInvalido();

            return;
        }

        // Validar senha
        if (!usuario.isSenhaValida()) {
            GWT.log("Campo senha inválido");
            view.setSenhaInvalida();

            return;
        }

        // Validar confirmar senha
        if (!senha.equals(confirmarSenha)) {
            GWT.log("Campo confirmar senha inválido");
            view.setConfirmarSenhaInvalida();

            return;
        }

        CadastroService.RPC.getInstance().cadastrar(usuario, new AsyncCallback<Resposta>() {
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Erro ao cadastrar usuário", caught);

                view.setErro(caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(Resposta result) {
                GWT.log("Usuário cadastrado com sucesso");
            }
        });
    }

    @Override
    public void onVoltarClicked() {
        eventBus.fireEvent(new VoltarEvent());
    }
}
