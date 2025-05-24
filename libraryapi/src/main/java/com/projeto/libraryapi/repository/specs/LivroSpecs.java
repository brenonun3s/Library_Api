package com.projeto.libraryapi.repository.specs;

import com.projeto.libraryapi.model.GeneroLivro;
import com.projeto.libraryapi.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    public static Specification<Livro> isbnEqual(String isbn) {
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> tituloLike(String titulo) {
        // UPPER(LIVRO.TITULO) LIKE (%:PARAM%)
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%");
    }

    public static Specification<Livro> generoEqual(GeneroLivro genero) {
        return (root, query, cb) -> cb.equal(root.get("genero"), genero);
    }

    public static Specification<Livro> anoPublicacaoEqual(Integer anoPublicaco) {
        return (root, query, cb) ->
                cb.equal(
                        cb.function("to_char", String.class, root.get("dataPublicacao"),
                                cb.literal("YYYY")),
                        anoPublicaco.toString());
    }

    //NAVEGAR NAS SPECIFICATIONS USANDO JOIN
    public static Specification<Livro> nomeAutorLike(String nome) {
        return (root, query, cb) -> {
            Join<Object, Object> joinAutor = root.join("autor", JoinType.INNER);
            return cb.like(cb.upper(joinAutor.get("nome")), "%" + nome.toUpperCase() + "%");

//            return cb.like( cb.upper(root.get("autor").get("nome")), "%" + nome + "%");
        };
    }
}
