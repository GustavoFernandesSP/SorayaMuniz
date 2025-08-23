package com.Afya.AfyaBack.Service;

import com.Afya.AfyaBack.DTO.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.Afya.AfyaBack.Entity.Item;
import com.Afya.AfyaBack.Repository.ItemRepository;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Value("${upload.dir}")
    private String uploadDir; // pasta onde será salvo

    public Item salvarItem(ItemDTO dto) throws IOException {
        // 1. Salvar a imagem na pasta
        MultipartFile imagem = dto.getImagem();
        String nomeArquivo = System.currentTimeMillis() + "_" + imagem.getOriginalFilename();
        Path caminho = Paths.get(uploadDir + nomeArquivo);
        Files.createDirectories(caminho.getParent());
        imagem.transferTo(caminho.toFile());

        // 2. Criar a URL da imagem (exemplo básico)
        String url = "http://localhost:8080/" + uploadDir + nomeArquivo;

        // 3. Criar entidade Item
        Item item = new Item();
        item.setNome(dto.getNome());
        item.setDescricao(dto.getDescricao());
        item.setPreco(dto.getPreco());
        item.setEstoque(dto.getEstoque());
        item.setCategoria(dto.getCategoria());
        item.setImagemUrl(url);

        // 4. Salvar no banco
        return itemRepository.save(item);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

}
