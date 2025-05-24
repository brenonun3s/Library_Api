package com.projeto.libraryapi.controller.mappers;

import com.projeto.libraryapi.controller.dto.CadastroLivroDTO;
import com.projeto.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.projeto.libraryapi.model.Livro;
import com.projeto.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {AutorMapper.class})
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    //Expressão que serve para chamar uma classe ou método de uma classe
    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);

}
