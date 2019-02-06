package br.pucpr.gss.client.view.uibinder;

import br.pucpr.gss.client.view.DetalhesSolicitacaoView;
import br.pucpr.gss.client.view.MenuView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
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
}