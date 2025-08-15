package com.Afya.AfyaBack.Controller;

import com.Afya.AfyaBack.DTO.CarrinhoItemRequestDTO;
import com.Afya.AfyaBack.Entity.Carrinho;
import com.Afya.AfyaBack.Entity.CarrinhoItem;
import com.Afya.AfyaBack.Entity.Produto;
import com.Afya.AfyaBack.Entity.Usuarios;
import com.Afya.AfyaBack.Repository.CarrinhoRepository;
import com.Afya.AfyaBack.Repository.ProdutoRepository;
import com.Afya.AfyaBack.Repository.UsuarioRepository;
import com.Afya.AfyaBack.Security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    private CarrinhoItem carrinhoItem;

    private ProdutoRepository produtoRepository;

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

        // 2 Extrair e-mail do token
        String email = jwtUtil.extractEmail(token);

        // 3 Busca o usuário mediante o email dele.
        Usuarios usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // 4 Buscar ou criar carrinho
        Carrinho carrinho = carrinhoRepository.findByUsuario(usuario)
                .orElseGet(() -> {
                    Carrinho novoCarrinho = new Carrinho();
                    novoCarrinho.setUsuarios(usuario);
                    return carrinhoRepository.save(novoCarrinho);
                });

        // 5 Busca o produto para ver se ele existe.
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));


        CarrinhoItem item = new CarrinhoItem(); // 6 Cria um novo carrinhoItem
        item.setProduto(produto);               // Seta o Produto no carrinhoItem
        item.setQuantidade(quantidade);         // Seta a quantidade informada
        item.setCarrinho(carrinho);             // Seta o ID do carrinho que ele faz parte

        carrinho.getItems().add(item);          // Pega o objeto carrinho que foi buscado ou criado na linha 51 e addiciona o objeto carrinhoItem no carrinho.
        carrinhoRepository.save(carrinho);      // Salva o carrinho Editado.

        return ResponseEntity.ok(carrinho);

    }



}
