package br.upf.controller;

import br.upf.entity.Autor;
import br.upf.entity.Emprestimo;
import br.upf.entity.Livro;
import br.upf.service.BibliotecaService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("bibliotecaBean")
@ViewScoped
public class BibliotecaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private BibliotecaService bibliotecaService;

    // Listas e contadores
    private List<Autor> autores = new ArrayList<>();
    private List<Livro> livros = new ArrayList<>();
    private List<Emprestimo> emprestimosAtivos = new ArrayList<>();

    private long totalLivros;
    private long livrosDisponisponiveis; 
    private long emprestimosAtivosCount;
    private long totalAutores;

    @PostConstruct
    public void init() {
        carregarDados();
        carregarEstatisticas();
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
            
            livrosDisponisponiveis = bibliotecaService.contarLivrosDisponisponiveis();
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

    // --- MÉTODOS DE EXCLUSÃO ---

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
        // A página chama 'livrosDisponiveis' 
        // O getter retorna a variável 'livrosDisponisponiveis' 
        // Isso faz a "ponte" e resolve o erro.
        return livrosDisponisponiveis;
    }

    public long getEmprestimosAtivosCount() {
        return emprestimosAtivosCount;
    }

    public long getTotalAutores() {
        return totalAutores;
    }
}
