package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.AppController;
import br.pucpr.gss.client.event.*;
import br.pucpr.gss.client.service.SolicitacaoService;
import br.pucpr.gss.client.view.DetalhesSolicitacaoView;
import br.pucpr.gss.shared.fabrica.Fabrica;
import br.pucpr.gss.shared.fabrica.FabricaEstado;
import br.pucpr.gss.shared.fabrica.FabricaPrioridade;
import br.pucpr.gss.shared.model.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Date;

public class DetalhesSolicitacaoPresenter implements Presenter, DetalhesSolicitacaoView.Presenter {

    private HandlerManager eventBus;
    private DetalhesSolicitacaoView view;
    private Usuario usuario;
    private Solicitacao solicitacao;
    private InformacaoAdicional informacaoAdicional;
    private Setor setor;
    private ArrayList<Setor> setores;
    private Usuario atendente;
    private ArrayList<Usuario> atendentes;
    private int indiceSetor;
    private int indiceAtendente;

    private final Fabrica fabricaPrioridade = new FabricaPrioridade();

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

    /**
     * Busca informações de um dado setor.
     *
     * @param idSetor       ID do setor para buscar informações.
     * @param asyncCallback Função para ser chamada após retorno do servidor.
     */
    private void fetchSolicitacaoSetor(int idSetor, AsyncCallback<Setor> asyncCallback) {
        SolicitacaoService.RPC.getInstance().getSetorById(idSetor, asyncCallback);
    }

    /**
     * Busca informações de um dado atendente.
     *
     * @param idAtendente   ID do atendente para buscar informações.
     * @param asyncCallback Função para ser chamada após retorno do servidor.
     */
    private void fetchSolicitacaoAtendente(int idAtendente, AsyncCallback<Usuario> asyncCallback) {
        SolicitacaoService.RPC.getInstance().getAtendenteById(idAtendente, asyncCallback);
    }

    /**
     * Busca a lista de todos os setores cadastrados no banco de dados do RH, exceto o setor do solicitante.
     *
     * @param asyncCallback Função para ser chamada após o retorno do servidor.
     */
    private void fetchSetores(int idSolicitante, AsyncCallback<ArrayList<Setor>> asyncCallback) {
        SolicitacaoService.RPC.getInstance().getListaOutrosSetores(idSolicitante, asyncCallback);
    }

    /**
     * Busca a lista de todos os atendentes em um dado setor, exceto o próprio gestor.
     *
     * @param idSetor       ID do setor para buscar os atendentes.
     * @param asyncCallback Função para ser chamada após o retorno do servidor.
     */
    private void fetchAtendentes(int idSetor, int idGestor, AsyncCallback<ArrayList<Usuario>> asyncCallback) {
        SolicitacaoService.RPC.getInstance().getListaAtendentesByIdSetorExcetoGestor(idSetor, idGestor, asyncCallback);
    }

