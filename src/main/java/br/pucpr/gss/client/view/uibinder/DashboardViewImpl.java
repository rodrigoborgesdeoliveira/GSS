package br.pucpr.gss.client.view.uibinder;

import br.pucpr.gss.client.view.DashboardView;
import br.pucpr.gss.client.view.MenuView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.ui.MaterialLabel;

public class DashboardViewImpl extends Composite implements DashboardView {

    interface DashboardViewImplUiBinder extends UiBinder<Widget, DashboardViewImpl> {
    }

    private static DashboardViewImplUiBinder uiBinder = GWT.create(DashboardViewImplUiBinder.class);

    private Presenter presenter;

    @UiField
    MenuView menu;
    // Geral
    @UiField
    MaterialLabel quantidadeTotal, quantidadeAbertas, quantidadeAbertasEstadoAguardandoAtendimento,
            quantidadeAbertasEstadoEmAndamento, quantidadeAbertasEstadoPausada, quantidadeAbertasEstadoEncerramentoProposto,
            quantidadeAbertasEstadoEncerramentoRejeitado, quantidadeAbertasEstadoRespondida,
            quantidadeAbertasEstadoAguardandoInformacoesAdicionais, quantidadeEncerradas, quantidadeAbertasPrioridadeBaixa, quantidadeAbertasPrioridadeNormal,
            quantidadeAbertasPrioridadeAlta;
    // Solicitante
    @UiField
    MaterialLabel quantidadeSolicitadas, quantidadeSolicitadasEstadoAguardandoAtendimento,
            quantidadeSolicitadasEstadoEmAndamento, quantidadeSolicitadasEstadoPausada, quantidadeSolicitadasEstadoEncerramentoProposto,
            quantidadeSolicitadasEstadoEncerramentoRejeitado, quantidadeSolicitadasEstadoRespondida,
            quantidadeSolicitadasEstadoAguardandoInformacoesAdicionais, quantidadeSolicitadasEstadoEncerrada,
            quantidadeSolicitadasPrioridadeBaixa, quantidadeSolicitadasPrioridadeNormal, quantidadeSolicitadasPrioridadeAlta;
    // Atendente
    @UiField
    MaterialLabel quantidadeAtendidas, quantidadeAtendidasEstadoAguardandoAtendimento,
            quantidadeAtendidasEstadoEmAndamento, quantidadeAtendidasEstadoPausada, quantidadeAtendidasEstadoEncerramentoProposto,
            quantidadeAtendidasEstadoEncerramentoRejeitado, quantidadeAtendidasEstadoRespondida,
            quantidadeAtendidasEstadoAguardandoInformacoesAdicionais, quantidadeAtendidasEstadoEncerrada,
            quantidadeAtendidasPrioridadeBaixa, quantidadeAtendidasPrioridadeNormal, quantidadeAtendidasPrioridadeAlta;
    // Gestor
    @UiField
    MaterialLabel quantidadeGerenciadas, quantidadeGerenciadasEstadoAguardandoAtendimento,
            quantidadeGerenciadasEstadoEmAndamento, quantidadeGerenciadasEstadoPausada, quantidadeGerenciadasEstadoEncerramentoProposto,
            quantidadeGerenciadasEstadoEncerramentoRejeitado, quantidadeGerenciadasEstadoRespondida,
            quantidadeGerenciadasEstadoAguardandoInformacoesAdicionais, quantidadeGerenciadasEstadoEncerrada,
            quantidadeGerenciadasPrioridadeBaixa, quantidadeGerenciadasPrioridadeNormal, quantidadeGerenciadasPrioridadeAlta;

