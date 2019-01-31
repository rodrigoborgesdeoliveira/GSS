package br.pucpr.gss.client.view.uibinder;

import br.pucpr.gss.client.view.CadastroSolicitacaoView;
import br.pucpr.gss.client.view.MenuView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

import java.util.ArrayList;

public class CadastroSolicitacaoViewImpl extends Composite implements CadastroSolicitacaoView {
    interface CadastroSolicitacaoViewImplUiBinder extends UiBinder<Widget, CadastroSolicitacaoViewImpl> {
    }

    private static CadastroSolicitacaoViewImplUiBinder uiBinder = GWT.create(CadastroSolicitacaoViewImplUiBinder.class);

    public CadastroSolicitacaoViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    private Presenter presenter;

    @UiField
    MenuView menu;
    @UiField
    ListBox setor;
    @UiField
    TextBox titulo;
    @UiField
    TextArea descricao;
    @UiField
    Button cadastrar;
    @UiField
    Button cancelar;

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public MenuView getMenuView() {
        return menu;
    }

    @Override
    public void carregarListaSetores(ArrayList<String> setores) {
        for (String setor : setores) {
            this.setor.addItem(setor);
        }
    }

    @UiHandler("cadastrar")
    void onClickCadastrar(ClickEvent event) {
        if (presenter != null) {
            presenter.onCadastrarButtonClicked(titulo.getText(), setor.getSelectedIndex(), descricao.getText());
        }
    }

    @UiHandler("cancelar")
    void onClickCancelar(ClickEvent event) {
        if (presenter != null) {
            presenter.onCancelarButtonClicked();
        }
    }
}