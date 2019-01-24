package br.pucpr.gss.client.view.uibinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

public class CadastroView extends Composite {
    interface CadastroViewUiBinder extends UiBinder<HTMLPanel, CadastroView> {
    }

    private static CadastroViewUiBinder ourUiBinder = GWT.create(CadastroViewUiBinder.class);

    public CadastroView() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }
}