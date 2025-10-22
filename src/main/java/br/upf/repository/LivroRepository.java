package br.upf.repository;

import br.upf.entity.Livro;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class LivroRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Retorna todos os livros, buscando também o autor para evitar lazy loading
    public List<Livro> findAll() {
        return entityManager.createQuery("SELECT l FROM Livro l LEFT JOIN FETCH l.autor", Livro.class)
                            .getResultList();
    }

    // Retorna a contagem total de livros
    public Long count() {
        return entityManager.createQuery("SELECT COUNT(l) FROM Livro l", Long.class)
                            .getSingleResult();
    }

    // Retorna a contagem de livros por status de disponibilidade
    public Long countByDisponivel(boolean disponivel) {
        return entityManager.createQuery("SELECT COUNT(l) FROM Livro l WHERE l.disponivel = :disponivel", Long.class)
                            .setParameter("disponivel", disponivel)
                            .getSingleResult();
    }
    // Encontra um livro pelo ID
    public Livro findById(Long id) {
        // Usamos JOIN FETCH para já trazer o autor e evitar LazyException
        try {
            return entityManager.createQuery(
                            "SELECT l FROM Livro l LEFT JOIN FETCH l.autor WHERE l.id = :id", Livro.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null; // Retorna nulo se não encontrar
        }
    }

    // Deleta um livro (deve ser chamado dentro de uma transação)
    public void delete(Livro livro) {
        if (entityManager.contains(livro)) {
            entityManager.remove(livro);
        } else {
            entityManager.remove(entityManager.merge(livro));
        }
    }

    // NOVO: Necessário para a regra de negócio no service
    // Conta quantos livros um autor específico possui
    public Long countByAutorId(Long autorId) {
        return entityManager.createQuery(
                        "SELECT COUNT(l) FROM Livro l WHERE l.autor.id = :autorId", Long.class)
                .setParameter("autorId", autorId)
                .getSingleResult();
    }
}