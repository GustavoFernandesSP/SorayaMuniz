package com.Afya.AfyaBack.Repository;

import com.Afya.AfyaBack.Entity.Carrinho;
import com.Afya.AfyaBack.Entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {

    Optional<Carrinho> findByUsuario(Usuarios usuario);
}
