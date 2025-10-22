package br.upf.repository;

import br.upf.entity.Autor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class AutorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Retorna todos os autores
    public List<Autor> findAll() {
        return entityManager.createQuery("SELECT a FROM Autor a", Autor.class)
                            .getResultList();
    }

    // Retorna a contagem total de autores
    public Long count() {
        return entityManager.createQuery("SELECT COUNT(a) FROM Autor a", Long.class)
                            .getSingleResult();
    }

    // Encontra um autor pelo ID
    public Autor findById(Long id) {
        return entityManager.find(Autor.class, id);
    }

    // Deleta um autor (deve ser chamado dentro de uma transação)
    public void delete(Autor autor) {
        if (entityManager.contains(autor)) {
            entityManager.remove(autor);
        } else {
            // Se o objeto não estiver gerenciado, faz o merge antes de remover
            entityManager.remove(entityManager.merge(autor));
        }
    }
}