package br.pucpr.gss.client.view;

import com.google.gwt.user.client.ui.Widget;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Date;

public interface DetalhesSolicitacaoView {

    interface Presenter {
        void onCancelarButtonClicked();

        /**
         * Salva as alterações da solicitação.
         *
         * @param prazo            Prazo atual ou nulo, caso não seja necessário alterá-lo.
         * @param indiceSetor      Índice do setor na lista ou -1, caso não seja necessário alterá-lo.
         * @param indicePrioridade Índice da prioridade na lista ou -1, caso não seja necessário alterá-lo.
         * @param indiceAtendente  Índice do atendente na lista ou -1, caso não seja necessário alterá-lo.
         */
        void onSalvarButtonClicked(@Nullable Date prazo, int indiceSetor, int indicePrioridade, int indiceAtendente);

        void onIniciarAtendimentoClicked();

        void onPausarAtendimentoClicked();

        void onContinuarAtendimentoClicked();

        String getOnRequisitarInformacoesAdicionaisClickedToken();

        void onRequisitarInformacoesAdicionaisClicked();

        String getOnRegistrarInformacoesAdicionaisClickedToken();

        void onRegistrarInformacoesAdicionaisClicked();
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

    /**
     * Define a visibilidade da opção de iniciar um atendimento.
     *
     * @param visivel true, se visível, false, caso contrário.
     */
    void setVisibilidadeIniciarAtendimento(boolean visivel);

    /**
     * Define a visibilidade da opção de pausar um atendimento.
     *
     * @param visivel true, se visível, false, caso contrário.
     */
    void setVisibilidadePausarAtendimento(boolean visivel);

    /**
     * Define a visibilidade da opção de continuar um atendimento.
     *
     * @param visivel true, se visível, false, caso contrário.
     */
    void setVisibilidadeContinuarAtendimento(boolean visivel);

    /**
     * Define a visibilidade da opção de requisitar informações adicionais.
     *
     * @param visivel true, se visível, false, caso contrário.
     */
    void setVisibilidadeRequisitarInformacoesAdicionais(boolean visivel);

    /**
     * Define a visibilidade da opção de registrar informações adicionais.
     *
     * @param visivel true, se visível, false, caso contrário.
     */
    void setVisibilidadeRegistrarInformacoesAdicionais(boolean visivel);
}
