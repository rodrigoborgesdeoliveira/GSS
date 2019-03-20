package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.event.DashboardEvent;
import br.pucpr.gss.client.service.CadastroService;
import br.pucpr.gss.client.view.GerenciamentoCadastrosView;
import br.pucpr.gss.shared.model.Usuario;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import gwt.material.design.client.ui.MaterialToast;

import java.util.ArrayList;
import java.util.List;

public class GerenciamentoCadastrosPresenter implements Presenter, GerenciamentoCadastrosView.Presenter {

    private HandlerManager eventBus;
    private GerenciamentoCadastrosView view;
    private Usuario usuario;
    private List<Usuario> cadastros;

    public GerenciamentoCadastrosPresenter(HandlerManager eventBus, GerenciamentoCadastrosView view, Usuario usuario) {
        this.eventBus = eventBus;
        this.view = view;
        this.usuario = usuario;

        this.view.setPresenter(this);

        new MenuPresenter(eventBus, this.view.getMenuView(), usuario.isAdmin());

        if (!this.usuario.isAdmin()) {
            this.eventBus.fireEvent(new DashboardEvent());
        }

        fetchUsuarios();
    }

    @Override
    public void go(HasWidgets container) {
        container.clear();
        container.add(view.asWidget());
    }

    private void fetchUsuarios() {
        CadastroService.RPC.getInstance().getCadastros(usuario.getId(), new AsyncCallback<List<Usuario>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(List<Usuario> result) {
                GWT.log("Usuarios: " + result);
                cadastros = new ArrayList<>();

                List<String> nomeUsuarios = new ArrayList<>();
                List<Boolean> isUsuariosAdmin = new ArrayList<>();
                result.forEach(u -> {
                    // Não mostrar usuário atual na lista
                    if (u.getId() != usuario.getId()) {
                        cadastros.add(u);
                        nomeUsuarios.add(u.getNome());
                        isUsuariosAdmin.add(u.isAdmin());
                    }
                });

                view.carregarListaUsuarios(nomeUsuarios, isUsuariosAdmin);
            }
        });
    }

    @Override
    public void setAdmin(int indiceListaUsuarios, boolean asAdmin) {
        if (cadastros != null && usuario.isAdmin()) {
            CadastroService.RPC.getInstance().setAdmin(usuario.getId(), cadastros.get(indiceListaUsuarios).getId(),
                    asAdmin, new AsyncCallback<Void>() {
                        Usuario cadastro = cadastros.get(indiceListaUsuarios);

                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert("Não foi possível " + (asAdmin ? "" : "des")
                                    + "marcar " + cadastro.getNome() + " como admin");
                        }

                        @Override
                        public void onSuccess(Void result) {
                            MaterialToast.fireToast(cadastro.getNome() + (asAdmin ? " é agora um admin" :
                                    " não é mais um admin"), "rounded");
                        }
                    });
        }
    }
}
