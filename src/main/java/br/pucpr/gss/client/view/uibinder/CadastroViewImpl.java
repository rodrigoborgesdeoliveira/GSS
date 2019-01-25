package br.pucpr.gss.client.view.uibinder;

import br.pucpr.gss.client.view.CadastroView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

public class CadastroViewImpl extends Composite implements CadastroView {

    interface CadastroViewUiBinder extends UiBinder<HTMLPanel, CadastroViewImpl> {
    }

    private static CadastroViewUiBinder uiBinder = GWT.create(CadastroViewUiBinder.class);

    private Presenter presenter;

    public CadastroViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}