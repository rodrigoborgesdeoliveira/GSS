package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.view.RequisicaoInformacoesAdicionaisView;
import br.pucpr.gss.shared.model.Solicitacao;
import br.pucpr.gss.shared.model.Usuario;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

public class RequisicaoInformacoesAdicionaisPresenter implements Presenter, RequisicaoInformacoesAdicionaisView.Presenter {

    private HandlerManager eventBus;
    private RequisicaoInformacoesAdicionaisView view;
    private Usuario usuario;
    private Solicitacao solicitacao;

    public RequisicaoInformacoesAdicionaisPresenter(HandlerManager eventBus, RequisicaoInformacoesAdicionaisView view,
                                                    Usuario usuario, Solicitacao solicitacao) {
        this.eventBus = eventBus;
        this.view = view;
        this.usuario = usuario;
        this.solicitacao = solicitacao;

        this.view.setPresenter(this);

        new MenuPresenter(eventBus, this.view.getMenuView(), usuario.isAdmin());
    }

    @Override
    public void go(HasWidgets container) {
        container.clear();
        container.add(view.asWidget());
    }
}
