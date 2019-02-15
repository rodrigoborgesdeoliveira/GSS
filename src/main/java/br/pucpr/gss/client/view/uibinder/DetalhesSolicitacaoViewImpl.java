package br.pucpr.gss.client.view.uibinder;

import br.pucpr.gss.client.view.DetalhesSolicitacaoView;
import br.pucpr.gss.client.view.MenuView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;

import java.util.ArrayList;
import java.util.Date;

public class DetalhesSolicitacaoViewImpl extends Composite implements DetalhesSolicitacaoView {
    interface DetalhesSolicitacaoViewImplUiBinder extends UiBinder<Widget, DetalhesSolicitacaoViewImpl> {
    }

    private static DetalhesSolicitacaoViewImplUiBinder uiBinder = GWT.create(DetalhesSolicitacaoViewImplUiBinder.class);

    public DetalhesSolicitacaoViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    private Presenter presenter;

    @UiField
    Hyperlink linkRequisitarInformacoesAdicionais;
    @UiField
    Hyperlink linkRegistrarInformacoesAdicionais;
    @UiField
    VerticalPanel verticalPanelAtendimento;
    @UiField
    Button buttonIniciarAtendimento;
    @UiField
    Button buttonPausarAtendimento;
    @UiField
    Button buttonContinuarAtendimento;
    @UiField
    MenuView menu;
    @UiField
    Label labelTituloSolicitacao;
    @UiField
    Label labelDataInicial;
    @UiField
    Label labelPrazo;
    @UiField
    DateBox dateBoxPrazoVisaoAtendente;
    @UiField
    VerticalPanel verticalPanelSetor;
    @UiField
    ListBox listBoxSetorVisaoGestor;
    @UiField
    Label labelSetorVisaoSolicitante;
    @UiField
    Label labelEstado;
    @UiField
    ListBox listBoxPrioridade;
    @UiField
    VerticalPanel verticalPanelAtendente;
    @UiField
    Label labelAtendenteVisaoSolicitante;
    @UiField
    ListBox listBoxAtendenteVisaoGestor;
    @UiField
    TextArea textAreaDescricao;
    @UiField
    ListBox listBoxHistorico;

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;

