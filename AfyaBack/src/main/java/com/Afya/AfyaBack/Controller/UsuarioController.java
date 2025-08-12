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
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuarios usuario) {

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            return ResponseEntity.badRequest().body("Já existe um usuário com esse email!");
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario.setRole(Role.USER);  // Define o valor padrão para a role como 'USER'

        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Usuário cadastrado com sucesso!");
    }

    @GetMapping()
    public List<Usuarios> listarUsuariosComEnderecos() {
        return usuarioRepository.findAll();
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {

        String email = loginRequest.getEmail();
        String senha = loginRequest.getSenha();

        Optional<Usuarios> optionalUsuario = usuarioRepository.findByEmail(email);
        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email não existe.");
        }

        Usuarios usuario = optionalUsuario.get();
        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha inválida.");
        }

        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRole().name());

        return ResponseEntity.ok(new TokenResponse(token));
    }


    @GetMapping("/check-role")
    public String checkRole(@RequestHeader("Authorization") String token) {

        String jwtToken = token.substring(7);

        if (jwtUtil.isAdmin(jwtToken)) {
            return "Você é um ADMIN!";
        } else if (jwtUtil.isUser(jwtToken)) {
            return "Você é um USER!";
        } else {
            return "Role não encontrada!";
        }
    }
}
