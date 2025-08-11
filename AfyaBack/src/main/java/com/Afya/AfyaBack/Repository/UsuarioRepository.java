package com.Afya.AfyaBack.Repository;

import com.Afya.AfyaBack.Entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {
    Optional<Usuarios> findByEmail(String email);

    boolean existsByEmail(String email);
}
