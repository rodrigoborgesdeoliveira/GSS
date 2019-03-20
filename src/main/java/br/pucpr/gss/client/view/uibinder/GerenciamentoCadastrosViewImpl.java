package br.pucpr.gss.client.view.uibinder;

import br.pucpr.gss.client.view.GerenciamentoCadastrosView;
import br.pucpr.gss.client.view.MenuView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.data.factory.RowComponentFactory;
import gwt.material.design.client.ui.MaterialSwitch;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

import java.util.ArrayList;
import java.util.List;

public class GerenciamentoCadastrosViewImpl extends Composite implements GerenciamentoCadastrosView {
    interface GerenciamentoCadastrosUiBinder extends UiBinder<Widget, GerenciamentoCadastrosViewImpl> {
    }

    private static GerenciamentoCadastrosUiBinder uiBinder = GWT.create(GerenciamentoCadastrosUiBinder.class);

    private Presenter presenter;

    @UiField
    MenuView menu;
    @UiField
    MaterialDataTable<UsuariosView> tableUsuarios;

    public GerenciamentoCadastrosViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));

        // Configurar as colunas da tabela de solicitações
        tableUsuarios.setRowFactory(new RowComponentFactory<>());
        tableUsuarios.setUseStickyHeader(true);
        tableUsuarios.addColumn(new WidgetColumn<UsuariosView, MaterialSwitch>() {
            @Override
            public MaterialSwitch getValue(UsuariosView usuario) {
                MaterialSwitch materialSwitch = new MaterialSwitch();
                materialSwitch.setValue(usuario.getColunaIsAdmin());
                final int indiceListaUsuarios = usuario.getIndiceLista();

                materialSwitch.addValueChangeHandler(event -> {
                    if (presenter != null) {
                        presenter.setAdmin(indiceListaUsuarios, event.getValue());
                    }
                });

                return materialSwitch;
            }
        }, "Admin");
        tableUsuarios.addColumn(new TextColumn<UsuariosView>() {
            @Override
            public String getValue(UsuariosView usuarioView) {
                return usuarioView.getColunaNome();
            }
        }, "Nome");
        tableUsuarios.setEnabled(false);
        tableUsuarios.addRowSelectHandler(event -> {
            tableUsuarios.getView().setRedraw(true);
            tableUsuarios.getView().refresh();
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

    @Override
    public void carregarListaUsuarios(List<String> nomeUsuarios, List<Boolean> isUsuariosAdmin) {
        GWT.log("Lista inicial: " + isUsuariosAdmin);

        List<UsuariosView> usuariosViews = new ArrayList<>();
        for (int i = 0; i < nomeUsuarios.size(); i++) {
            usuariosViews.add(new UsuariosView(i, isUsuariosAdmin.get(i), nomeUsuarios.get(i)));
        }
        tableUsuarios.setRowData(0, usuariosViews);
    }

    private class UsuariosView {
        private int indiceLista;
        private boolean colunaIsAdmin;
        private String colunaNome;

        public UsuariosView(int indiceLista, boolean colunaIsAdmin, String colunaNome) {
            this.indiceLista = indiceLista;
            this.colunaIsAdmin = colunaIsAdmin;
            this.colunaNome = colunaNome;
        }

        public int getIndiceLista() {
            return indiceLista;
        }

        public boolean getColunaIsAdmin() {
            return colunaIsAdmin;
        }

        public String getColunaNome() {
            return colunaNome;
        }
    }
}