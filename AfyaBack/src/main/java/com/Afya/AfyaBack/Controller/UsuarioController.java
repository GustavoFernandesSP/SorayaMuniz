package com.Afya.AfyaBack.Controller;

import com.Afya.AfyaBack.DTO.LoginRequestDTO;
import com.Afya.AfyaBack.DTO.TokenResponse;
import com.Afya.AfyaBack.Entity.Usuarios;
import com.Afya.AfyaBack.Enum.Role;
import com.Afya.AfyaBack.Repository.UsuarioRepository;
import com.Afya.AfyaBack.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/public/user")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    // Metodo para Registrar um novo usuário
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuarios usuario) {

        // Verifica se o email já existe no banco de dados
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            // Se o email já existe, retorna um erro com uma mensagem explicativa
            return ResponseEntity.badRequest().body("Já existe um usuário com esse email!");
        }

        // Se o email não existir, define a senha e a role padrão
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario.setRole(Role.USER);  // Define o valor padrão para a role como 'USER'

        // Salva o novo usuário no banco de dados
        usuarioRepository.save(usuario);

        // Retorna uma resposta de sucesso
        return ResponseEntity.ok("Usuário cadastrado com sucesso!");
    }


    // Metodo que retorna todos os users.
    @GetMapping()
    public List<Usuarios> listarUsuariosComEnderecos() {
        return usuarioRepository.findAll();
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {

        //Pega mediante uma DTO o Email e senha para realizar o Login
        String email = loginRequest.getEmail();
        String senha = loginRequest.getSenha();

        // Verifica se existe o email registrado no banco.
        Optional<Usuarios> optionalUsuario = usuarioRepository.findByEmail(email);
        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email não existe.");
        }

        // Verifica a senha se é a mesma que existe.
        Usuarios usuario = optionalUsuario.get();
        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha inválida.");
        }

        // ✅ Gerar token JWT com email e role
        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRole().name());

        // ✅ Retornar o token como resposta
        return ResponseEntity.ok(new TokenResponse(token));
    }


    // Metodo que visualiza o Role do user.
    @GetMapping("/check-role")
    public String checkRole(@RequestHeader("Authorization") String token) {
        // Remove o "Bearer " do início do token
        String jwtToken = token.substring(7);

        // Verifica se o usuário é admin
        if (jwtUtil.isAdmin(jwtToken)) {
            return "Você é um ADMIN!";
        } else if (jwtUtil.isUser(jwtToken)) {
            return "Você é um USER!";
        } else {
            return "Role não encontrada!";
        }
    }




}
