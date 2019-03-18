package br.pucpr.gss.client.view.uibinder;

import br.pucpr.gss.client.view.GeracaoRelatorioView;
import br.pucpr.gss.client.view.MenuView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialDatePicker;

import java.util.Date;

public class GeracaoRelatorioViewImpl extends Composite implements GeracaoRelatorioView {
    interface GeracaoRelatorioViewImplUiBinder extends UiBinder<Widget, GeracaoRelatorioViewImpl> {
    }

    private static GeracaoRelatorioViewImplUiBinder uiBinder = GWT.create(GeracaoRelatorioViewImplUiBinder.class);

    private Presenter presenter;

    @UiField
    MenuView menu;
    @UiField
    MaterialDatePicker datePickerDataInicial, datePickerDataFinal;
    @UiField
    MaterialCheckBox checkBoxSolicitante, checkBoxAtendente, checkBoxGestor;

    public GeracaoRelatorioViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));

        // A data inicial e final não pode ser no futuro devido à data de criação
        datePickerDataInicial.setDateMax(new Date());
        datePickerDataFinal.setDateMax(new Date());

        // Não permitir que a data final seja menor que a inicial
        datePickerDataInicial.addCloseHandler(event -> {
            datePickerDataFinal.setDateMin(datePickerDataInicial.getDate()!=null?datePickerDataInicial.getDate():
                    new Date(0L));
        });
        datePickerDataFinal.addCloseHandler(event -> {
            datePickerDataInicial.setDateMax(datePickerDataFinal.getDate() != null ? datePickerDataFinal.getDate() :
                    new Date());
        });
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public MenuView getMenuView() {
        return menu;
    }
}