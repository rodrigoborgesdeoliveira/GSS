package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.event.VoltarEvent;
import br.pucpr.gss.client.service.SolicitacaoService;
import br.pucpr.gss.client.view.RequisicaoInformacoesAdicionaisView;
import br.pucpr.gss.shared.model.InformacaoAdicional;
import br.pucpr.gss.shared.model.Solicitacao;
import br.pucpr.gss.shared.model.Usuario;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class RequisicaoInformacoesAdicionaisPresenter implements Presenter, RequisicaoInformacoesAdicionaisView.Presenter {

    private HandlerManager eventBus;
    private RequisicaoInformacoesAdicionaisView view;
    private Usuario usuario;
    private Solicitacao solicitacao;
    private InformacaoAdicional informacaoAdicional;

    public RequisicaoInformacoesAdicionaisPresenter(HandlerManager eventBus, RequisicaoInformacoesAdicionaisView view,
                                                    Usuario usuario, Solicitacao solicitacao) {
        this.eventBus = eventBus;
        this.view = view;
        this.usuario = usuario;
        this.solicitacao = solicitacao;

        this.view.setPresenter(this);

        new MenuPresenter(eventBus, this.view.getMenuView(), usuario.isAdmin());

        setView();
    }

    @Override
    public void go(HasWidgets container) {
        container.clear();
        container.add(view.asWidget());
    }

    private void setView() {
        SolicitacaoService.RPC.getInstance().getInformacaoAdicionalByIdSolicitacao(solicitacao.getId(),
                new AsyncCallback<InformacaoAdicional>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        GWT.log("Não foi possível carregar informação adicional");
                    }

                    @Override
                    public void onSuccess(InformacaoAdicional result) {
                        informacaoAdicional = result;

                        if (informacaoAdicional != null) {
                            // Uma informação adicional já foi requisitada antes, inserir a descrição na tela
                            view.setDescricao(informacaoAdicional.getDescricao());
                        }
                    }
                });
    }

    @Override
    public void onCancelarButtonClicked() {
        eventBus.fireEvent(new VoltarEvent());
    }

    @Override
    public void onSalvarButtonClicked(String descricao) {
        if (informacaoAdicional != null) {
            informacaoAdicional.setDescricao(descricao);
        } else {
            informacaoAdicional = new InformacaoAdicional(descricao, null, solicitacao.getId());
        }

        SolicitacaoService.RPC.getInstance().requisitarInformacoesAdicionais(informacaoAdicional, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Não possível salvar a requisição.");
            }

            @Override
            public void onSuccess(Void result) {
                Window.alert("Requisição salva com sucesso");

                eventBus.fireEvent(new VoltarEvent());
            }
        });
    }
}
