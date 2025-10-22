package br.upf.service;

import br.upf.entity.*;
import br.upf.repository.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional; 
import java.util.List;

@ApplicationScoped
public class BibliotecaService {

    @Inject
    private AutorRepository autorRepository;

    @Inject
    private LivroRepository livroRepository;

    @Inject
    private EmprestimoRepository emprestimoRepository;

    // --- MÉTODOS DE LEITURA E CONSULTA (Existentes) ---

    public List<Autor> listarTodosAutores() {
        return autorRepository.findAll();
    }

    public List<Livro> listarTodosLivros() {
        return livroRepository.findAll();
    }

    public List<Emprestimo> listarEmprestimosAtivos() {
        return emprestimoRepository.findAtivos();
    }

    // --- MÉTODOS DE CONTAGEM (Existentes) ---

    public Long contarTotalLivros() {
        return livroRepository.count();
    }

    public Long contarLivrosDisponisponiveis() { 
        return livroRepository.countByDisponivel(true);
    }

    public Long contarEmprestimosAtivos() {
        return emprestimoRepository.countAtivos();
    }

    public Long contarTotalAutores() {
        return autorRepository.count();
    }

    

    /**
     * Exclui um autor.
     * Regra de Negócio: Não permite excluir um autor se ele possuir livros cadastrados.
     * @param id O ID do autor a ser excluído.
     */
    @Transactional
    public void excluirAutor(Long id) {
        // 1. Verifica a regra de negócio (depende do método novo no LivroRepository)
        Long livrosDoAutor = livroRepository.countByAutorId(id);
        if (livrosDoAutor > 0) {
            // Lança uma exceção que pode ser tratada pela sua camada de API/Controller
            throw new RuntimeException("Não é possível excluir o autor, pois ele possui " + livrosDoAutor + " livro(s) associado(s).");
        }

        // 2. Busca o autor (depende do método novo no AutorRepository)
        Autor autor = autorRepository.findById(id);
        if (autor != null) {
            // 3. Deleta o autor (depende do método novo no AutorRepository)
            autorRepository.delete(autor);
        } else {
            throw new RuntimeException("Autor com ID " + id + " não encontrado.");
        }
    }

    /**
     * Exclui um livro.
     * Regra de Negócio: Não permite excluir um livro se ele estiver em um empréstimo ativo.
     * @param id O ID do livro a ser excluído.
     */
    @Transactional
    public void excluirLivro(Long id) {
        // 1. Verifica a regra de negócio (depende do método novo no EmprestimoRepository)
        Long emprestimosAtivos = emprestimoRepository.countAtivosByLivroId(id);
        if (emprestimosAtivos > 0) {
            throw new RuntimeException("Não é possível excluir o livro, pois ele está em " + emprestimosAtivos + " empréstimo(s) ativo(s).");
        }

        // 2. Busca o livro (depende do método novo no LivroRepository)
        Livro livro = livroRepository.findById(id);
        if (livro != null) {
            // 3. Deleta o livro (depende do método novo no LivroRepository)
            livroRepository.delete(livro);
        } else {
            throw new RuntimeException("Livro com ID " + id + " não encontrado.");
        }
    }
}
