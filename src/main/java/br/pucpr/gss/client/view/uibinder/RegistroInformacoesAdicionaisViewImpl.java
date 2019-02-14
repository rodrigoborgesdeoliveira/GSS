package br.pucpr.gss.client.view.uibinder;

import br.pucpr.gss.client.view.MenuView;
import br.pucpr.gss.client.view.RegistroInformacoesAdicionaisView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class RegistroInformacoesAdicionaisViewImpl extends Composite implements RegistroInformacoesAdicionaisView {
    interface RegistroInformacoesAdicionaisUiBinder extends UiBinder<Widget, RegistroInformacoesAdicionaisViewImpl> {
    }

    private static RegistroInformacoesAdicionaisUiBinder uiBinder = GWT.create(RegistroInformacoesAdicionaisUiBinder.class);

    public RegistroInformacoesAdicionaisViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    private Presenter presenter;

    @UiField
    MenuView menu;

    @UiField
    TextArea textAreaDescricao;
    @UiField
    TextArea textAreaInformacoesAdicionais;

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

    @UiHandler("buttonCancelar")
    void onClickCancelar(ClickEvent event) {
        if (presenter != null) {
            presenter.onCancelarButtonClicked();
        }
    }

    @UiHandler("buttonSalvar")
    void onClickSalvar(ClickEvent event) {
        if (presenter != null) {
            presenter.onSalvarButtonClicked(textAreaInformacoesAdicionais.getText());
        }
    }
}