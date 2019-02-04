package br.pucpr.gss.client.view.uibinder;

import br.pucpr.gss.client.view.DetalhesSolicitacaoView;
import br.pucpr.gss.client.view.MenuView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;

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
    Label prazo;
    @UiField
    DateBox prazoVisaoAtendente;
    @UiField
    VerticalPanel grupoSetor;
    @UiField
    ListBox setorVisaoGestor;
    @UiField
    Label setorVisaoSolicitante;
    @UiField
    Label estadoVisaoGestor;
    @UiField
    ListBox estado;
    @UiField
    VerticalPanel grupoAtendente;
    @UiField
    Label atendenteVisaoSolicitante;
    @UiField
    ListBox atendenteVisaoGestor;

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public MenuView getMenuView() {
        return menu;
    }

    @Override
    public void setAtendenteUI() {
        prazo.setVisible(false);
        prazoVisaoAtendente.setVisible(true);
        grupoSetor.setVisible(false);
        estadoVisaoGestor.setVisible(false);
        estado.setVisible(true);
        grupoAtendente.setVisible(false);
        // TODO: 04/02/2019 Setar os possíveis estados
        // TODO: 04/02/2019 Setar as possíveis prioridades
    }

    @Override
    public void setSolicitanteUI() {
        prazo.setVisible(true);
        prazoVisaoAtendente.setVisible(false);
        grupoSetor.setVisible(true);
        setorVisaoGestor.setVisible(false);
        setorVisaoSolicitante.setVisible(true);
        estadoVisaoGestor.setVisible(false);
        estado.setVisible(true);
        grupoAtendente.setVisible(true);
        atendenteVisaoSolicitante.setVisible(true);
        atendenteVisaoGestor.setVisible(false);

        // TODO: 04/02/2019 Setar os possíveis estados
        // TODO: 04/02/2019 Setar as possíveis prioridades
    }

    @Override
    public void setGestorUI() {
        prazo.setVisible(true);
        prazoVisaoAtendente.setVisible(false);
        grupoSetor.setVisible(true);
        setorVisaoGestor.setVisible(true);
        setorVisaoSolicitante.setVisible(false);
        estadoVisaoGestor.setVisible(true);
        estado.setVisible(false);
        grupoAtendente.setVisible(true);
        atendenteVisaoSolicitante.setVisible(false);
        atendenteVisaoGestor.setVisible(true);

        // TODO: 04/02/2019 Setar as possíveis prioridades
    }
}