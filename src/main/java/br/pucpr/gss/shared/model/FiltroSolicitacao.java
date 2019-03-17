package br.pucpr.gss.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FiltroSolicitacao implements IsSerializable {

    private Usuario usuario;
    private List<Solicitacao> listaOriginalSolicitacoes;
    private String filtroTitulo;
    private Date filtroDataInicial, filtroDataFinal;
    private boolean showSolicitante, showAtendente, showGestor;

    /**
     * Construtor para o serializable.
     */
    public FiltroSolicitacao() {
    }

    public FiltroSolicitacao(Usuario usuario, List<Solicitacao> listaOriginalSolicitacoes) {
        this.usuario = usuario;
        this.listaOriginalSolicitacoes = listaOriginalSolicitacoes;

        showSolicitante = showAtendente = showGestor = true;
    }

    public void setFiltroTitulo(String filtroTitulo) {
        this.filtroTitulo = filtroTitulo;
    }

    public void setFiltroDataInicial(Date filtroDataInicial) {
        this.filtroDataInicial = filtroDataInicial;
    }

    public void setFiltroDataFinal(Date filtroDataFinal) {
        this.filtroDataFinal = filtroDataFinal;
    }

    public void setShowSolicitante(boolean showSolicitante) {
        this.showSolicitante = showSolicitante;
    }

    public void setShowAtendente(boolean showAtendente) {
        this.showAtendente = showAtendente;
    }

    public void setShowGestor(boolean showGestor) {
        this.showGestor = showGestor;
    }

    public List<Solicitacao> filtrar() {
        List<Solicitacao> solicitacoesFiltradas = new ArrayList<>();

        for (Solicitacao solicitacao : listaOriginalSolicitacoes) {
            boolean isSolicitante = false, isAtendente = false, isGestor = false;

            if (usuario.getIdFuncionario() == solicitacao.getIdSolicitante()) {
                // Solicitante
                isSolicitante = true;
            } else if (usuario.getIdFuncionario() == solicitacao.getIdAtendente()) {
                // Atendente
                isAtendente = true;
            } else {
                // Gestor
                isGestor = true;
            }

            // Não incluir o papel se ele foi filtrado (showPapel = false)
            if ((isSolicitante && !showSolicitante) ||
                    (isAtendente && !showAtendente) ||
                    (isGestor && !showGestor)) {
                continue;
            }


            solicitacoesFiltradas.add(solicitacao);
        }

        return solicitacoesFiltradas;
    }
}
