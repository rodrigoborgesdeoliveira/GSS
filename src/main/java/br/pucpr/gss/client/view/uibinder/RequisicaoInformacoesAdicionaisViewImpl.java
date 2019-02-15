package br.pucpr.gss.client.view.uibinder;

import br.pucpr.gss.client.view.MenuView;
import br.pucpr.gss.client.view.RequisicaoInformacoesAdicionaisView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class RequisicaoInformacoesAdicionaisViewImpl extends Composite implements RequisicaoInformacoesAdicionaisView {
    interface RequisicaoInformacoesAdicionaisUiBinder extends UiBinder<Widget, RequisicaoInformacoesAdicionaisViewImpl> {
    }

    private static RequisicaoInformacoesAdicionaisUiBinder uiBinder = GWT.create(RequisicaoInformacoesAdicionaisUiBinder.class);

    public RequisicaoInformacoesAdicionaisViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    private Presenter presenter;

    @UiField
    MenuView menu;
    @UiField
    TextArea textAreaDescricao;
    @UiField
    TextArea textAreaInformacoesAdicionais;
    @UiField
    VerticalPanel verticalPanelInformacoesAdicionais;

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public MenuView getMenuView() {
        return menu;
    }

    @Override
    public void setDescricao(String descricao) {
        textAreaDescricao.setText(descricao);
    }

    @Override
    public void setInformacoesAdicionais(String informacoesAdicionais) {
        textAreaInformacoesAdicionais.setText(informacoesAdicionais);
        verticalPanelInformacoesAdicionais.setVisible(true);
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
            presenter.onSalvarButtonClicked(textAreaDescricao.getText());
        }
    }
}