package com.Afya.AfyaBack.DTO;

import com.Afya.AfyaBack.Entity.Item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
public class ItemDTO {

    private Long id;
    private Double preco;
    private String nome;
    private String descricao;
    private Integer estoque;
    private Item.Categoria categoria;

    // Arquivo enviado pelo usuário
    @NotNull
    private MultipartFile imagem;

    // Getters e Setters
    // (ou use Lombok @Getter @Setter)
}
