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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    // Pasta de uploads (desenvolvimento)
    private static final String uploadDir =
            Paths.get("src/main/resources/static/uploads").toAbsolutePath().toString();

    public Item salvarItem(ItemDTO dto) throws IOException {
        // Primeiro cria o item sem imagens para gerar o ID
        Item item = new Item();
        item.setNome(dto.getNome());
        item.setDescricao(dto.getDescricao());
        item.setPreco(dto.getPreco());
        item.setEstoque(dto.getEstoque());
        item.setCategoria(dto.getCategoria());

        // Salva inicialmente para gerar o ID
        item = itemRepository.save(item);

        MultipartFile[] imagens = dto.getImagens();
        List<String> urls = new ArrayList<>();

        // Caminho externo fixo para uploads
        String uploadDir = "E:/GFVirtus/00.Clientes/Soraya-Afya/uploads";

        for (int i = 0; i < imagens.length; i++) {
            MultipartFile imagem = imagens[i];
            if (imagem.isEmpty()) continue;

            // Nome do arquivo = ID do item + índice
            String nomeArquivo = item.getId() + "_" + i + ".jpg"; // ou .png
            Path caminho = Paths.get(uploadDir).resolve(nomeArquivo);

            // Cria diretório se não existir
            Files.createDirectories(caminho.getParent());

            // Sobrescreve a imagem se já existir
            imagem.transferTo(caminho.toFile());

            // URL para front
            String url = "/uploads/" + nomeArquivo;
            urls.add(url);
        }

        // Atualiza o item com as URLs das imagens
        item.setImagens(urls);

        return itemRepository.save(item);
    }



    public List<Item> listarTodos() {
        return itemRepository.findAll();
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
