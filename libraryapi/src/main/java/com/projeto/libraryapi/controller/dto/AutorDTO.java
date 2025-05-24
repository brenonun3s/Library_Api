package com.projeto.libraryapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

// Classe imutável
@Schema(name = "Autor")
public record AutorDTO(
        UUID id,
        @NotBlank(message = "Campo Obrigatório")
        @Size(max = 100, min = 2, message = "Campo fora do tamanho Padrão! Nome Minimo = 2, Máximo 100")
        String nome,
        @NotNull(message = "Campo Obrigatório")
        @Past(message = "Não pode ser data futura!")
        LocalDate dataNascimento,
        @NotBlank(message = "Campo Obrigatório")
        @Size(max = 50, min = 2, message = "Campo fora do tamanho Padrão! Nacionalidade Minimo = 2, Máximo 100")
        String nacionalidade) {

}
