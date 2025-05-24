package com.projeto.libraryapi.controller;

import com.projeto.libraryapi.controller.dto.UsuarioDTO;
import com.projeto.libraryapi.controller.mappers.UsuarioMapper;
import com.projeto.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping
    public void salvar(@RequestBody UsuarioDTO dto) {
        var usuario = usuarioMapper.toEntity(dto);
        usuarioService.salvar(usuario);
    }
}