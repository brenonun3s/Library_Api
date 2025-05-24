package com.projeto.libraryapi.service;

import com.projeto.libraryapi.model.GeneroLivro;
import com.projeto.libraryapi.model.Livro;
import com.projeto.libraryapi.model.Usuario;
import com.projeto.libraryapi.repository.LivroRepository;
import com.projeto.libraryapi.repository.specs.LivroSpecs;
import com.projeto.libraryapi.security.SecurityService;
import com.projeto.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.projeto.libraryapi.repository.specs.LivroSpecs.anoPublicacaoEqual;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroValidator validator;
    private final SecurityService securityService;

    public Livro salvar(Livro livro) {
        validator.validar(livro);
        Usuario usuario = securityService.obterUsuarioLogado();
        livro.setUsuario(usuario);
        return livroRepository.save(livro);
    }


    public Optional<Livro> obterPorId(UUID id) {
        return livroRepository.findById(id);
    }

    public void deletar(Livro livro) {
        livroRepository.delete(livro);
    }

    //ISBN, NOME AUTOR, GENERO, ANO PUBLICAÇÃO
    public Page<Livro> pesquisa(
            String isbn,
            String titulo,
            String nomeAutor,
            GeneroLivro genero,
            Integer anoPublicacao,
            Integer pagina,
            Integer tamanhoPagina) {

        // SELECT * FROM LIVRO WHERE ISBN = :ISBN AND NOMEAUTOR

        /*
        Specification<Livro> specs = Specification
                .where(LivroSpecs.isbnEqual(isbn))
                .and(LivroSpecs.tituloLike(titulo)
                        .and(LivroSpecs.generoEqual(genero)));
                        */

        // WHERE ISBN = :ISBN
        // ROOT É A PROJEÇÃO DA QUERY
        //Specification<Livro> isbnEqual = (root, query, cb) -> cb.equal(root.get("isbn"), isbn);

        Specification<Livro> specs = Specification.where((root, query, cb) -> cb.conjunction());
        if (isbn != null) {
            specs = specs.and(LivroSpecs.isbnEqual(isbn));
        }
        if (titulo != null) {
            specs = specs.and(LivroSpecs.isbnEqual(titulo));
        }
        if (genero != null) {
            specs = specs.and(LivroSpecs.generoEqual(genero));
        }

        if (anoPublicacao != null){
            specs = specs.and(anoPublicacaoEqual(anoPublicacao));
        }

        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);

        return livroRepository.findAll(specs, pageRequest);
    }

    public void atualizar(Livro livro) {
        if(livro.getId() == null){
            throw new IllegalArgumentException("Para atualizar, é necessário que o Livro já esteja salvo na base!");
        }
        validator.validar(livro);
        livroRepository.save(livro);
    }


}
