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
    ListBox labelAtendenteVisaoGestor;
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

    private void setUI(String tituloSolicitacao, String descricao, String dataInicial, String estado) {
        labelTituloSolicitacao.setText(tituloSolicitacao);
        textAreaDescricao.setText(descricao);
        labelDataInicial.setText(dataInicial);
        labelEstado.setText(estado);
    }

    @Override
    public void setAtendenteUI(String tituloSolicitacao, String descricao, String dataInicial, Date prazo, String estado,
                               int indicePrioridade, ArrayList<String> prioridades) {
        labelPrazo.setVisible(false);
        dateBoxPrazoVisaoAtendente.setVisible(true);
        verticalPanelSetor.setVisible(false);
        verticalPanelAtendente.setVisible(false);

        setUI(tituloSolicitacao, descricao, dataInicial, estado);
        // TODO: 04/02/2019 Setar as possíveis prioridades
    }

    @Override
    public void setSolicitanteUI(String tituloSolicitacao, String descricao, String dataInicial, String prazo, String setor,
                                 String estado, int indicePrioridade, ArrayList<String> prioridades, String nomeAtendente) {
        labelPrazo.setVisible(true);
        dateBoxPrazoVisaoAtendente.setVisible(false);
        verticalPanelSetor.setVisible(true);
        listBoxSetorVisaoGestor.setVisible(false);
        labelSetorVisaoSolicitante.setVisible(true);
        verticalPanelAtendente.setVisible(true);
        labelAtendenteVisaoSolicitante.setVisible(true);
        labelAtendenteVisaoGestor.setVisible(false);

        setUI(tituloSolicitacao, descricao, dataInicial, estado);
        // TODO: 04/02/2019 Setar as possíveis prioridades
    }

    @Override
    public void setGestorUI(String tituloSolicitacao, String descricao, String dataInicial, String prazo, int indiceSetor,
                            ArrayList<String> setores, String estado, int prioridade, ArrayList<String> prioridades,
                            int indiceAtendente, ArrayList<String> atendentes) {
        labelPrazo.setVisible(true);
        dateBoxPrazoVisaoAtendente.setVisible(false);
        verticalPanelSetor.setVisible(true);
        listBoxSetorVisaoGestor.setVisible(true);
        labelSetorVisaoSolicitante.setVisible(false);
        verticalPanelAtendente.setVisible(true);
        labelAtendenteVisaoSolicitante.setVisible(false);
        labelAtendenteVisaoGestor.setVisible(true);

        setUI(tituloSolicitacao, descricao, dataInicial, estado);
        // TODO: 04/02/2019 Setar as possíveis prioridades
    }
}