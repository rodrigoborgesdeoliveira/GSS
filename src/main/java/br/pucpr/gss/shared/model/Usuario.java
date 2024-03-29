package br.pucpr.gss.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Usuario implements IsSerializable {

    private int id;
    private String nome;
    private String senha;
    private boolean isAdmin;
    private int idFuncionario;

    public Usuario(int id, String nome, String senha, boolean isAdmin, int idFuncionario) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.isAdmin = isAdmin;
        this.idFuncionario = idFuncionario;
    }

    public Usuario(String nome, String senha, boolean isAdmin, int idFuncionario) {
        this.nome = nome;
        this.senha = senha;
        this.isAdmin = isAdmin;
        this.idFuncionario = idFuncionario;
    }

    public Usuario(int id, String nome, boolean isAdmin, int idFuncionario) {
        this.id = id;
        this.nome = nome;
        this.isAdmin = isAdmin;
        this.idFuncionario = idFuncionario;
    }

    public Usuario(String nome, int idFuncionario) {
        this.nome = nome;
        this.idFuncionario = idFuncionario;
    }

    /**
     * Construtor para o serializable.
     */
    public Usuario() {
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    @Override
    public boolean equals(Object obj) {
        // Sobrescrever esse método para ver dois usuários com o mesmo id de funcionário como sendo o mesmo
        return obj instanceof Usuario && this.idFuncionario == ((Usuario) obj).idFuncionario;
    }

    @Override
    public String toString() {
        return "id: " + id + ", nome: " + nome + ", isAdmin: " + isAdmin + ", idFuncionario: " + idFuncionario;
    }
}
