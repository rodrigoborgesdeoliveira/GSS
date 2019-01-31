package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.event.DashboardEvent;
import br.pucpr.gss.client.event.VoltarEvent;
import br.pucpr.gss.client.service.SolicitacaoService;
import br.pucpr.gss.client.view.CadastroSolicitacaoView;
import br.pucpr.gss.shared.model.Setor;
import br.pucpr.gss.shared.model.Solicitacao;
import br.pucpr.gss.shared.model.Usuario;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import java.util.ArrayList;
import java.util.Date;

public class CadastroSolicitacaoPresenter implements Presenter, CadastroSolicitacaoView.Presenter {

    private HandlerManager eventBus;
    private CadastroSolicitacaoView view;
    private Usuario usuario;
    private ArrayList<Setor> listaSetores;

    public CadastroSolicitacaoPresenter(HandlerManager eventBus, CadastroSolicitacaoView view, Usuario usuario) {
        this.eventBus = eventBus;
        this.view = view;
        this.usuario = usuario;

        this.view.setPresenter(this);

        new MenuPresenter(eventBus, this.view.getMenuView(), usuario.isAdmin());

        fetchListaSetores();
    }

    @Override
    public void go(HasWidgets container) {
        container.clear();
        container.add(view.asWidget());
    }

    /**
     * Obtém a lista de setores no banco de dados do RH.
     */
    private void fetchListaSetores() {
        SolicitacaoService.RPC.getInstance().getListaSetores(new AsyncCallback<ArrayList<Setor>>() {
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Erro ao carregar lista de setores", caught);

                Window.alert("Não foi possível carregar lista de setores, tente novamente.");
            }

            @Override
            public void onSuccess(ArrayList<Setor> result) {
                listaSetores = result;
                ArrayList<String> setoresNome = new ArrayList<>();

                for (Setor setor : result) {
                    setoresNome.add(setor.getNome());
                }

                view.carregarListaSetores(setoresNome);
            }
        });
    }

    @Override
    public void onCadastrarButtonClicked(String titulo, int indiceSetor, String descricao) {
        Setor setorSelecionado = listaSetores.get(indiceSetor);
        Solicitacao solicitacao = new Solicitacao(titulo, descricao, new Date(), setorSelecionado.getId(), usuario.getId(),
                setorSelecionado.getIdGestor());
        SolicitacaoService.RPC.getInstance().cadastrarSolicitacao(solicitacao, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Erro ao cadastrar solicitação", caught);

                Window.alert("Não foi possível cadastrar a solicitação, tente novamente.");
            }

            @Override
            public void onSuccess(Void result) {
                GWT.log("Solicitação cadastrada");

                Window.alert("Solicitação cadastrada com sucesso");
                eventBus.fireEvent(new DashboardEvent());
            }
        });
    }

    @Override
    public void onCancelarButtonClicked() {
        eventBus.fireEvent(new VoltarEvent());
    }
}
