package com.Afya.AfyaBack.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CarrinhoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Carrinho carrinho;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Produto produto;

    private Integer quantidade;

    // Getters e Setters
}
