package com.Afya.AfyaBack.DTO;

import com.Afya.AfyaBack.Entity.Item;

public class ItemDTOResponse {
    private String nome;
    private String descricao;
    private Double preco;
    private Integer estoque;
    private String categoria;
    private String imagemUrl; // URL da imagem para o front

    // Construtor
    public ItemDTOResponse(String nome, String descricao, Double preco, Integer estoque, String categoria, String imagemUrl) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.categoria = categoria;
        this.imagemUrl = imagemUrl;
    }

    // Getters e setters
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public Double getPreco() { return preco; }
    public Integer getEstoque() { return estoque; }
    public String getCategoria() { return categoria; }
    public String getImagemUrl() { return imagemUrl; }
}
