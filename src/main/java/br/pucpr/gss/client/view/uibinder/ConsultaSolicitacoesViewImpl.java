package br.pucpr.gss.client.view.uibinder;

import br.pucpr.gss.client.view.ConsultaSolicitacoesView;
import br.pucpr.gss.client.view.MenuView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.data.events.RowSelectEvent;
import gwt.material.design.client.data.events.RowSelectHandler;
import gwt.material.design.client.data.factory.RowComponentFactory;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;

import java.util.ArrayList;

public class ConsultaSolicitacoesViewImpl extends Composite implements ConsultaSolicitacoesView {
    interface ConsultaSolicitacaoUiBinder extends UiBinder<Widget, ConsultaSolicitacoesViewImpl> {
    }

    private static ConsultaSolicitacaoUiBinder uiBinder = GWT.create(ConsultaSolicitacaoUiBinder.class);

    private Presenter presenter;

    @UiField
    MenuView menu;
    @UiField
    MaterialDatePicker datePickerDataInicial, datePickerDataFinal;
    @UiField
    MaterialDataTable<SolicitacaoView> tableSolicitacoes;

    private int solicitacaoSelecionadaIndice = -1; // < 0 representa que nenhuma está selecionada

    public ConsultaSolicitacoesViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));

        tableSolicitacoes.setRowFactory(new RowComponentFactory<>());
        tableSolicitacoes.setUseStickyHeader(true);
        tableSolicitacoes.addColumn(new TextColumn<SolicitacaoView>() {
            @Override
            public String getValue(SolicitacaoView solicitacaoView) {
                return solicitacaoView.getColunaTitulo();
            }
        }, "Título");
        tableSolicitacoes.addColumn(new TextColumn<SolicitacaoView>() {
            @Override
            public String getValue(SolicitacaoView solicitacaoView) {
                return solicitacaoView.getColunaPapelUsuario();
            }
        }, "Papel");
        tableSolicitacoes.addColumn(new TextColumn<SolicitacaoView>() {
            @Override
            public String getValue(SolicitacaoView solicitacaoView) {
                return solicitacaoView.getColunaEstado();
            }
        }, "Estado");
        tableSolicitacoes.addColumn(new TextColumn<SolicitacaoView>() {
            @Override
            public String getValue(SolicitacaoView solicitacaoView) {
                return solicitacaoView.getColunaPrioridade();
            }
        }, "Prioridade");
        tableSolicitacoes.addRowSelectHandler(new RowSelectHandler<SolicitacaoView>() {
            @Override
            public void onRowSelect(RowSelectEvent<SolicitacaoView> rowSelectEvent) {
                if (rowSelectEvent.isSelected()) {
                    // Solicitação selecionada
                    solicitacaoSelecionadaIndice = tableSolicitacoes.getRowValueIndex(TableRowElement
                            .as(rowSelectEvent.getRow()));
                } else {
                    solicitacaoSelecionadaIndice = -1;
                }
            }
        });
        // TODO: 12/03/2019 Implementar um listener nas datas para limitar a data máxima da data inicial e a mínima da
        //  data final
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public MenuView getMenuView() {
        return menu;
    }

    @Override
    public void carregarListaSolicitacoes(ArrayList<String> solicitacoes, ArrayList<String> papelUsuario,
                                          ArrayList<String> estado, ArrayList<String> prioridade) {
        ArrayList<SolicitacaoView> solicitacoesView = new ArrayList<>();
        for (int i = 0; i < solicitacoes.size(); i++) {
            solicitacoesView.add(new SolicitacaoView(solicitacoes.get(i), papelUsuario.get(i), estado.get(i),
                    prioridade.get(i)));
        }
        tableSolicitacoes.setRowData(0, solicitacoesView);
    }

    @UiHandler("cancelar")
    void onClickCancelar(ClickEvent event) {
        if (presenter != null) {
            presenter.onCancelarButtonClicked();
        }
    }

    @UiHandler("consultar")
    void onClickConsultar(ClickEvent event) {
        if (presenter != null) {
            if (solicitacaoSelecionadaIndice >= 0) {
                presenter.onConsultarButtonClicked(solicitacaoSelecionadaIndice);
            } else {
                Window.alert("Nenhuma solicitação selecionada");
            }
        }
    }

    private class SolicitacaoView {
        private String colunaTitulo, colunaPapelUsuario, colunaEstado, colunaPrioridade;

        public SolicitacaoView(String colunaTitulo, String colunaPapelUsuario, String colunaEstado,
                               String colunaPrioridade) {
            this.colunaTitulo = colunaTitulo;
            this.colunaPapelUsuario = colunaPapelUsuario;
            this.colunaEstado = colunaEstado;
            this.colunaPrioridade = colunaPrioridade;
        }

        public String getColunaTitulo() {
            return colunaTitulo;
        }

        public String getColunaPapelUsuario() {
            return colunaPapelUsuario;
        }

        public String getColunaEstado() {
            return colunaEstado;
        }

        public String getColunaPrioridade() {
            return colunaPrioridade;
        }
    }
}