package com.projeto.libraryapi.controller.dto;

import com.projeto.libraryapi.model.GeneroLivro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResultadoPesquisaLivroDTO (
        UUID id,
        String isbn,
        String titulo,
        LocalDate dataPublicaco,
        GeneroLivro genero,
        BigDecimal preco,
        AutorDTO autor
){
}
