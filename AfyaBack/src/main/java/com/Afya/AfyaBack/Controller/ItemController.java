package com.Afya.AfyaBack.Controller;

import com.Afya.AfyaBack.DTO.CarrinhoItemRequestDTO;
import com.Afya.AfyaBack.DTO.ItemDTO;
import com.Afya.AfyaBack.DTO.ItemDTOResponse;
import com.Afya.AfyaBack.Entity.Item;
import com.Afya.AfyaBack.Service.ItemService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public List<Item> listarItens() {
        return itemService.listarTodos(); // retorna os itens com a URL da imagem já incluída
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        return itemService.getItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Item criarItem(@ModelAttribute ItemDTO dto) throws IOException {
        return itemService.salvarItem(dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }


}
