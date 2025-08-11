package com.Afya.AfyaBack.Entity;

import jakarta.persistence.*;

@Entity
public class Produto {

    public enum Categoria {
        MASCULINO,
        FEMININO,
        UNISSEX
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_id;

    private String nome;
    private String descricao;
    private Double preco;
    private Integer estoque;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;


}
