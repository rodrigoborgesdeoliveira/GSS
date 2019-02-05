package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.service.SolicitacaoService;
import br.pucpr.gss.client.view.DetalhesSolicitacaoView;
import br.pucpr.gss.shared.fabrica.Fabrica;
import br.pucpr.gss.shared.fabrica.FabricaPrioridade;
import br.pucpr.gss.shared.model.Setor;
import br.pucpr.gss.shared.model.Solicitacao;
import br.pucpr.gss.shared.model.Usuario;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import java.util.ArrayList;

public class DetalhesSolicitacaoPresenter implements Presenter, DetalhesSolicitacaoView.Presenter {

    private HandlerManager eventBus;
    private DetalhesSolicitacaoView view;
    private Usuario usuario;
    private Solicitacao solicitacao;
    private Setor setor;
    private ArrayList<Setor> setores;
    private Usuario atendente;

    public DetalhesSolicitacaoPresenter(HandlerManager eventBus, DetalhesSolicitacaoView view, Usuario usuario,
                                        Solicitacao solicitacao) {
        this.eventBus = eventBus;
        this.view = view;
        this.usuario = usuario;
        this.solicitacao = solicitacao;

        this.view.setPresenter(this);

        new MenuPresenter(eventBus, this.view.getMenuView(), usuario.isAdmin());

        setViewUI();
    }

    private void fetchSolicitacaoSetor(int idSetor, AsyncCallback<Setor> asyncCallback) {
        SolicitacaoService.RPC.getInstance().getSetorById(idSetor, asyncCallback);
    }

    private void fetchSolicitacaoAtendente(int idAtendente, AsyncCallback<Usuario> asyncCallback) {
        SolicitacaoService.RPC.getInstance().getAtendenteById(idAtendente, asyncCallback);
    }

    private void fetchSetores(AsyncCallback<ArrayList<Setor>> asyncCallback) {
        SolicitacaoService.RPC.getInstance().getListaSetores(asyncCallback);
    }

    private void fetchAtendentes(int idSetor, AsyncCallback<ArrayList<Usuario>> asyncCallback) {
        SolicitacaoService.RPC.getInstance().getListaAtendentesByIdSetor(idSetor, asyncCallback);
    }

    private void setViewUI() {
        Fabrica fabricaPrioridade = new FabricaPrioridade();

        ArrayList<String> prioridades = new ArrayList<>();
        prioridades.add(fabricaPrioridade.criarPrioridade(FabricaPrioridade.BAIXA).getNome());
        prioridades.add(fabricaPrioridade.criarPrioridade(FabricaPrioridade.NORMAL).getNome());

        // Carregar a view de acordo com o tipo de usuário em relação à solicitação
        if (usuario.getId() == solicitacao.getIdAtendente()) {
            this.view.setAtendenteUI(solicitacao.getTitulo(), solicitacao.getDescricao(), solicitacao.getDataCriacao().toString(),
                    solicitacao.getPrazo(), solicitacao.getEstado().getNome(),
                    prioridades.indexOf(solicitacao.getPrioridade().getNome()), prioridades);
        } else if (usuario.getId() == solicitacao.getIdSolicitante()) {
            fetchSolicitacaoSetor(solicitacao.getIdSetor(), new AsyncCallback<Setor>() {
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("Não foi possível carregar o setor da solicitação");
                }

                @Override
                public void onSuccess(Setor result) {
                    setor = result;

                    if (solicitacao.getIdAtendente() > 0) {
                        // A solicitação já foi delegada a um atendente
                        fetchSolicitacaoAtendente(solicitacao.getIdAtendente(), new AsyncCallback<Usuario>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                Window.alert("Não foi possível carregar o atendente da solicitação");
                            }

                            @Override
                            public void onSuccess(Usuario result) {
                                atendente = result; // Pode não ter sido definido ainda
                                setSolicitanteUI(prioridades);
                            }
                        });
                    } else {
                        setSolicitanteUI(prioridades);
                    }

                }
            });
        } else if (usuario.getId() == solicitacao.getIdGestor()) {
            prioridades.add(fabricaPrioridade.criarPrioridade(FabricaPrioridade.ALTA).getNome());

            fetchSetores(new AsyncCallback<ArrayList<Setor>>() {
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("Não foi possível carregar a lista de setores");
                }

                @Override
                public void onSuccess(ArrayList<Setor> result) {
                    setores = result;
                    setor = setores.get(setores.indexOf(new Setor(solicitacao.getIdSetor(), "", 0)));

//                    fetchAtendentes();
                }
            });

            ArrayList<String> nomesSetores = new ArrayList<>();
            for (Setor s : setores) {
                nomesSetores.add(s.getNome());
            }
            /*this.view.setGestorUI(solicitacao.getTitulo(), solicitacao.getDescricao(), solicitacao.getDataCriacao().toString(),
                    solicitacao.getPrazo().toString(), setor.getId(), nomesSetores, solicitacao.getEstado().getNome(),
                    prioridades.indexOf(solicitacao.getPrioridade().getNome()),prioridades, );*/
        }
    }

    private void setSolicitanteUI(ArrayList<String> prioridades) {
        view.setSolicitanteUI(solicitacao.getTitulo(), solicitacao.getDescricao(),
                solicitacao.getDataCriacao().toString(), solicitacao.getPrazo().toString(),
                setor.getNome(), solicitacao.getEstado().getNome(),
                prioridades.indexOf(solicitacao.getPrioridade().getNome()), prioridades,
                atendente != null ? atendente.getNome() : "");
    }

    @Override
    public void go(HasWidgets container) {
        container.clear();
        container.add(view.asWidget());
    }
}
