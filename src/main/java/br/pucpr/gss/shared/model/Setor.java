package br.pucpr.gss.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Setor implements IsSerializable {

    private int id;
    private String nome;
    private int idGestor;

    /**
     * Construtor para o serializable.
     */
    public Setor() {
    }

    public Setor(int id, String nome, int idGestor) {
        this.id = id;
        this.nome = nome;
        this.idGestor = idGestor;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getIdGestor() {
        return idGestor;
    }

    @Override
    public String toString() {
        return "(id = " + id + ", nome = " + nome + ", idGestor = " + idGestor + ")";
    }

    @Override
    public boolean equals(Object obj) {
        // Sobrescrever esse método para ver dois setores com o mesmo id como sendo o mesmo
        return obj instanceof Setor && id == ((Setor) obj).id;
    }
}
