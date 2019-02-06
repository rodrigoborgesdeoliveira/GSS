package br.pucpr.gss.client.view;

import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.Date;

public interface DetalhesSolicitacaoView {

    interface Presenter {

    }

    void setPresenter(Presenter presenter);

    Widget asWidget();

    MenuView getMenuView();

    void setAtendenteUI(String tituloSolicitacao, String descricao, String dataInicial, Date prazo, String estado,
                        int indicePrioridade, ArrayList<String> prioridades);

    void setSolicitanteUI(String tituloSolicitacao, String descricao, String dataInicial, String prazo, String setor,
                          String estado, int indicePrioridade, ArrayList<String> prioridades, String nomeAtendente);

    void setGestorUI(String tituloSolicitacao, String descricao, String dataInicial, String prazo, int indiceSetor,
                     ArrayList<String> setores, String estado, int indicePrioridade, ArrayList<String> prioridades,
                     int indiceAtendente, ArrayList<String> atendentes);
}
