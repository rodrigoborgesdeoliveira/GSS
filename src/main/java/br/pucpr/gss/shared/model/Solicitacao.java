package br.pucpr.gss.shared.model;

import br.pucpr.gss.shared.model.estado.Estado;
import br.pucpr.gss.shared.model.prioridade.Prioridade;
import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.Date;

public class Solicitacao implements IsSerializable {

    private int id;
    private String titulo;
    private String descricao;
    private Prioridade prioridade;
    private Estado estado;
    private Date dataCriacao;
    private Date prazo;
    private String descricaoSolucao;
    private int idSetor;
    private int idSolicitante;
    private int idAtendente = -1; // Inicialmente a solicitação não foi delegada a nenhum atendenete ainda
    private int idGestor;

    /**
     * Construtor para o serializable.
     */
    public Solicitacao() {
    }

    public Solicitacao(int id, String titulo, String descricao, Prioridade prioridade, Estado estado, Date dataCriacao,
                       Date prazo, String descricaoSolucao, int idSetor, int idSolicitante, int idAtendente, int idGestor) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.estado = estado;
        this.dataCriacao = dataCriacao;
        this.prazo = prazo;
        this.descricaoSolucao = descricaoSolucao;
        this.idSetor = idSetor;
        this.idSolicitante = idSolicitante;
        this.idAtendente = idAtendente;
        this.idGestor = idGestor;
    }

    public Solicitacao(String titulo, String descricao, Date dataCriacao, int idSetor, int idSolicitante, int idGestor) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.idSetor = idSetor;
        this.idSolicitante = idSolicitante;
        this.idGestor = idGestor;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public Estado getEstado() {
        return estado;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public Date getPrazo() {
        return prazo;
    }

    public String getDescricaoSolucao() {
        return descricaoSolucao;
    }

    public int getIdSetor() {
        return idSetor;
    }

    public int getIdSolicitante() {
        return idSolicitante;
    }

    public int getIdAtendente() {
        return idAtendente;
    }

    public int getIdGestor() {
        return idGestor;
    }
}
