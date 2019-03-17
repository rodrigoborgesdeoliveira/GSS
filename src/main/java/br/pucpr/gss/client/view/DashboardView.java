package br.pucpr.gss.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface DashboardView {

    interface Presenter {

    }

    void setPresenter(Presenter presenter);

    MenuView getMenuView();

    Widget asWidget();

    void setIndicadores(int total, int abertas, int encerradas, int prioridadeBaixa, int prioridadeNormal,
                        int prioridadeAlta, int papelSolicitante, int papelAtendente, int papelGestor);

    void setIndicadoresEstadoSolicitacoesAbertas(int estadoAguardandoAtendimento, int estadoEmAndamento, int estadoPausada,
                                                 int estadoEncerramentoProposto, int estadoEncerramentoRejeitado,
                                                 int estadoRespondida, int estadoAguardandoInformacoesAdicionais);

    void setIndicadoresPrioridadeSolicitacoesAbertas(int prioridadeBaixa, int prioridadeNormal, int prioridadeAlta);

    void setIndicadoresEstadoSolicitacoesSolicitante(int estadoAguardandoAtendimento, int estadoEmAndamento, int estadoPausada,
                                                     int estadoEncerramentoProposto, int estadoEncerramentoRejeitado,
                                                     int estadoRespondida, int estadoAguardandoInformacoesAdicionais,
                                                     int estadoEncerrada);

    void setIndicadoresPrioridadeSolicitante(int prioridadeBaixa, int prioridadeNormal, int prioridadeAlta);

    void setIndicadoresEstadoSolicitacoesAtendente(int estadoAguardandoAtendimento, int estadoEmAndamento, int estadoPausada,
                                                   int estadoEncerramentoProposto, int estadoEncerramentoRejeitado,
                                                   int estadoRespondida, int estadoAguardandoInformacoesAdicionais,
                                                   int estadoEncerrada);

    void setIndicadoresPrioridadeAtendente(int prioridadeBaixa, int prioridadeNormal, int prioridadeAlta);

    void setIndicadoresEstadoSolicitacoesGestor(int estadoAguardandoAtendimento, int estadoEmAndamento, int estadoPausada,
                                                int estadoEncerramentoProposto, int estadoEncerramentoRejeitado,
                                                int estadoRespondida, int estadoAguardandoInformacoesAdicionais,
                                                int estadoEncerrada);

    void setIndicadoresPrioridadeGestor(int prioridadeBaixa, int prioridadeNormal, int prioridadeAlta);
}
