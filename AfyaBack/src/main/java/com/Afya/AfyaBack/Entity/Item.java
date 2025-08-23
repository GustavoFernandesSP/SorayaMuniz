package com.Afya.AfyaBack.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Item {

    public enum Categoria {
        MASCULINO,
        FEMININO,
        UNISSEX
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Carrinho carrinho;

    private Double preco;

    private String nome;

    private String descricao;

    private Integer estoque;

    @Enumerated(EnumType.STRING)
    private Item.Categoria categoria;

    // Novo campo para armazenar URL ou caminho da imagem
    private String imagemUrl;

}