        if (presenter != null) {
            linkRequisitarInformacoesAdicionais.setTargetHistoryToken(this.presenter
                    .getOnRequisitarInformacoesAdicionaisClickedToken());
            linkRegistrarInformacoesAdicionais.setTargetHistoryToken(this.presenter
                    .getOnRegistrarInformacoesAdicionaisClickedToken());
        }
    }

    @Override
    public MenuView getMenuView() {
        return menu;
    }

    private void setUI(String tituloSolicitacao, String descricao, String dataInicial, String estado,
                       int indicePrioridade, ArrayList<String> prioridades) {
        labelTituloSolicitacao.setText(tituloSolicitacao);
        textAreaDescricao.setText(descricao);
        labelDataInicial.setText(dataInicial);
        labelEstado.setText(estado);

        for (String p : prioridades) {
            listBoxPrioridade.addItem(p);
        }
        listBoxPrioridade.setSelectedIndex(indicePrioridade);
    }

    @Override
    public void setAtendenteUI(String tituloSolicitacao, String descricao, String dataInicial, Date prazo, String estado,
                               int indicePrioridade, ArrayList<String> prioridades) {

        setUI(tituloSolicitacao, descricao, dataInicial, estado, indicePrioridade, prioridades);

        // Definir elementos visíveis da interface
        labelPrazo.setVisible(false);
        dateBoxPrazoVisaoAtendente.setVisible(true);
        verticalPanelSetor.setVisible(false);
        verticalPanelAtendente.setVisible(false);

        dateBoxPrazoVisaoAtendente.setValue(prazo);
    }

    @Override
    public void setSolicitanteUI(String tituloSolicitacao, String descricao, String dataInicial, String prazo, String setor,
                                 String estado, int indicePrioridade, ArrayList<String> prioridades, String nomeAtendente) {

        setUI(tituloSolicitacao, descricao, dataInicial, estado, indicePrioridade, prioridades);

        // Definir elementos visíveis da interface
        labelPrazo.setVisible(true);
        dateBoxPrazoVisaoAtendente.setVisible(false);
        verticalPanelSetor.setVisible(true);
        listBoxSetorVisaoGestor.setVisible(false);
        labelSetorVisaoSolicitante.setVisible(true);
        verticalPanelAtendente.setVisible(true);
        labelAtendenteVisaoSolicitante.setVisible(true);
        listBoxAtendenteVisaoGestor.setVisible(false);

        // Definir valor dos elementos
        labelPrazo.setText(prazo);
        labelSetorVisaoSolicitante.setText(setor);
        labelAtendenteVisaoSolicitante.setText(nomeAtendente);
    }

    @Override
    public void setGestorUI(String tituloSolicitacao, String descricao, String dataInicial, String prazo, int indiceSetor,
                            ArrayList<String> setores, String estado, int indicePrioridade, ArrayList<String> prioridades,
                            int indiceAtendente, ArrayList<String> atendentes) {

        setUI(tituloSolicitacao, descricao, dataInicial, estado, indicePrioridade, prioridades);

        // Definir elementos visíveis da interface
        labelPrazo.setVisible(true);
        dateBoxPrazoVisaoAtendente.setVisible(false);
        verticalPanelSetor.setVisible(true);
        listBoxSetorVisaoGestor.setVisible(true);
        labelSetorVisaoSolicitante.setVisible(false);
        verticalPanelAtendente.setVisible(true);
        labelAtendenteVisaoSolicitante.setVisible(false);
        listBoxAtendenteVisaoGestor.setVisible(true);

        // Definir valor dos elementos
        labelPrazo.setText(prazo);

        for (String setor : setores) {
            listBoxSetorVisaoGestor.addItem(setor);
        }
        listBoxSetorVisaoGestor.setSelectedIndex(indiceSetor);

        for (String atendente : atendentes) {
            listBoxAtendenteVisaoGestor.addItem(atendente);
        }
        listBoxAtendenteVisaoGestor.setSelectedIndex(indiceAtendente);
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
            presenter.onSalvarButtonClicked(dateBoxPrazoVisaoAtendente.getValue(),
                    listBoxSetorVisaoGestor.isVisible() ? listBoxSetorVisaoGestor.getSelectedIndex() : -1,
                    listBoxPrioridade.getSelectedIndex(),
                    listBoxAtendenteVisaoGestor.isVisible() ? listBoxAtendenteVisaoGestor.getSelectedIndex() : -1);
        }
    }

    /**
     * Exibe ou esconde o painel de atendimento considerando a visbilidade dos botões de inicar, pausar e retomar o
     * atendimento.
     */
    private void showHidePanelAtendimento() {
        if (buttonIniciarAtendimento.isVisible() || buttonPausarAtendimento.isVisible() ||
                buttonContinuarAtendimento.isVisible()) {
            verticalPanelAtendimento.setVisible(true);
        } else {
            verticalPanelAtendimento.setVisible(false);
        }
    }

    @Override
    public void setVisibilidadeIniciarAtendimento(boolean visivel) {
        buttonIniciarAtendimento.setVisible(visivel);
        showHidePanelAtendimento();
    }

    @Override
    public void setVisibilidadePausarAtendimento(boolean visivel) {
        buttonPausarAtendimento.setVisible(visivel);
        showHidePanelAtendimento();
    }

    @Override
    public void setVisibilidadeContinuarAtendimento(boolean visivel) {
        buttonContinuarAtendimento.setVisible(visivel);
        showHidePanelAtendimento();
    }

    @Override
    public void setVisibilidadeRequisitarInformacoesAdicionais(boolean visivel) {
        linkRequisitarInformacoesAdicionais.setVisible(visivel);
    }

    @Override
    public void setVisualizarInformacoesAdicionais(boolean isVisualizar) {
        if (isVisualizar) {
            linkRequisitarInformacoesAdicionais.setText("Visualizar informações adicionais");
        } else {
            linkRequisitarInformacoesAdicionais.setText("Requisitar informações adicionais");
        }
    }

    @Override
    public void setVisibilidadeRegistrarInformacoesAdicionais(boolean visivel) {
        linkRegistrarInformacoesAdicionais.setVisible(visivel);
    }

    @UiHandler("buttonIniciarAtendimento")
    void onClickIniciarAtendimento(ClickEvent event) {
        if (presenter != null) {
            presenter.onIniciarAtendimentoClicked();
        }
    }

    @UiHandler("buttonPausarAtendimento")
    void onClickPausarAtendimento(ClickEvent event) {
        if (presenter != null) {
            presenter.onPausarAtendimentoClicked();
        }
    }

    @UiHandler("buttonContinuarAtendimento")
    void onClickContinuarAtendimento(ClickEvent event) {
        if (presenter != null) {
            presenter.onContinuarAtendimentoClicked();
        }
    }

    @UiHandler("linkRequisitarInformacoesAdicionais")
    void onClickRequisitarInformacoesAdicionais(ClickEvent event) {
        if (presenter != null) {
            presenter.onRequisitarInformacoesAdicionaisClicked();
        }
    }

    @UiHandler("linkRegistrarInformacoesAdicionais")
    void onClickRegistrarInformacoesAdicionais(ClickEvent event) {
        if (presenter != null) {
            presenter.onRegistrarInformacoesAdicionaisClicked();
        }
    }
}