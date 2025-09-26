package com.Afya.AfyaBack.Controller;

import com.Afya.AfyaBack.DTO.CarrinhoItemRequestDTO;
import com.Afya.AfyaBack.Entity.Carrinho;
import com.Afya.AfyaBack.Entity.Item;
import com.Afya.AfyaBack.Entity.Usuario;
import com.Afya.AfyaBack.Repository.CarrinhoRepository;
import com.Afya.AfyaBack.Repository.ItemRepository;
import com.Afya.AfyaBack.Repository.UsuarioRepository;
import com.Afya.AfyaBack.Security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    private Item Item;

    private ItemRepository itemRepository;

    private UsuarioRepository usuarioRepository;

    private CarrinhoRepository carrinhoRepository;

    private JwtUtil jwtUtil;

    @PostMapping("/AdicionarItem")
    public ResponseEntity<?> addItemAoCarrinhoDoUsuario(@RequestBody CarrinhoItemRequestDTO requestDto, HttpServletRequest request )  {

        Long produtoId = requestDto.getProdutoId();
        int quantidade = requestDto.getQuantidade();

        // 1 Pegar o token do header
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token não fornecido ou inválido");
        }
        String token = authorizationHeader.substring(7);

        boolean StringVerificarBarrer = jwtUtil.isTokenValid(token);
        if (StringVerificarBarrer){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }

        // 2 Extrair e-mail do token
        String email = jwtUtil.extractEmail(token);

        // 3 Busca o usuário mediante o email dele.
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // 4 Buscar ou criar carrinho
        Carrinho carrinho = carrinhoRepository.findByUsuario(usuario)
                .orElseGet(() -> {
                    Carrinho novoCarrinho = new Carrinho();
                    novoCarrinho.setUsuario(usuario);
                    return carrinhoRepository.save(novoCarrinho);
                });

// 6 - Buscar item
        Item Item = itemRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        carrinho.getItems().add(Item);
        carrinhoRepository.save(carrinho);

        return ResponseEntity.ok(carrinho);

    }


    @GetMapping("/buscarCarrinho")
    public ResponseEntity<?> getCarrinho(HttpServletRequest request){

        // 1 Pegar o token do header
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token não fornecido ou inválido");
        }
        String token = authorizationHeader.substring(7);

        boolean StringVerificarBarrer = jwtUtil.isTokenValid(token);
        if (StringVerificarBarrer){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }

        // 2 Extrair e-mail do token
        String email = jwtUtil.extractEmail(token);

        // 3 Busca o usuário mediante o email dele.
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Carrinho carrinho = carrinhoRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Carrinho Vazio"));

        return ResponseEntity.ok(carrinho);

    }



}
