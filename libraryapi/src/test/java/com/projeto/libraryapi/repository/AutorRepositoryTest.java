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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest() {
        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1950, 01, 31));

        var autorSalvo = repository.save(autor);
        System.out.println("Autor salvo com sucesso!" + autorSalvo);

    }

    @Test
    public void atualizarTest() {
        var id = UUID.fromString("c8aba5bb-315d-4b0c-b542-307bff8c4578");

        Optional<Autor> possivelAutor = repository.findById(id);

        if (possivelAutor.isPresent()){
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do Autor:");
            System.out.println(autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(1960, 01, 31));

            repository.save(autorEncontrado);

        }
    }

    @Test
    public void listarTest(){
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem de autores" + repository.count());
    }

    @Test
    public void deletarPorIdTest(){
        var id = UUID.fromString("c8aba5bb-315d-4b0c-b542-307bff8c4578");
        repository.deleteById(id);

    }

    @Test
    void deletarPorObjTest(){
        var id = UUID.fromString("c8aba5bb-315d-4b0c-b542-307bff8c4578");
        var maria = repository.findById(id).get();
        repository.delete(maria);
    }

    @Test
    void salvarAutorTest(){
        Autor autor = new Autor();
        autor.setNome("Breno");
        autor.setNacionalidade("Francês");
        autor.setDataNascimento(LocalDate.of(1950, 01, 26));

        Livro livro = new Livro();
        livro.setIsbn("2058-5478");
        livro.setTitulo("O susto");
        livro.setPreco(BigDecimal.valueOf(150));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setDataPublicacao(LocalDate.of(1970, 1, 2));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("2058-8888");
        livro2.setTitulo("O misterio");
        livro2.setPreco(BigDecimal.valueOf(250));
        livro2.setGenero(GeneroLivro.CIENCIA);
        livro2.setDataPublicacao(LocalDate.of(2000, 12, 2));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);

        livroRepository.saveAll(autor.getLivros());
    }

    @Test
    @Transactional
    void listarLivrosPorAutor(){
        var id = UUID.fromString("ae1f82a0-2855-47aa-bf1a-8f9bf673b48e");
        var autor = repository.findById(id).get();

        List<Livro> livrosLista = livroRepository.findByAutor(autor);
        autor.setLivros(livrosLista);

        autor.getLivros().forEach(System.out::println);
    }

    @Test
    void pesquisarPorTituloTest(){
        List<Livro> lista = livroRepository.findByTitulo("O misterio");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisarPorTituloEPrecoTest(){
        var preco = BigDecimal.valueOf(100.00);
        List<Livro> lista = livroRepository.findByTituloAndPreco("Livro Test", preco );
        lista.forEach(System.out::println);
    }
    
    



}

