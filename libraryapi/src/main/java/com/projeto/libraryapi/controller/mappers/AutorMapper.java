package com.projeto.libraryapi.controller.mappers;

import com.projeto.libraryapi.controller.dto.AutorDTO;
import com.projeto.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") //transforma a classe em um componente para o spring no momento da compilação
public interface AutorMapper {

    // Por padrão, já deixa explicito, entretanto, caso queira mapear, é dessa forma
    // @Mapping(source = "nome", target = "nomeAutor")
    Autor toEntity(AutorDTO dto);

    AutorDTO toDto(Autor autor);


}
