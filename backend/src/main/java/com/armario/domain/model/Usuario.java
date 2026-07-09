package com.armario.domain.model;

import java.util.UUID;

public class Usuario {

    private UUID id;
    private String login;
    private String senha;

    public Usuario() {
    }

    public Usuario(UUID id, String login, String senha) {
        this.id = id;
        this.login = login;
        this.senha = senha;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
