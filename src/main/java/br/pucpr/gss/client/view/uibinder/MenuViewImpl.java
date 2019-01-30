package br.pucpr.gss.client.view.uibinder;

import br.pucpr.gss.client.view.MenuView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

public class MenuViewImpl extends Composite implements MenuView {

    interface MenuViewImplUiBinder extends UiBinder<HTMLPanel, MenuViewImpl> {
    }

    private static MenuViewImplUiBinder uiBinder = GWT.create(MenuViewImplUiBinder.class);

    private Presenter presenter;

    public MenuViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}