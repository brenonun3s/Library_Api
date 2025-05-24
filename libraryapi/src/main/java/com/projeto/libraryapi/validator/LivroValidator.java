package com.projeto.libraryapi.validator;

import com.projeto.libraryapi.exceptions.CampoInvalidadoException;
import com.projeto.libraryapi.exceptions.RegistroDuplicadoException;
import com.projeto.libraryapi.model.Livro;
import com.projeto.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private static final int ANO_EXIGENCIA_PRECO = 2020;

    private final LivroRepository livroRepository;

    public void validar(Livro livro) {
        if(existeLivroComIsbn(livro)) {
            throw new RegistroDuplicadoException("ISBN ja cadastrado!");
        }
        if(isPrecoObrigatorioNulo(livro)){
            throw new CampoInvalidadoException("preco", "Para livros com ano de publicação a aprtir de 2020, o preço é obrigatório!");
        }
    }

    private boolean isPrecoObrigatorioNulo(Livro livro) {
        return livro.getPreco() == null && livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }

    private boolean existeLivroComIsbn(Livro livro){
        Optional<Livro>livroEncontrado = livroRepository.findByIsbn(livro.getIsbn());
        if(livro.getId() == null){
            return livroEncontrado.isPresent();
        }

        return livroEncontrado
                .map(Livro::getId)
                .stream()
                .anyMatch(id -> !id.equals(livro.getId()));
    }
}
