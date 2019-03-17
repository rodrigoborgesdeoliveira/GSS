package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.event.DetalhesSolicitacaoEvent;
import br.pucpr.gss.client.event.VoltarEvent;
import br.pucpr.gss.client.service.SolicitacaoService;
import br.pucpr.gss.client.view.ConsultaSolicitacoesView;
import br.pucpr.gss.shared.model.FiltroSolicitacao;
import br.pucpr.gss.shared.model.Solicitacao;
import br.pucpr.gss.shared.model.Usuario;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConsultaSolicitacoesPresenter implements Presenter, ConsultaSolicitacoesView.Presenter {

    private HandlerManager eventBus;
    private ConsultaSolicitacoesView view;
    private Usuario usuario;
    private ArrayList<Solicitacao> listaSolicitacoes;
    private FiltroSolicitacao filtroSolicitacao;

    public ConsultaSolicitacoesPresenter(HandlerManager eventBus, ConsultaSolicitacoesView view, Usuario usuario) {
        this.eventBus = eventBus;
        this.view = view;
        this.usuario = usuario;

        this.view.setPresenter(this);
        new MenuPresenter(eventBus, this.view.getMenuView(), usuario.isAdmin());

        fetchSolicitacoes();
    }

    @Override
    public void go(HasWidgets container) {
        container.clear();
        container.add(view.asWidget());
    }

    private void fetchSolicitacoes() {
        SolicitacaoService.RPC.getInstance().consultarSolicitacoes(usuario.getIdFuncionario(),
                new AsyncCallback<ArrayList<Solicitacao>>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        GWT.log("Erro ao carregar lista de solicitações", caught);

                        Window.alert("Não foi possível carregar lista de solicitações, tente novamente.");
                    }

                    @Override
                    public void onSuccess(ArrayList<Solicitacao> result) {
                        listaSolicitacoes = result;
                        filtroSolicitacao = new FiltroSolicitacao(usuario, listaSolicitacoes);

                        setListaSolicitacoes(listaSolicitacoes);
                    }
                });
    }

    private void setListaSolicitacoes(List<Solicitacao> solicitacoes) {
        ArrayList<String> solicitacoesNome = new ArrayList<>();
        ArrayList<String> solicitacoesPapel = new ArrayList<>();
        ArrayList<String> solicitacoesEstado = new ArrayList<>();
        ArrayList<String> solicitacoesPrioridade = new ArrayList<>();

        for (Solicitacao solicitacao : solicitacoes) {
            solicitacoesNome.add(solicitacao.getTitulo());

            String papel;
            if (usuario.getIdFuncionario() == solicitacao.getIdSolicitante()) {
                papel = "Solicitante";
            } else if (usuario.getIdFuncionario() == solicitacao.getIdAtendente()) {
                papel = "Atendente";
            } else {
                papel = "Gestor";
            }
            solicitacoesPapel.add(papel);

            solicitacoesEstado.add(solicitacao.getEstado().getNome());
            solicitacoesPrioridade.add(solicitacao.getPrioridade().getNome());
        }

        view.carregarListaSolicitacoes(solicitacoesNome, solicitacoesPapel, solicitacoesEstado,
                solicitacoesPrioridade);
    }

    @Override
    public void onCancelarButtonClicked() {
        eventBus.fireEvent(new VoltarEvent());
    }

    @Override
    public void onConsultarButtonClicked(int indiceSolicitacao) {
        Solicitacao solicitacao = listaSolicitacoes.get(indiceSolicitacao);

        eventBus.fireEvent(new DetalhesSolicitacaoEvent(solicitacao));
    }

    @Override
    public void filtrarPapel(boolean showSolicitante, boolean showAtendente, boolean showGestor) {
        if (filtroSolicitacao != null) {
            filtroSolicitacao.setShowSolicitante(showSolicitante);
            filtroSolicitacao.setShowAtendente(showAtendente);
            filtroSolicitacao.setShowGestor(showGestor);

            setListaSolicitacoes(filtroSolicitacao.filtrar());
        }
    }

    @Override
    public void filtrarTituloEData(String filtroTitulo, Date filtroDataInicial, Date filtroDataFinal) {
        if (filtroSolicitacao != null) {
            com.google.gwt.core.shared.GWT.log("Data inicial: " + filtroDataInicial);
            filtroSolicitacao.setFiltroTitulo(filtroTitulo);
            filtroSolicitacao.setFiltroDataInicial(filtroDataInicial);
            filtroSolicitacao.setFiltroDataFinal(filtroDataFinal);

            setListaSolicitacoes(filtroSolicitacao.filtrar());
        }
    }
}