    private void setViewUI() {
        // Iniciar interface com visibilidade padrão
        view.setVisibilidadeIniciarAtendimento(false);
        view.setVisibilidadePausarAtendimento(false);
        view.setVisibilidadeContinuarAtendimento(false);
        view.setVisibilidadeRequisitarInformacoesAdicionais(false);
        view.setVisualizarInformacoesAdicionais(false);
        view.setVisibilidadeRegistrarInformacoesAdicionais(false);
        view.setVisibilidadeOferecerSolucao(false);
        view.setVisibilidadeVisualizarSolucao(false);

        ArrayList<String> prioridades = new ArrayList<>();
        prioridades.add(fabricaPrioridade.criarPrioridade(FabricaPrioridade.BAIXA).getNome());
        prioridades.add(fabricaPrioridade.criarPrioridade(FabricaPrioridade.NORMAL).getNome());

        ArrayList<String> historicoEventos = new ArrayList<>();
        SolicitacaoService.RPC.getInstance().getEventosSolicitacao(solicitacao.getId(),
                new AsyncCallback<ArrayList<Evento>>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Não foi possível carregar o histórico");
                    }

                    @Override
                    public void onSuccess(ArrayList<Evento> result) {
                        for (Evento evento : result) {
                            historicoEventos.add(evento.getDataOcorrencia() + " - " + evento.getNome());
                        }
                    }
                });

        // Carregar a view de acordo com o tipo de usuário em relação à solicitação
        if (usuario.getIdFuncionario() == solicitacao.getIdAtendente()) {
            // Atendente
            this.view.setAtendenteUI(solicitacao.getTitulo(), solicitacao.getDescricao(),
                    solicitacao.getDataCriacao().toString(),
                    solicitacao.getPrazo(), solicitacao.getEstado().getNome(),
                    prioridades.indexOf(solicitacao.getPrioridade().getNome()), prioridades, historicoEventos);

            // Dependendo do estado, exibir as opções de iniciar, pausar e continuar atendimento
            switch (solicitacao.getEstado().getIndice()) {
                case FabricaEstado.AGUARDANDO_ATENDIMENTO:
                    view.setVisibilidadeIniciarAtendimento(true);
                    break;
                case FabricaEstado.EM_ANDAMENTO:
                    view.setVisibilidadePausarAtendimento(true);
                    view.setVisibilidadeOferecerSolucao(true);
                    break;
                case FabricaEstado.PAUSADA:
                    view.setVisibilidadeContinuarAtendimento(true);
                    view.setVisibilidadeRequisitarInformacoesAdicionais(true);
                    view.setVisibilidadeOferecerSolucao(true);
                    break;
                case FabricaEstado.RESPONDIDA:
                    view.setVisibilidadePausarAtendimento(true);
                    view.setVisibilidadeContinuarAtendimento(true);
                    view.setVisibilidadeRequisitarInformacoesAdicionais(true);
                    view.setVisualizarInformacoesAdicionais(true);
                    view.setVisibilidadeOferecerSolucao(true);
                    break;
                case FabricaEstado.ENCERRAMENTO_REJEITADO:
                    view.setVisibilidadePausarAtendimento(true);
                    view.setVisibilidadeContinuarAtendimento(true);
                    view.setVisibilidadeRequisitarInformacoesAdicionais(true);
                    break;
            }
        } else if (usuario.getIdFuncionario() == solicitacao.getIdSolicitante()) {
            // Solicitante
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
                                setSolicitanteUI(prioridades, historicoEventos);
                            }
                        });
                    } else {
                        setSolicitanteUI(prioridades, historicoEventos);
                    }
                }
            });
        } else if (usuario.getIdFuncionario() == solicitacao.getIdGestor()) {
            // Gestor
            prioridades.add(fabricaPrioridade.criarPrioridade(FabricaPrioridade.ALTA).getNome());

            fetchSetores(solicitacao.getIdSolicitante(), new AsyncCallback<ArrayList<Setor>>() {
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("Não foi possível carregar a lista de setores");
                }

                @Override
                public void onSuccess(ArrayList<Setor> result) {
                    setores = result;
                    indiceSetor = setores.indexOf(new Setor(solicitacao.getIdSetor(), "", 0));

                    fetchAtendentes(solicitacao.getIdSetor(), solicitacao.getIdGestor(), new AsyncCallback<ArrayList<Usuario>>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert("Não foi possível carregar a lista de atendentes");
                        }

                        @Override
                        public void onSuccess(ArrayList<Usuario> result) {
                            atendentes = result;
                            // Se id do atendente da solicitação for 0, índice será -1 e portanto a lista não mostrará
                            // nenhum item selecionado
                            indiceAtendente = atendentes.indexOf(new Usuario(0, "", false,
                                    solicitacao.getIdAtendente()));

                            setGestorUI(prioridades, historicoEventos);
                        }
                    });
                }
            });
        }
    }

    private void setSolicitanteUI(ArrayList<String> prioridades, ArrayList<String> historicoEventos) {
        String prazo = solicitacao.getPrazo() != null ? solicitacao.getPrazo().toString() : "";
        view.setSolicitanteUI(solicitacao.getTitulo(), solicitacao.getDescricao(),
                solicitacao.getDataCriacao().toString(), prazo,
                setor.getNome(), solicitacao.getEstado().getNome(),
                prioridades.indexOf(solicitacao.getPrioridade().getNome()), prioridades,
                atendente != null ? atendente.getNome() : "", historicoEventos);

        switch (solicitacao.getEstado().getIndice()) {
            case FabricaEstado.AGUARDANDO_INFORMACOES_ADICIONAIS:
                SolicitacaoService.RPC.getInstance().getInformacaoAdicionalByIdSolicitacao(solicitacao.getId(),
                        new AsyncCallback<InformacaoAdicional>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                Window.alert("Não foi possível carregar a requisição de informações adicionais");
                            }

                            @Override
                            public void onSuccess(InformacaoAdicional result) {
                                informacaoAdicional = result;

                                view.setVisibilidadeRegistrarInformacoesAdicionais(true);
                            }
                        });

                break;
            case FabricaEstado.ENCERRAMENTO_PROPOSTO:
                view.setVisibilidadeVisualizarSolucao(true);

                break;
        }
    }

    private void setGestorUI(ArrayList<String> prioridades, ArrayList<String> historicoEventos) {
        String prazo = solicitacao.getPrazo() != null ? solicitacao.getPrazo().toString() : "";

        ArrayList<String> nomesSetores = new ArrayList<>();
        for (Setor s : setores) {
            nomesSetores.add(s.getNome());
        }

        ArrayList<String> nomesAtendentes = new ArrayList<>();
        for (Usuario a : atendentes) {
            nomesAtendentes.add(a.getNome());
        }

        this.view.setGestorUI(solicitacao.getTitulo(), solicitacao.getDescricao(), solicitacao.getDataCriacao().toString(),
                prazo, indiceSetor, nomesSetores, solicitacao.getEstado().getNome(),
                prioridades.indexOf(solicitacao.getPrioridade().getNome()), prioridades, indiceAtendente, nomesAtendentes,
                historicoEventos);
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
    public void onSalvarButtonClicked(@Nullable Date prazo, int indiceSetor, int indicePrioridade, int indiceAtendente) {
        if (prazo != null) {
            solicitacao.setPrazo(prazo);
        }

        boolean isSetorAlterado = false; // se índice setor for < 0 ou o id do setor não for alterado, permanecerá falso

        if (indiceSetor >= 0) {
            Setor setor = setores.get(indiceSetor);

            if (solicitacao.getIdSetor() != setor.getId()) {
                isSetorAlterado = true;

                solicitacao.setIdSetor(setor.getId());
                solicitacao.setIdGestor(setor.getIdGestor());

                // Alterar o estado para pausada, prioridade para normal, e limpar o prazo e atendente
                solicitacao.setEstado(solicitacao.getEstado().pausarAtendimento());
                solicitacao.setPrioridade(fabricaPrioridade.criarPrioridade(FabricaPrioridade.NORMAL));
                solicitacao.setPrazo(null);
                solicitacao.setIdAtendente(0); // 0 ou menos para ser marcado como SQL NULL no banco de dados
            }
        }

        if (!isSetorAlterado) {
            if (indicePrioridade >= 0) {
                solicitacao.setPrioridade(fabricaPrioridade.criarPrioridade(indicePrioridade));
            }

            if (indiceAtendente >= 0) {
                solicitacao.setIdAtendente(atendentes.get(indiceAtendente).getIdFuncionario());
            }
        }

        SolicitacaoService.RPC.getInstance().updateSolicitacao(solicitacao, usuario, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Erro ao atualizar solicitação", caught);

                Window.alert("Não foi possível salvar a solicitação, tente novamente.");
            }

            @Override
            public void onSuccess(Void result) {
                GWT.log("Solicitação atualizada");

                Window.alert("Solicitação atualizada com sucesso");
                eventBus.fireEvent(new DashboardEvent());
            }
        });
    }

    @Override
    public void onIniciarAtendimentoClicked() {
        solicitacao.setEstado(solicitacao.getEstado().iniciarAtendimento());

        SolicitacaoService.RPC.getInstance().updateSolicitacao(solicitacao, usuario, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Não foi possível iniciar o atendimento");
            }

            @Override
            public void onSuccess(Void result) {
                setViewUI();
                Window.alert("Atendimento iniciado");
            }
        });
    }

    @Override
    public void onPausarAtendimentoClicked() {
        boolean isEstadoRespondida = solicitacao.getEstado().getIndice() == FabricaEstado.RESPONDIDA;

        solicitacao.setEstado(solicitacao.getEstado().pausarAtendimento());

        SolicitacaoService.RPC.getInstance().updateSolicitacao(solicitacao, usuario, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Não foi possível pausar o atendimento");
            }

            @Override
            public void onSuccess(Void result) {
                setViewUI();
                Window.alert("Atendimento pausado");

                if (isEstadoRespondida) {
                    removerInformacoesAdicionais();
                }
            }
        });
    }

    @Override
    public void onContinuarAtendimentoClicked() {
        boolean isEstadoRespondida = solicitacao.getEstado().getIndice() == FabricaEstado.RESPONDIDA;

        solicitacao.setEstado(solicitacao.getEstado().continuarAtendimento());

        SolicitacaoService.RPC.getInstance().updateSolicitacao(solicitacao, usuario, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Não foi possível retomar o atendimento");
            }

            @Override
            public void onSuccess(Void result) {
                setViewUI();
                Window.alert("Atendimento retomado");

                if (isEstadoRespondida) {
                    removerInformacoesAdicionais();
                }
            }
        });
    }

    private void removerInformacoesAdicionais() {
        SolicitacaoService.RPC.getInstance().removerInformacoesAdicionaisByIdSolicitacao(solicitacao.getId(),
                new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        GWT.log("Não foi possível remover as informações adicionais");
                    }

                    @Override
                    public void onSuccess(Void result) {
                        GWT.log("As informações adicionais foram removidas com sucesso");
                    }
                });
    }

    @Override
    public String getOnRequisitarInformacoesAdicionaisClickedToken() {
        return AppController.REQUISICAO_INFORMACOES_ADICIONAIS_TOKEN;
    }

    @Override
    public void onRequisitarInformacoesAdicionaisClicked() {
        eventBus.fireEvent(new RequisitarInformacoesAdicionaisEvent(solicitacao));
    }

    @Override
    public String getOnRegistrarInformacoesAdicionaisClickedToken() {
        return AppController.REGISTRO_INFORMACOES_ADICIONAIS_TOKEN;
    }

    @Override
    public void onRegistrarInformacoesAdicionaisClicked() {
        eventBus.fireEvent(new RegistrarInformacoesAdicionaisEvent(informacaoAdicional));
    }

    @Override
    public String getOnOferecerSolucaoClickedToken() {
        return AppController.REGISTRO_SOLUCAO_TOKEN;
    }

    @Override
    public void onOferecerSolucaoClicked() {
        eventBus.fireEvent(new OferecerSolucaoEvent(solicitacao));
    }

    @Override
    public String getOnVisualizarSolucaoClickedToken() {
        return AppController.VISUALIZACAO_SOLUCAO_TOKEN;
    }
}