    public DashboardViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
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
    public void setIndicadores(int total, int abertas, int encerradas, int prioridadeBaixa, int prioridadeNormal,
                               int prioridadeAlta, int papelSolicitante, int papelAtendente, int papelGestor) {
        quantidadeTotal.setText(String.valueOf(total));
        quantidadeAbertas.setText(String.valueOf(abertas));
        quantidadeEncerradas.setText(String.valueOf(encerradas));
        quantidadeSolicitadas.setText(String.valueOf(papelSolicitante));
        quantidadeAtendidas.setText(String.valueOf(papelAtendente));
        quantidadeGerenciadas.setText(String.valueOf(papelGestor));
    }

    @Override
    public void setIndicadoresEstadoSolicitacoesAbertas(int estadoAguardandoAtendimento, int estadoEmAndamento,
                                                        int estadoPausada, int estadoEncerramentoProposto,
                                                        int estadoEncerramentoRejeitado, int estadoRespondida,
                                                        int estadoAguardandoInformacoesAdicionais) {
        quantidadeAbertasEstadoAguardandoAtendimento.setText("Aguardando atendimento: " + estadoAguardandoAtendimento);
        quantidadeAbertasEstadoEmAndamento.setText("Em andamento: " + estadoEmAndamento);
        quantidadeAbertasEstadoPausada.setText("Pausada: " + estadoPausada);
        quantidadeAbertasEstadoEncerramentoProposto.setText("Encerramento proposto: " + estadoEncerramentoProposto);
        quantidadeAbertasEstadoEncerramentoRejeitado.setText("Encerramento rejeitado: " + estadoEncerramentoRejeitado);
        quantidadeAbertasEstadoRespondida.setText("Respondida: " + estadoRespondida);
        quantidadeAbertasEstadoAguardandoInformacoesAdicionais.setText("Aguardando informações adicionais: " +
                estadoAguardandoInformacoesAdicionais);
    }

    @Override
    public void setIndicadoresPrioridadeSolicitacoesAbertas(int prioridadeBaixa, int prioridadeNormal,
                                                            int prioridadeAlta) {
        quantidadeAbertasPrioridadeBaixa.setText("Prioridade baixa: " + prioridadeBaixa);
        quantidadeAbertasPrioridadeNormal.setText("Prioridade normal: " + prioridadeNormal);
        quantidadeAbertasPrioridadeAlta.setText("Prioridade alta: " + prioridadeAlta);
    }

    @Override
    public void setIndicadoresEstadoSolicitacoesSolicitante(int estadoAguardandoAtendimento, int estadoEmAndamento,
                                                            int estadoPausada, int estadoEncerramentoProposto,
                                                            int estadoEncerramentoRejeitado, int estadoRespondida,
                                                            int estadoAguardandoInformacoesAdicionais,
                                                            int estadoEncerrada) {
        quantidadeSolicitadasEstadoAguardandoAtendimento.setText("Aguardando atendimento: " + estadoAguardandoAtendimento);
        quantidadeSolicitadasEstadoEmAndamento.setText("Em andamento: " + estadoEmAndamento);
        quantidadeSolicitadasEstadoPausada.setText("Pausada: " + estadoPausada);
        quantidadeSolicitadasEstadoEncerramentoProposto.setText("Encerramento proposto: " + estadoEncerramentoProposto);
        quantidadeSolicitadasEstadoEncerramentoRejeitado.setText("Encerramento rejeitado: " + estadoEncerramentoRejeitado);
        quantidadeSolicitadasEstadoRespondida.setText("Respondida: " + estadoRespondida);
        quantidadeSolicitadasEstadoAguardandoInformacoesAdicionais.setText("Aguardando informações adicionais: " +
                estadoAguardandoInformacoesAdicionais);
        quantidadeSolicitadasEstadoEncerrada.setText("Encerrada: " + estadoEncerrada);
    }

    @Override
    public void setIndicadoresPrioridadeSolicitante(int prioridadeBaixa, int prioridadeNormal, int prioridadeAlta) {
        quantidadeSolicitadasPrioridadeBaixa.setText("Prioridade baixa: " + prioridadeBaixa);
        quantidadeSolicitadasPrioridadeNormal.setText("Prioridade normal: " + prioridadeNormal);
        quantidadeSolicitadasPrioridadeAlta.setText("Prioridade alta: " + prioridadeAlta);
    }

