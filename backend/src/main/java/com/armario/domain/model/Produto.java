package com.armario.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public class Produto {

    private UUID id;
    private String nome;
    private Double peso;
    private String tipo;
    private LocalDate dataFabricacao;
    private LocalDate dataValidade;

    public Produto() {
    }

    public Produto(UUID id, String nome, Double peso, String tipo,
                   LocalDate dataFabricacao, LocalDate dataValidade) {
        this.id = id;
        this.nome = nome;
        this.peso = peso;
        this.tipo = tipo;
        this.dataFabricacao = dataFabricacao;
        this.dataValidade = dataValidade;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public LocalDate getDataFabricacao() { return dataFabricacao; }
    public void setDataFabricacao(LocalDate dataFabricacao) { this.dataFabricacao = dataFabricacao; }

    public LocalDate getDataValidade() { return dataValidade; }
    public void setDataValidade(LocalDate dataValidade) { this.dataValidade = dataValidade; }
}
