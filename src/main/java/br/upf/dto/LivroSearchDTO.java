package br.upf.dto;

import java.io.Serializable;

/**
 * DTO para encapsular parâmetros de busca avançada de livros.
 * Usado tanto pela API REST quanto pela interface JSF.
 */
public class LivroSearchDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    // Filtro por título do livro (busca parcial, case-insensitive)
    private String titulo;

    // Filtro por nome do autor (busca parcial, case-insensitive)
    private String nomeAutor;

    // Filtro por disponibilidade (true = disponível, false = emprestado, null = todos)
    private Boolean disponivel;

    // Filtro por número mínimo de páginas
    private Integer paginasMin;

    // Filtro por número máximo de páginas
    private Integer paginasMax;

    // Filtro por ano de publicação (exato)
    private Integer anoPublicacao;

    // ========== CONSTRUTORES ==========

    /**
     * Construtor padrão (necessário para JSF e frameworks de serialização)
     */
    public LivroSearchDTO() {
    }

    /**
     * Construtor completo (útil para testes unitários ou instanciação rápida)
     */
    public LivroSearchDTO(String titulo, String nomeAutor, Boolean disponivel,
                          Integer paginasMin, Integer paginasMax, Integer anoPublicacao) {
        this.titulo = titulo;
        this.nomeAutor = nomeAutor;
        this.disponivel = disponivel;
        this.paginasMin = paginasMin;
        this.paginasMax = paginasMax;
        this.anoPublicacao = anoPublicacao;
    }

    // ========== GETTERS E SETTERS ==========

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }

    public Integer getPaginasMin() {
        return paginasMin;
    }

    public void setPaginasMin(Integer paginasMin) {
        this.paginasMin = paginasMin;
    }

    public Integer getPaginasMax() {
        return paginasMax;
    }

    public void setPaginasMax(Integer paginasMax) {
        this.paginasMax = paginasMax;
    }

    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Integer anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    // ========== MÉTODOS AUXILIARES ==========

    /**
     * Verifica se algum filtro foi preenchido.
     * Útil para decidir se deve executar uma query filtrada ou um findAll simples.
     */
    public boolean hasFilters() {
        return titulo != null ||
                nomeAutor != null ||
                disponivel != null ||
                paginasMin != null ||
                paginasMax != null ||
                anoPublicacao != null;
    }

    /**
     * Limpa todos os filtros, resetando o objeto.
     */
    public void clear() {
        this.titulo = null;
        this.nomeAutor = null;
        this.disponivel = null;
        this.paginasMin = null;
        this.paginasMax = null;
        this.anoPublicacao = null;
    }

    @Override
    public String toString() {
        return "LivroSearchDTO{" +
                "titulo='" + titulo + '\'' +
                ", nomeAutor='" + nomeAutor + '\'' +
                ", disponivel=" + disponivel +
                ", paginasMin=" + paginasMin +
                ", paginasMax=" + paginasMax +
                ", anoPublicacao=" + anoPublicacao +
                '}';
    }
}