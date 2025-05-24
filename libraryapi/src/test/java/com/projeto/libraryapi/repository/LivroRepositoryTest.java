package com.projeto.libraryapi.repository;

import com.projeto.libraryapi.model.Autor;
import com.projeto.libraryapi.model.GeneroLivro;
import com.projeto.libraryapi.model.Livro;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("90887-6554");
        livro.setTitulo("Livro Test");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = new Autor();
        autor.setNome("Joana");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1950, 01, 31));

        autorRepository.save(autor);

        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test // METODO DE SALVAR USANDO CASCADE
    void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("90887-6554");
        livro.setTitulo("Livro Dois");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = new Autor();
        autor.setNome("Joana");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1950, 01, 31));

        livro.setAutor(autor);


        livroRepository.save(livro);
    }

    @Test
    void atualizarAutorLivroTest(){
        UUID id = UUID.fromString("554ebfce-dc05-43d5-a8ff-da99381d11b5");
        var livroParaAtualizar = livroRepository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("30c3efd7-a8ab-4053-b723-8e158a8a228b");
        Autor maria = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setAutor(maria);
        livroRepository.save(livroParaAtualizar);
    }

    @Test
    void deletar(){
        UUID id = UUID.fromString("554ebfce-dc05-43d5-a8ff-da99381d11b5");
        livroRepository.deleteById(id);
    }

    @Test
    @Transactional
    void buscarLivroTest(){
        UUID id = UUID.fromString("94735b7e-3eda-418e-b103-1e33d7dfdc38");        Livro livro = livroRepository.findById(id).orElse(null);
        System.out.println("Livro: ");
        System.out.println(livro.getTitulo());

        System.out.println("Autor: ");
        System.out.println(livro.getAutor().getNome());

    }


}