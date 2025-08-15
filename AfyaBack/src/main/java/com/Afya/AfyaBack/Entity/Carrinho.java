package com.Afya.AfyaBack.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Usuarios usuarios;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL)
    private List<CarrinhoItem> items;


}
