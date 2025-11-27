package br.upf.controller;

import br.upf.entity.Autor;
import br.upf.entity.Emprestimo;
import br.upf.entity.Livro;
import br.upf.entity.Usuario;
import br.upf.repository.UsuarioRepository;
import br.upf.service.BibliotecaService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import io.quarkus.elytron.security.common.BcryptUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("bibliotecaBean")
@ViewScoped
public class BibliotecaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private BibliotecaService bibliotecaService;

    @Inject
    private SecurityBean securityBean;

    @Inject
    private UsuarioRepository usuarioRepository;

    // Listas
    private List<Autor> autores = new ArrayList<>();
    private List<Livro> livros = new ArrayList<>();
    private List<Emprestimo> emprestimosAtivos = new ArrayList<>();

    // Estatísticas
    private long totalLivros;
    private long livrosDisponiveisCount;
    private long emprestimosAtivosCount;
    private long totalAutores;

    @PostConstruct
    public void init() {
        // --- ADICIONE ESTA LINHA PARA VER O HASH NO TERMINAL ---
        System.out.println(">>> HASH GERADO PARA senha123: " + BcryptUtil.bcryptHash("senha123"));

        carregarDados();
        // ... resto do código
    }

    public void carregarDados() {
        try {
            autores = bibliotecaService.listarTodosAutores();
            livros = bibliotecaService.listarTodosLivros();
            emprestimosAtivos = bibliotecaService.listarEmprestimosAtivos();
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao Carregar", "Não foi possível carregar os dados.");
        }
    }

    public void carregarEstatisticas() {
        try {
            totalLivros = bibliotecaService.contarTotalLivros();
            livrosDisponiveisCount = bibliotecaService.contarLivrosDisponisponiveis();
            emprestimosAtivosCount = bibliotecaService.contarEmprestimosAtivos();
            totalAutores = bibliotecaService.contarTotalAutores();
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao Carregar", "Não foi possível carregar as estatísticas.");
        }
    }

    public void recarregarDados() {
        init();
    }

    // --- MÉTODOS DE AÇÃO ---

    /**
     * Realiza o empréstimo de um livro para o usuário logado (Perfil LEITOR).
     */
    public void emprestarLivro(Long livroId) {
        // 1. Verificação de Segurança
        if (!securityBean.isLoggedIn() || !securityBean.isUsuario()) {
            addMessage(FacesMessage.SEVERITY_WARN, "Acesso Negado", "Apenas usuários leitores podem realizar empréstimos.");
            return;
        }

        try {
            // 2. Obter dados do usuário logado
            String username = securityBean.getUsername();
            Usuario usuario = usuarioRepository.findByUsername(username);

            if (usuario == null) {
                throw new RuntimeException("Não foi possível localizar os dados do usuário logado.");
            }

            // 3. Chamar o serviço de negócio
            bibliotecaService.realizarEmprestimo(livroId, usuario.getNomeCompleto(), usuario.getEmail());

            // 4. Atualizar a tela
            recarregarDados();
            addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Livro emprestado com sucesso! Previsão de devolução em 14 dias.");

        } catch (RuntimeException e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage());
        }
    }

    public void excluirAutor(Long autorId) {
        try {
            bibliotecaService.excluirAutor(autorId);
            recarregarDados();
            addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Autor excluído com sucesso.");
        } catch (RuntimeException e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage());
        }
    }

    public void excluirLivro(Long livroId) {
        try {
            bibliotecaService.excluirLivro(livroId);
            recarregarDados();
            addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Livro excluído com sucesso.");
        } catch (RuntimeException e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage());
        }
    }

    // --- MÉTODO AUXILIAR PARA MENSAGENS ---

    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(severity, summary, detail));
    }

    // --- GETTERS ---

    public List<Autor> getAutores() {
        return autores;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public List<Emprestimo> getEmprestimosAtivos() {
        return emprestimosAtivos;
    }

    public long getTotalLivros() {
        return totalLivros;
    }

    public long getLivrosDisponiveis() {
        return livrosDisponiveisCount;
    }

    public long getEmprestimosAtivosCount() {
        return emprestimosAtivosCount;
    }

    public long getTotalAutores() {
        return totalAutores;
    }
}