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
    MaterialDataTable<String> tableSolicitacoes;

    private int solicitacaoSelecionadaIndice = -1; // < 0 representa que nenhuma está selecionada

    public ConsultaSolicitacoesViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));

        tableSolicitacoes.setRowFactory(new RowComponentFactory<>());
        tableSolicitacoes.addColumn(new TextColumn<String>() {
            @Override
            public String getValue(String s) {
                return s;
            }
        }, "Título");
        tableSolicitacoes.addRowSelectHandler(new RowSelectHandler<String>() {
            @Override
            public void onRowSelect(RowSelectEvent<String> rowSelectEvent) {
                if (rowSelectEvent.isSelected()) {
                    // Solicitação selecionada
                    solicitacaoSelecionadaIndice = tableSolicitacoes.getRowValueIndex(TableRowElement.as(rowSelectEvent.getRow()));
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
    public void carregarListaSolicitacoes(ArrayList<String> solicitacoes) {
        tableSolicitacoes.setRowData(0, solicitacoes);
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
}