    @Override
    public void setIndicadoresEstadoSolicitacoesAtendente(int estadoAguardandoAtendimento, int estadoEmAndamento,
                                                          int estadoPausada, int estadoEncerramentoProposto,
                                                          int estadoEncerramentoRejeitado, int estadoRespondida,
                                                          int estadoAguardandoInformacoesAdicionais,
                                                          int estadoEncerrada) {
        quantidadeAtendidasEstadoAguardandoAtendimento.setText("Aguardando atendimento: " + estadoAguardandoAtendimento);
        quantidadeAtendidasEstadoEmAndamento.setText("Em andamento: " + estadoEmAndamento);
        quantidadeAtendidasEstadoPausada.setText("Pausada: " + estadoPausada);
        quantidadeAtendidasEstadoEncerramentoProposto.setText("Encerramento proposto: " + estadoEncerramentoProposto);
        quantidadeAtendidasEstadoEncerramentoRejeitado.setText("Encerramento rejeitado: " + estadoEncerramentoRejeitado);
        quantidadeAtendidasEstadoRespondida.setText("Respondida: " + estadoRespondida);
        quantidadeAtendidasEstadoAguardandoInformacoesAdicionais.setText("Aguardando informações adicionais: " +
                estadoAguardandoInformacoesAdicionais);
        quantidadeAtendidasEstadoEncerrada.setText("Encerrada: " + estadoEncerrada);
    }

    @Override
    public void setIndicadoresPrioridadeAtendente(int prioridadeBaixa, int prioridadeNormal, int prioridadeAlta) {
        quantidadeAtendidasPrioridadeBaixa.setText("Prioridade baixa: " + prioridadeBaixa);
        quantidadeAtendidasPrioridadeNormal.setText("Prioridade normal: " + prioridadeNormal);
        quantidadeAtendidasPrioridadeAlta.setText("Prioridade alta: " + prioridadeAlta);
    }

    @Override
    public void setIndicadoresEstadoSolicitacoesGestor(int estadoAguardandoAtendimento, int estadoEmAndamento,
                                                       int estadoPausada, int estadoEncerramentoProposto,
                                                       int estadoEncerramentoRejeitado, int estadoRespondida,
                                                       int estadoAguardandoInformacoesAdicionais,
                                                       int estadoEncerrada) {
        quantidadeGerenciadasEstadoAguardandoAtendimento.setText("Aguardando atendimento: " + estadoAguardandoAtendimento);
        quantidadeGerenciadasEstadoEmAndamento.setText("Em andamento: " + estadoEmAndamento);
        quantidadeGerenciadasEstadoPausada.setText("Pausada: " + estadoPausada);
        quantidadeGerenciadasEstadoEncerramentoProposto.setText("Encerramento proposto: " + estadoEncerramentoProposto);
        quantidadeGerenciadasEstadoEncerramentoRejeitado.setText("Encerramento rejeitado: " + estadoEncerramentoRejeitado);
        quantidadeGerenciadasEstadoRespondida.setText("Respondida: " + estadoRespondida);
        quantidadeGerenciadasEstadoAguardandoInformacoesAdicionais.setText("Aguardando informações adicionais: " +
                estadoAguardandoInformacoesAdicionais);
        quantidadeGerenciadasEstadoEncerrada.setText("Encerrada: " + estadoEncerrada);
    }

    @Override
    public void setIndicadoresPrioridadeGestor(int prioridadeBaixa, int prioridadeNormal, int prioridadeAlta) {
        quantidadeGerenciadasPrioridadeBaixa.setText("Prioridade baixa: " + prioridadeBaixa);
        quantidadeGerenciadasPrioridadeNormal.setText("Prioridade normal: " + prioridadeNormal);
        quantidadeGerenciadasPrioridadeAlta.setText("Prioridade alta: " + prioridadeAlta);
    }
}