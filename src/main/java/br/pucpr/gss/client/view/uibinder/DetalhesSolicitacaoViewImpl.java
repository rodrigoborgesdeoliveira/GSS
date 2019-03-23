package br.pucpr.gss.client.view.uibinder;

import br.pucpr.gss.client.view.DetalhesSolicitacaoView;
import br.pucpr.gss.client.view.MenuView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.data.factory.RowComponentFactory;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class DetalhesSolicitacaoViewImpl extends Composite implements DetalhesSolicitacaoView, ValueChangeHandler<String> {

    private final String ACAO_REQUISITAR_INFORMACOES_ADICIONAIS = "Requisitar informações adicionais";
    private final String ACAO_VISUALIZAR_INFORMACOES_ADICIONAIS = "Visualizar informações adicionais";
    private final String ACAO_REGISTRAR_INFORMACOES_ADICIONAIS = "Registrar informações adicionais";
    private final String ACAO_OFERECER_SOLUCAO = "Oferecer solução";
    private final String ACAO_VISUALIZAR_SOLUCAO = "Visualizar solução";
    private final String ACAO_INICIAR = "Iniciar";
    private final String ACAO_PAUSAR = "Pausar";
    private final String ACAO_CONTINUAR = "Continuar";
    private final String ACAO_REGISTRAR_TAREFAS = "Registrar tarefa executada";

    interface DetalhesSolicitacaoViewImplUiBinder extends UiBinder<Widget, DetalhesSolicitacaoViewImpl> {
    }

    private static DetalhesSolicitacaoViewImplUiBinder uiBinder = GWT.create(DetalhesSolicitacaoViewImplUiBinder.class);

    private Presenter presenter;

    @UiField
    MenuView menu;
    @UiField
    MaterialTextBox textBoxTituloSolicitacao, textBoxEstado;
    @UiField
    MaterialDatePicker datePickerDataInicial, datePickerPrazo;
    @UiField
    MaterialListBox listBoxAcoes, listBoxSetor, listBoxPrioridade, listBoxAtendente, listBoxAtendimento;
    @UiField
    MaterialTextArea textAreaDescricao;
    @UiField
    MaterialDataTable<HistoricoView> tableHistorico;

    public DetalhesSolicitacaoViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));

        listBoxAcoes.addValueChangeHandler(this);
        listBoxAtendimento.addValueChangeHandler(this);

        tableHistorico.setRowFactory(new RowComponentFactory<>());
        tableHistorico.setUseStickyHeader(true);
        tableHistorico.addColumn(new TextColumn<HistoricoView>() {
            @Override
            public String getValue(HistoricoView historicoView) {
                return historicoView.getColunaDataOcorrencia();
            }
        }, "Data");
        tableHistorico.addColumn(new TextColumn<HistoricoView>() {
            @Override
            public String getValue(HistoricoView historicoView) {
                return historicoView.getColunaEvento();
            }
        }, "Evento");

        datePickerPrazo.setDateMin(new Date());
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public MenuView getMenuView() {
        return menu;
    }

    private void setUI(String tituloSolicitacao, String descricao, Date dataInicial, Date prazo, String estado,
                       int indicePrioridade, ArrayList<String> prioridades) {
        textBoxTituloSolicitacao.setText(tituloSolicitacao);
        datePickerDataInicial.setDate(dataInicial);
        datePickerPrazo.setDate(prazo);
        textBoxEstado.setText(estado);
        textAreaDescricao.setText(descricao);

        listBoxPrioridade.clear();
        for (String p : prioridades) {
            listBoxPrioridade.addItem(p);
        }
        listBoxPrioridade.setSelectedIndex(indicePrioridade);
    }

    @Override
    public void setAtendenteUI(String tituloSolicitacao, String descricao, Date dataInicial, Date prazo, String estado,
                               int indicePrioridade, ArrayList<String> prioridades) {

        setUI(tituloSolicitacao, descricao, dataInicial, prazo, estado, indicePrioridade, prioridades);

        // Definir elementos visíveis da interface
        datePickerPrazo.setReadOnly(false);
        listBoxSetor.setVisible(false);
        listBoxAtendente.setVisible(false);
    }

    @Override
    public void setSolicitanteUI(String tituloSolicitacao, String descricao, Date dataInicial, Date prazo, String setor,
                                 String estado, int indicePrioridade, ArrayList<String> prioridades, String nomeAtendente) {

        setUI(tituloSolicitacao, descricao, dataInicial, prazo, estado, indicePrioridade, prioridades);

        // Definir elementos visíveis da interface
        datePickerPrazo.setReadOnly(true);
        listBoxSetor.setVisible(true);
        listBoxSetor.setReadOnly(true);
        listBoxAtendente.setVisible(true);
        listBoxAtendente.setReadOnly(true);

        // Definir valor dos elementos
        listBoxSetor.addItem(setor);
        listBoxAtendente.addItem(nomeAtendente);
    }

    @Override
    public void setGestorUI(String tituloSolicitacao, String descricao, Date dataInicial, Date prazo, int indiceSetor,
                            ArrayList<String> setores, String estado, int indicePrioridade, ArrayList<String> prioridades,
                            int indiceAtendente, ArrayList<String> atendentes) {

        setUI(tituloSolicitacao, descricao, dataInicial, prazo, estado, indicePrioridade, prioridades);

        // Definir elementos visíveis da interface
        datePickerPrazo.setReadOnly(true);
        listBoxSetor.setVisible(true);
        listBoxSetor.setReadOnly(false);
        listBoxAtendente.setVisible(true);
        listBoxAtendente.setReadOnly(false);
        listBoxAtendente.setEmptyPlaceHolder("Selecione um atendente");

        // Definir valor dos elementos
        listBoxSetor.clear();
        for (String setor : setores) {
            listBoxSetor.addItem(setor);
        }
        listBoxSetor.setSelectedIndex(indiceSetor);

        listBoxAtendente.clear();
        for (String atendente : atendentes) {
            listBoxAtendente.addItem(atendente);
        }
        listBoxAtendente.setSelectedIndex(indiceAtendente);
    }

    @Override
    public void setHistorico(ArrayList<String> dataOcorrencia, ArrayList<String> historicoEventos) {
        ArrayList<HistoricoView> historico = new ArrayList<>();
        for (int i = 0; i < dataOcorrencia.size(); i++) {
            historico.add(new HistoricoView(dataOcorrencia.get(i), historicoEventos.get(i)));
        }

        tableHistorico.setRowData(0, historico);
    }

    @UiHandler("buttonCancelar")
    void onClickCancelar(ClickEvent event) {
        if (presenter != null) {
            presenter.onCancelarButtonClicked();
        }
    }

    @UiHandler("buttonSalvar")
    void onClickSalvar(ClickEvent event) {
        if (presenter != null) {
            presenter.onSalvarButtonClicked(datePickerPrazo.getValue(),
                    listBoxSetor.isVisible() && !listBoxSetor.isReadOnly() ? listBoxSetor.getSelectedIndex() : -1,
                    listBoxPrioridade.getSelectedIndex(),
                    listBoxAtendente.isVisible() && !listBoxAtendente.isReadOnly() ? listBoxAtendente.getSelectedIndex() : -1);
        }
    }

    /**
     * Exibe ou esconde o painel de atendimento considerando a visibilidade das ações de inicar, pausar e retomar o
     * atendimento.
     */
    private void showHidePanelAtendimento() {
        if (listBoxAtendimento.getItemCount() > 1) { // 1 é o empty placeholder
            listBoxAtendimento.setVisible(true);
        } else {
            listBoxAtendimento.setVisible(false);
        }
    }

    @Override
    public void setVisibilidadeIniciarAtendimento(boolean visivel) {
        addRemoveItemLista(listBoxAtendimento, ACAO_INICIAR, visivel);
        showHidePanelAtendimento();
    }

    @Override
    public void setVisibilidadePausarAtendimento(boolean visivel) {
        addRemoveItemLista(listBoxAtendimento, ACAO_PAUSAR, visivel);
        showHidePanelAtendimento();
    }

    @Override
    public void setVisibilidadeContinuarAtendimento(boolean visivel) {
        addRemoveItemLista(listBoxAtendimento, ACAO_CONTINUAR, visivel);
        showHidePanelAtendimento();
    }

    @Override
    public void setVisibilidadeRequisitarInformacoesAdicionais(boolean visivel) {
        addRemoveItemLista(listBoxAcoes, ACAO_REQUISITAR_INFORMACOES_ADICIONAIS, visivel);
    }

    @Override
    public void setVisualizarInformacoesAdicionais(boolean isVisualizar) {
        int indiceRequisitarInformacoes = getIndex(listBoxAcoes, ACAO_REQUISITAR_INFORMACOES_ADICIONAIS);
        int indiceVisualizarInformacoes = getIndex(listBoxAcoes, ACAO_VISUALIZAR_INFORMACOES_ADICIONAIS);
        // Só realizar a troca de opção se alguma das opções já foi adicionada à lista
        if (indiceRequisitarInformacoes > 0 || indiceVisualizarInformacoes > 0) {
            addRemoveItemLista(listBoxAcoes, ACAO_VISUALIZAR_INFORMACOES_ADICIONAIS, isVisualizar);
            addRemoveItemLista(listBoxAcoes, ACAO_REQUISITAR_INFORMACOES_ADICIONAIS, !isVisualizar);
        }
    }

    @Override
    public void setVisibilidadeRegistrarInformacoesAdicionais(boolean visivel) {
        addRemoveItemLista(listBoxAcoes, ACAO_REGISTRAR_INFORMACOES_ADICIONAIS, visivel);
    }

    @Override
    public void setVisibilidadeOferecerSolucao(boolean visivel) {
        addRemoveItemLista(listBoxAcoes, ACAO_OFERECER_SOLUCAO, visivel);
    }

    @Override
    public void setVisibilidadeVisualizarSolucao(boolean visivel) {
        addRemoveItemLista(listBoxAcoes, ACAO_VISUALIZAR_SOLUCAO, visivel);
    }

    @Override
    public void setVisibilidadeRegistrarTarefasExecutadas(boolean visivel) {
        addRemoveItemLista(listBoxAcoes, ACAO_REGISTRAR_TAREFAS, visivel);
    }

    /**
     * Adiciona ou remove um item à ou da lista.
     * Se a lista não possuir nenhum item após a adição/remoção do item, ela será escondida.
     *
     * @param item      Item para ser adicionado ou removido.
     * @param adicionar true, para adicionar, ou false, para remover.
     */
    private void addRemoveItemLista(MaterialListBox listBox, String item, boolean adicionar) {
        GWT.log("Visibilidade de " + item + " = " + adicionar);

        if (adicionar) {
            listBox.addItem(item);
        } else {
            int indice = getIndex(listBox, item);

            // Índice -1 para itens não encontrados e 0 para o empty placeholder da lista
            if (indice > 0) {
                listBox.removeItem(indice);
            }
        }

        if (listBox.getItemCount() - 1 == 0) { // Subtraindo 1 por causa do empty placeholder
            listBox.setVisible(false);
        } else {
            listBox.setVisible(true);
        }
    }

    /**
     * Reescreve o método {@link MaterialListBox#getIndex(Object)}, pois ele está caindo em um IndexOutOfBoundsException
     * quando a lista tem um empty placeholder.
     *
     * @param value O valor do item a ser encontrado.
     * @return O índice do item encontrado a partir de 1 (0 é o empty placeholder) ou -1 se nenhum foi encontrado.
     */
    private int getIndex(MaterialListBox listBox, String value) {
        int count = listBox.getItemCount() - 1; // Substraindo 1 por causa do empty placeholder

        for (int i = 0; i < count; i++) {
            if (Objects.equals(listBox.getValue(i), value)) {
                return i + 1; // Soma 1, porque o empty placeholder da
                // lista não é considerado na hora de pegar o índice, mas é na hora de remover um item. Então o getIndex
                // pode retornar 0 para o primeiro item da lista, mas ao tentar remover 0, irá remover o item anterior ao
                // que queremos, nesse caso, o empty placeholder da lista.
            }
        }

        return -1;
    }

    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
        // Ação selecionada no listBoxAcoes, ir para a página apropriada
        if (presenter != null) {
            switch (event.getValue()) {
                case ACAO_REQUISITAR_INFORMACOES_ADICIONAIS:
                case ACAO_VISUALIZAR_INFORMACOES_ADICIONAIS:
                    presenter.onRequisitarInformacoesAdicionaisClicked();
                    break;
                case ACAO_REGISTRAR_INFORMACOES_ADICIONAIS:
                    presenter.onRegistrarInformacoesAdicionaisClicked();
                    break;
                case ACAO_OFERECER_SOLUCAO:
                    presenter.onOferecerSolucaoClicked();
                    break;
                case ACAO_VISUALIZAR_SOLUCAO:
                    presenter.onVisualizarSolucaoClicked();
                    break;
                case ACAO_REGISTRAR_TAREFAS:
                    onClickRegistarTarefa();

                    break;
                case ACAO_INICIAR:
                    onClickIniciarAtendimento();
                    break;
                case ACAO_CONTINUAR:
                    onClickContinuarAtendimento();
                    break;
                case ACAO_PAUSAR:
                    onClickPausarAtendimento();
                    break;
            }
        }
    }

    private void onClickIniciarAtendimento() {
        if (presenter != null) {
            presenter.onIniciarAtendimentoClicked();
            listBoxAtendimento.setSelectedValue(listBoxAtendimento.getEmptyPlaceHolder());
            listBoxAtendimento.reload();
        }
    }

    private void onClickPausarAtendimento() {
        if (presenter != null) {
            presenter.onPausarAtendimentoClicked();
            listBoxAtendimento.setSelectedValue(listBoxAtendimento.getEmptyPlaceHolder());
            listBoxAtendimento.reload();
        }
    }

    private void onClickContinuarAtendimento() {
        if (presenter != null) {
            presenter.onContinuarAtendimentoClicked();
            listBoxAtendimento.setSelectedValue(listBoxAtendimento.getEmptyPlaceHolder());
            listBoxAtendimento.reload();
        }
    }

    private void onClickRegistarTarefa() {
        String tarefaRegistrada = Window.prompt("Tarefa executada (máximo de 62 caracteres)",
                "");
        // Se for null, significa que o diálogo foi cancelado
        if (tarefaRegistrada != null) {
            if (tarefaRegistrada.isEmpty()) {
                Window.alert("O campo não pode ser vazio");
                onClickRegistarTarefa();
            } else if (tarefaRegistrada.length() > 62) {
                Window.alert("O campo deve possuir no máximo 62 caracteres");
                onClickRegistarTarefa();
            } else {
                presenter.registrarTarefa(tarefaRegistrada);
            }
        }
    }

    /**
     * Classe para exibir o histórico na tabela com diferentes colunas.
     */
    private class HistoricoView {

        private String colunaDataOcorrencia;
        private String colunaEvento;

        HistoricoView(String colunaDataOcorrencia, String colunaEvento) {
            this.colunaDataOcorrencia = colunaDataOcorrencia;
            this.colunaEvento = colunaEvento;
        }

        String getColunaDataOcorrencia() {
            return colunaDataOcorrencia;
        }

        String getColunaEvento() {
            return colunaEvento;
        }
    }
}