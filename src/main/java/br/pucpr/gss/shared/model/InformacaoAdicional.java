package br.pucpr.gss.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import org.jetbrains.annotations.NotNull;

public class InformacaoAdicional implements IsSerializable {

    private int id;
    private String descricao;
    private String resposta;
    private int idSolicitacao;

    /**
     * Construtor para o serializable.
     */
    public InformacaoAdicional() {
    }

    public InformacaoAdicional(int id, @NotNull String descricao, String resposta, int idSolicitacao) {
        this.id = id;
        this.descricao = descricao;
        this.resposta = resposta;
        this.idSolicitacao = idSolicitacao;
    }

    public InformacaoAdicional(@NotNull String descricao, String resposta, int idSolicitacao) {
        this.descricao = descricao;
        this.resposta = resposta;
        this.idSolicitacao = idSolicitacao;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public int getIdSolicitacao() {
        return idSolicitacao;
    }

    public boolean isDescricaoValida() {
        return descricao != null && !descricao.isEmpty();
    }
}
