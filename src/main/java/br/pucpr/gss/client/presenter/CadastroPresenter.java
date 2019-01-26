package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.event.VoltarEvent;
import br.pucpr.gss.client.view.CadastroView;
import com.google.gwt.event.shared.HandlerManager;
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
        // TODO: 25/01/2019 Validar campos e cadastrar
    }

    @Override
    public void onVoltarClicked() {
        eventBus.fireEvent(new VoltarEvent());
    }
}
