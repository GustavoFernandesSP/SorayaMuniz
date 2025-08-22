package com.Afya.AfyaBack.Controller;

import com.Afya.AfyaBack.DTO.CarrinhoItemRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class OrdemController {

    @PostMapping("/adicionarOredem")
    public ResponseEntity<?> AddOrdem (@RequestBody CarrinhoItemRequestDTO requestDto, HttpServletRequest request ){

    }

}
