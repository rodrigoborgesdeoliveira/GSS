package br.pucpr.gss.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Resposta<T> implements IsSerializable {

    private String status;
    private String codigoErro;
    private T dados;

    public Resposta(String status, String codigoErro, T dados) {
        this.status = status;
        this.codigoErro = codigoErro;
        this.dados = dados;
    }

    public Resposta() {
    }

    public String getStatus() {
        return status;
    }

    public String getCodigoErro() {
        return codigoErro;
    }

    public T getDados() {
        return dados;
    }
}
