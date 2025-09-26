package com.Afya.AfyaBack.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
public class Item {

    public enum Categoria {
        MASCULINO,
        FEMININO,
        UNISSEX;
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

    @ElementCollection
    @NotNull
    private List<String> imagens;

}
