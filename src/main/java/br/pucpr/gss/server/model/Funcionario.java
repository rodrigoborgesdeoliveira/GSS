package br.pucpr.gss.server.model;

public class Funcionario {
    private int id;
    private String email;
    private String nome;
    private int setorId;

    public Funcionario(int id, String email, String nome, int setorId) {
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.setorId = setorId;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public int getSetorId() {
        return setorId;
    }
}
