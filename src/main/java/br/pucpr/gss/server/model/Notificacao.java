package br.pucpr.gss.server.model;

public class Notificacao {

    private String remetente;
    private String destinatario;
    private String assunto;
    private String descricao;

    public Notificacao(String remetente, String destinatario, String assunto, String descricao) {
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.assunto = assunto;
        this.descricao = descricao;
    }

    public String getRemetente() {
        return remetente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public String getAssunto() {
        return assunto;
    }

    public String getDescricao() {
        return descricao;
    }
}
