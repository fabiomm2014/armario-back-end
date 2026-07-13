package com.armario.adapter.inbound.controller;

import com.armario.adapter.inbound.dto.CadastroRequest;
import com.armario.adapter.inbound.dto.CadastroResponse;
import com.armario.adapter.inbound.dto.LoginRequest;
import com.armario.adapter.inbound.dto.LoginResponse;
import com.armario.adapter.inbound.mapper.UsuarioDtoMapper;
import com.armario.domain.model.Usuario;
import com.armario.domain.port.inbound.AutenticarUsuarioUseCase;
import com.armario.domain.port.inbound.CadastrarUsuarioUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/auth", "/"})
public class AuthController {

    private final CadastrarUsuarioUseCase cadastrarUseCase;
    private final AutenticarUsuarioUseCase autenticarUseCase;
    private final UsuarioDtoMapper mapper;

    public AuthController(CadastrarUsuarioUseCase cadastrarUseCase,
                          AutenticarUsuarioUseCase autenticarUseCase,
                          UsuarioDtoMapper mapper) {
        this.cadastrarUseCase = cadastrarUseCase;
        this.autenticarUseCase = autenticarUseCase;
        this.mapper = mapper;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<CadastroResponse> cadastro(@RequestBody CadastroRequest request) {
        Usuario usuario = cadastrarUseCase.cadastrar(request.login(), request.senha());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(usuario));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = autenticarUseCase.autenticar(request.login(), request.senha());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
