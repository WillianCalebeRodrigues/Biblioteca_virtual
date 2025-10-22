package br.upf.repository;

import br.upf.entity.Emprestimo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class EmprestimoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Retorna todos os empréstimos com livro e autor carregados
    public List<Emprestimo> findAll() {
        return entityManager.createQuery(
                "SELECT e FROM Emprestimo e " +
                "LEFT JOIN FETCH e.livro l " +
                "LEFT JOIN FETCH l.autor", Emprestimo.class)
                .getResultList();
    }

    // Retorna todos os empréstimos ativos (não devolvidos) com livro carregado
    public List<Emprestimo> findAtivos() {
        return entityManager.createQuery(
                "SELECT e FROM Emprestimo e " +
                "LEFT JOIN FETCH e.livro l " +
                "WHERE e.dataDevolucao IS NULL", Emprestimo.class)
                .getResultList();
    }

    // Retorna a contagem total de empréstimos
    public Long count() {
        return entityManager.createQuery(
                "SELECT COUNT(e) FROM Emprestimo e", Long.class)
                .getSingleResult();
    }

    // Retorna a contagem de empréstimos ativos (não devolvidos)
    public Long countAtivos() {
        return entityManager.createQuery(
                "SELECT COUNT(e) FROM Emprestimo e WHERE e.dataDevolucao IS NULL", Long.class)
                .getSingleResult();
    }

    // NOVO: Necessário para a regra de negócio no service
    // Conta empréstimos ativos para um livro específico
    public Long countAtivosByLivroId(Long livroId) {
        return entityManager.createQuery(
                        "SELECT COUNT(e) FROM Emprestimo e WHERE e.livro.id = :livroId AND e.dataDevolucao IS NULL", Long.class)
                .setParameter("livroId", livroId)
                .getSingleResult();
    }
}