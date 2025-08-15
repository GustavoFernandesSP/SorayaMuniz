package com.Afya.AfyaBack.Repository;

import com.Afya.AfyaBack.Entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
