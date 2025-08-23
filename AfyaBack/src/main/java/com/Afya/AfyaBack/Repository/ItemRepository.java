package com.Afya.AfyaBack.Repository;

import com.Afya.AfyaBack.Entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
