package com.projeto.libraryapi.controller;


import com.projeto.libraryapi.controller.dto.AutorDTO;
import com.projeto.libraryapi.controller.mappers.AutorMapper;
import com.projeto.libraryapi.model.Autor;
import com.projeto.libraryapi.model.Usuario;
import com.projeto.libraryapi.security.SecurityService;
import com.projeto.libraryapi.service.AutorService;
import com.projeto.libraryapi.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
@Tag(name = "Autores")
@Slf4j
public class AutorController implements GenericController {

    private final AutorService service;
    private final AutorMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Salvar", description = "Cadastrar novo autor")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso!"),
            @ApiResponse(responseCode = "422", description = "Erro de validação!"),
            @ApiResponse(responseCode = "409", description = "Autor já cadastrado!")})
    //@RequestMapping(method = RequestMethod.POST) -> Pode fazer dessa forma também
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO dto) {
        log.info("Cadastrando um novo autor: {}", dto.nome());
        Autor autor = mapper.toEntity(dto);
        service.salvar(autor);

        // Método retornara o id na URI ao enviar na API
        // http://localhost:8080/autores/numerodoID

        URI location = gerarHeaderLocation(autor.getId());
        return ResponseEntity.created(location).build();

    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Obter Detalhes", description = "Retorna os detalhes do autor e o ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor encontrado!"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado!")})
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);

        return service
                .obterPorId(idAutor)
                .map(autor -> {
                    AutorDTO dto = mapper.toDto(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping({"{id}"})
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Deletar", description = "Deleta um autor existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deletado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado!"),
            @ApiResponse(responseCode = "400", description = "Autor contém livros cadastrados!")})
    public ResponseEntity<Void> deletar(@PathVariable("id") String id) {
        log.info("Deletando o autor de ID: {} ", id);

        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deletar(autorOptional.get());
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Pesquisar", description = "Pesquisar autores por parâmetros")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso!")})
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {



        List<Autor> resultado = service.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> lista = resultado.stream().map(mapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);

    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Atualizar", description = "Atualizar um autor existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Atualizado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado!"),
            @ApiResponse(responseCode = "409", description = "Autor já cadastrado!")})
    public ResponseEntity<Void> atualizar(@PathVariable("id") String id, @RequestBody @Valid AutorDTO autorDTO) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var autor = autorOptional.get();
        autor.setNome(autorDTO.nome());
        autor.setNacionalidade(autorDTO.nacionalidade());
        autor.setDataNascimento(autorDTO.dataNascimento());

        service.atualizar(autor);

        return ResponseEntity.noContent().build();

    }
}
