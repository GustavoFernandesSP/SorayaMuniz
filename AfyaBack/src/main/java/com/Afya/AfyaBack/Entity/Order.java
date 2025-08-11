package com.Afya.AfyaBack.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Order {

    public enum Status {
        PENDENTE,
        PAGO,
        CANCELADO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id")
    private Usuarios UsuarioID;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    private Double total;


}
