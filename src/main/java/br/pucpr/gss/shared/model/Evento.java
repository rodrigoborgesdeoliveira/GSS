package br.pucpr.gss.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.Date;

public class Evento implements IsSerializable {

    private int id;
    private String nome;
    private Date dataOcorrencia;
    private int idSolicitacao;
    private int idUsuario;

    public Evento() {
    }

    public Evento(int id, String nome, Date dataOcorrencia, int idSolicitacao, int idUsuario) {
        this.id = id;
        this.nome = nome;
        this.dataOcorrencia = dataOcorrencia;
        this.idSolicitacao = idSolicitacao;
        this.idUsuario = idUsuario;
    }

    public Evento(String nome, Date dataOcorrencia, int idSolicitacao, int idUsuario) {
        this.nome = nome;
        this.dataOcorrencia = dataOcorrencia;
        this.idSolicitacao = idSolicitacao;
        this.idUsuario = idUsuario;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Date getDataOcorrencia() {
        return dataOcorrencia;
    }

    public int getIdSolicitacao() {
        return idSolicitacao;
    }

    public int getIdUsuario() {
        return idUsuario;
    }
}
