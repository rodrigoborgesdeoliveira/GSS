package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.event.DetalhesSolicitacaoEvent;
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

                            if (informacaoAdicional.getResposta() != null && !informacaoAdicional.getResposta().isEmpty()) {
                                view.setInformacoesAdicionais(informacaoAdicional.getResposta());
                            }
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

        if (!informacaoAdicional.isDescricaoValida()) {
            Window.alert("A descrição não pode ser vazia");

            return;
        }

        SolicitacaoService.RPC.getInstance().requisitarInformacoesAdicionais(informacaoAdicional, solicitacao, usuario,
                new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Não possível salvar a requisição.");
                    }

                    @Override
                    public void onSuccess(Void result) {
                        // Atualizar o estado da solicitação para refletir a requisição de informações adicionais
                        solicitacao.setEstado(solicitacao.getEstado().requisitarInformacoesAdicionais());

                        SolicitacaoService.RPC.getInstance().updateSolicitacao(solicitacao, usuario, new AsyncCallback<Void>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                Window.alert("Não foi possível atualizar o estado da solicitação");
                            }

                            @Override
                            public void onSuccess(Void result) {
                                Window.alert("Requisição salva com sucesso");

                                eventBus.fireEvent(new DetalhesSolicitacaoEvent(solicitacao));
                            }
                        });
                    }
                });
    }
}
