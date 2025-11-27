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


    // Conta empréstimos ativos para um livro específico
    public Long countAtivosByLivroId(Long livroId) {
        return entityManager.createQuery(
                "SELECT COUNT(e) FROM Emprestimo e WHERE e.livro.id = :livroId AND e.dataDevolucao IS NULL", Long.class)
                .setParameter("livroId", livroId)
                .getSingleResult();
    }

    /**
     * Persiste ou atualiza uma entidade Emprestimo. (Novo método)
     * @param emprestimo A entidade Emprestimo a ser salva.
     * @return A entidade Emprestimo gerenciada.
     */
    public Emprestimo save(Emprestimo emprestimo) {
        if (emprestimo.getId() == null) {
            entityManager.persist(emprestimo);
            return emprestimo;
        } else {
            return entityManager.merge(emprestimo);
        }
    }
}