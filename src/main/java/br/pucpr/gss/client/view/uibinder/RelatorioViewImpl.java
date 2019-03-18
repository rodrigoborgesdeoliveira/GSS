package br.pucpr.gss.client.view.uibinder;

import br.pucpr.gss.client.view.RelatorioView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class RelatorioViewImpl extends Composite implements RelatorioView {
    interface RelatorioViewImplUiBinder extends UiBinder<Widget, RelatorioViewImpl> {
    }

    private static RelatorioViewImplUiBinder uiBinder = GWT.create(RelatorioViewImplUiBinder.class);

    private Presenter presenter;

    @UiField
    ScrollPanel pagina;

    public RelatorioViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setHtml(String html) {
        pagina.add(new HTML(html, true));
    }
}