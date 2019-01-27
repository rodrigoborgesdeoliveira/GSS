package br.pucpr.gss.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UsuarioLogin implements IsSerializable {
    private String email;
    private String senha;

    public UsuarioLogin() {
    }

    public UsuarioLogin(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    /**
     * Email não pode:
     * - Ser nulo.
     * - Ser vazio.
     * - Possuir um formato inválido (formato válido é exemplo@exemplo.com - não necessariamente precisa ser .com).
     *
     * @return true, se válido, e false, caso contrário.
     */
    public boolean isEmailValido() {
        return email != null && !email.isEmpty()
                // Valida formato
                && email.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    }

    /**
     * Senha não pode:
     * - Ser nula.
     * - Ser vazia.
     * - Possuir menos de 8 caracteres.
     * @return true, se válido, e false, caso contrário.
     */
    public boolean isSenhaValida() {
        return senha != null && !(senha.length() < 8);
    }
}
