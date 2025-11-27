package br.upf.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit; // Import adicionado

@Entity
@Table(name = "emprestimos")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nomeUsuario;

    @Column(nullable = false, length = 150)
    private String emailUsuario;

    @Column(nullable = false)
    private LocalDate dataEmprestimo;

    @Column(nullable = false)
    private LocalDate dataDevolucaoPrevista;

    private LocalDate dataDevolucao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;


    public Emprestimo() {
    }

    // Construtor com nomeUsuario, emailUsuario e livro (Melhorado)
    public Emprestimo(String nomeUsuario, String emailUsuario, Livro livro) {
        this.nomeUsuario = nomeUsuario;
        this.emailUsuario = emailUsuario;
        this.livro = livro;
        // Define as datas do empréstimo automaticamente
        this.dataEmprestimo = LocalDate.now();
        this.dataDevolucaoPrevista = LocalDate.now().plus(14, ChronoUnit.DAYS); // Previsão de 14 dias
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    // Método de negócio: verifica se o empréstimo está ativo (não devolvido)
    public boolean isAtivo() {
        return this.dataDevolucao == null;
    }
}