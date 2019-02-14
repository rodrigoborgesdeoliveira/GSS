package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.event.DetalhesSolicitacaoEvent;
import br.pucpr.gss.client.event.VoltarEvent;
import br.pucpr.gss.client.service.SolicitacaoService;
import br.pucpr.gss.client.view.RegistroInformacoesAdicionaisView;
import br.pucpr.gss.shared.model.InformacaoAdicional;
import br.pucpr.gss.shared.model.Solicitacao;
import br.pucpr.gss.shared.model.Usuario;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class RegistroInformacoesAdicionaisPresenter implements Presenter, RegistroInformacoesAdicionaisView.Presenter {

    private HandlerManager eventBus;
    private RegistroInformacoesAdicionaisView view;
    private Usuario usuario;
    private Solicitacao solicitacao;
    private InformacaoAdicional informacaoAdicional;

    public RegistroInformacoesAdicionaisPresenter(HandlerManager eventBus, RegistroInformacoesAdicionaisView view,
                                                  Usuario usuario, Solicitacao solicitacao,
                                                  InformacaoAdicional informacaoAdicional) {
        this.eventBus = eventBus;
        this.view = view;
        this.usuario = usuario;
        this.solicitacao = solicitacao;
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

    @Override
    public void onCancelarButtonClicked() {
        eventBus.fireEvent(new VoltarEvent());
    }

    @Override
    public void onSalvarButtonClicked(String resposta) {
        informacaoAdicional.setResposta(resposta);

        SolicitacaoService.RPC.getInstance().registrarInformacoesAdicionais(informacaoAdicional, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Não foi possível registrar as informações adicionais");
            }

            @Override
            public void onSuccess(Void result) {
                // Atualizar estado da solicitação no banco de dados
                solicitacao.setEstado(solicitacao.getEstado().registrarInformacoesAdicionais());

                SolicitacaoService.RPC.getInstance().updateSolicitacao(solicitacao, new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Não foi possível atualizar o estado da solicitação");
                    }

                    @Override
                    public void onSuccess(Void result) {
                        Window.alert("Informações adicionais registradas com sucesso");

                        eventBus.fireEvent(new DetalhesSolicitacaoEvent(solicitacao));
                    }
                });
            }
        });
    }
}
