package com.projeto.libraryapi.controller.mappers;

import com.projeto.libraryapi.controller.dto.UsuarioDTO;
import com.projeto.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);

}
