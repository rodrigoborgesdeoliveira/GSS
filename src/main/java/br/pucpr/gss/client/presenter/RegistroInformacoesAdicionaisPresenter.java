package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.view.RegistroInformacoesAdicionaisView;
import br.pucpr.gss.shared.model.InformacaoAdicional;
import br.pucpr.gss.shared.model.Usuario;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

public class RegistroInformacoesAdicionaisPresenter implements Presenter, RegistroInformacoesAdicionaisView.Presenter {

    private HandlerManager eventBus;
    private RegistroInformacoesAdicionaisView view;
    private Usuario usuario;
    private InformacaoAdicional informacaoAdicional;

    public RegistroInformacoesAdicionaisPresenter(HandlerManager eventBus, RegistroInformacoesAdicionaisView view,
                                                  Usuario usuario, InformacaoAdicional informacaoAdicional) {
        this.eventBus = eventBus;
        this.view = view;
        this.usuario = usuario;
        this.informacaoAdicional = informacaoAdicional;

        this.view.setPresenter(this);
        this.view.setDescricao(informacaoAdicional.getDescricao());

        new MenuPresenter(eventBus, this.view.getMenuView(), usuario.isAdmin());
    }

    @Override
    public void go(HasWidgets container) {
        container.clear();
        container.add(view.asWidget());
    }
}
