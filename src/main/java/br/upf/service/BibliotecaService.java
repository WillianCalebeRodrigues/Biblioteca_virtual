package br.upf.service;

import br.upf.entity.Autor;
import br.upf.entity.Emprestimo;
import br.upf.entity.Livro;
import br.upf.repository.AutorRepository;
import br.upf.repository.EmprestimoRepository;
import br.upf.repository.LivroRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import br.upf.dto.LivroSearchDTO;


@ApplicationScoped
public class BibliotecaService {

    @Inject
    private AutorRepository autorRepository;

    @Inject
    private LivroRepository livroRepository;

    @Inject
    private EmprestimoRepository emprestimoRepository;

    // --- MÉTODOS DE LEITURA E CONSULTA ---

    public List<Autor> listarTodosAutores() {
        return autorRepository.findAll();
    }

    public List<Livro> listarTodosLivros() {
        return livroRepository.findAll();
    }

    public List<Emprestimo> listarEmprestimosAtivos() {
        return emprestimoRepository.findAtivos();
    }

    public Livro buscarLivroPorId(Long id) {
        return livroRepository.findById(id);
    }

    public List<Livro> buscarLivrosComFiltros(LivroSearchDTO searchDTO) {
        // A lógica de busca complexa é delegada ao repositório.
        return livroRepository.findByFiltros(searchDTO);
    }


    // --- MÉTODOS DE CONTAGEM ---

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


    // --- MÉTODOS DE NEGÓCIO TRANSAÇÃOIS ---

    /**
     * Salva ou atualiza um livro.
     * @param livro O livro a ser persistido.
     * @return O livro persistido.
     */
    @Transactional
    public Livro salvarLivro(Livro livro) {
        if (livro.getId() == null) {
            return livroRepository.save(livro);
        } else {
            return livroRepository.update(livro);
        }
    }


    /**
     * Exclui um autor.
     * Regra de Negócio: Não permite excluir um autor se ele possuir livros cadastrados.
     * @param id O ID do autor a ser excluído.
     */
    @Transactional
    public void excluirAutor(Long id) {
        // 1. Verifica a regra de negócio
        Long livrosDoAutor = livroRepository.countByAutorId(id);
        if (livrosDoAutor > 0) {
            throw new RuntimeException("Não é possível excluir o autor, pois ele possui " + livrosDoAutor + " livro(s) associado(s).");
        }

        // 2. Busca e deleta o autor
        Autor autor = autorRepository.findById(id);
        if (autor != null) {
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
        // 1. Verifica a regra de negócio
        Long emprestimosAtivos = emprestimoRepository.countAtivosByLivroId(id);
        if (emprestimosAtivos > 0) {
            throw new RuntimeException("Não é possível excluir o livro, pois ele está em " + emprestimosAtivos + " empréstimo(s) ativo(s).");
        }

        // 2. Busca e deleta o livro
        Livro livro = livroRepository.findById(id);
        if (livro != null) {
            livroRepository.delete(livro);
        } else {
            throw new RuntimeException("Livro com ID " + id + " não encontrado.");
        }
    }

    /**
     * NOVO: Realiza o empréstimo de um livro para um usuário.
     * Regra de Negócio: O livro deve estar disponível.
     * @param livroId O ID do livro.
     * @param nomeUsuario O nome completo do usuário logado.
     * @param emailUsuario O email do usuário logado.
     * @return A entidade Emprestimo recém-criada.
     */
    @Transactional
    public Emprestimo realizarEmprestimo(Long livroId, String nomeUsuario, String emailUsuario) {
        Livro livro = livroRepository.findById(livroId);

        if (livro == null) {
            throw new RuntimeException("Livro com ID " + livroId + " não encontrado.");
        }

        if (!livro.getDisponivel()) {
            throw new RuntimeException("O livro '" + livro.getTitulo() + "' já está emprestado.");
        }

        // 1. Cria a entidade Emprestimo (o construtor seta as datas)
        Emprestimo novoEmprestimo = new Emprestimo(nomeUsuario, emailUsuario, livro);

        // 2. Atualiza o status do Livro (Entidade gerenciada, será sincronizado)
        livro.setDisponivel(false);

        // 3. Salva o empréstimo
        return emprestimoRepository.save(novoEmprestimo);
    }
}