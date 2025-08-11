package com.Afya.AfyaBack.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Usuarios usuarios;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CarrinhoItem> items;


}
