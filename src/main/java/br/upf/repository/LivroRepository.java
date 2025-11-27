package br.upf.repository;

import br.upf.entity.Livro;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import br.upf.dto.LivroSearchDTO;
import java.util.Map;
import java.util.HashMap;

@ApplicationScoped
public class LivroRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // ===================================
    // MÉTODOS DE PERSISTÊNCIA (NOVOS)
    // ===================================

    /**
     * Persiste um novo livro no banco de dados.
     * @param livro O livro a ser salvo.
     * @return O livro persistido (com ID gerado).
     */
    public Livro save(Livro livro) {
        entityManager.persist(livro);
        return livro;
    }

    /**
     * Atualiza um livro existente no banco de dados.
     * @param livro O livro a ser atualizado.
     * @return O livro atualizado (objeto gerenciado).
     */
    public Livro update(Livro livro) {
        return entityManager.merge(livro);
    }

    // ===================================
    // MÉTODOS DE LEITURA E BUSCA
    // ===================================

    // Retorna todos os livros, buscando também o autor para evitar lazy loading
    public List<Livro> findAll() {
        return entityManager.createQuery("SELECT l FROM Livro l LEFT JOIN FETCH l.autor", Livro.class)
                .getResultList();
    }

    // Encontra um livro pelo ID
    public Livro findById(Long id) {
        // Usei JOIN FETCH para já trazer o autor e evitar LazyException
        try {
            return entityManager.createQuery(
                    "SELECT l FROM Livro l LEFT JOIN FETCH l.autor WHERE l.id = :id", Livro.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null; // Retorna nulo se não encontrar
        }
    }

    /**
     * Busca livros com filtros avançados baseados no DTO.
     */
    public List<Livro> findByFiltros(LivroSearchDTO dto) {
        // String de consulta base (já com o FETCH para otimizar)
        StringBuilder queryStr = new StringBuilder("SELECT l FROM Livro l LEFT JOIN FETCH l.autor a WHERE 1=1 ");

        // Parâmetros para a consulta
        Map<String, Object> params = new HashMap<>();

        // Adiciona filtros dinamicamente
        if (dto.getTitulo() != null && !dto.getTitulo().isBlank()) {
            queryStr.append("AND upper(l.titulo) LIKE upper(:titulo) ");
            params.put("titulo", "%" + dto.getTitulo() + "%");
        }

        if (dto.getAnoPublicacao() != null) {
            // Usa FUNCTION para extrair o ano da coluna LocalDate
            queryStr.append("AND FUNCTION('YEAR', l.dataPublicacao) = :ano ");
            params.put("ano", dto.getAnoPublicacao());
        }

        if (dto.getNomeAutor() != null && !dto.getNomeAutor().isBlank()) {
            // Usa o alias 'a' que definimos no LEFT JOIN FETCH
            queryStr.append("AND upper(a.nome) LIKE upper(:nomeAutor) ");
            params.put("nomeAutor", "%" + dto.getNomeAutor() + "%");
        }

        if (dto.getDisponivel() != null) {
            queryStr.append("AND l.disponivel = :disponivel ");
            params.put("disponivel", dto.getDisponivel());
        }

        if (dto.getPaginasMin() != null) {
            queryStr.append("AND l.numeroPaginas >= :paginasMin ");
            params.put("paginasMin", dto.getPaginasMin());
        }

        if (dto.getPaginasMax() != null) {
            queryStr.append("AND l.numeroPaginas <= :paginasMax ");
            params.put("paginasMax", dto.getPaginasMax());
        }

        // 1. Cria a query JPA
        jakarta.persistence.TypedQuery<Livro> jpaQuery =
                entityManager.createQuery(queryStr.toString(), Livro.class);

        // 2. Define os parâmetros
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            jpaQuery.setParameter(entry.getKey(), entry.getValue());
        }


        return jpaQuery.getResultList();
    }

    // ===================================
    // MÉTODOS DE CONTAGEM E EXCLUSÃO
    // ===================================

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

    // Conta quantos livros um autor específico possui
    public Long countByAutorId(Long autorId) {
        return entityManager.createQuery(
                "SELECT COUNT(l) FROM Livro l WHERE l.autor.id = :autorId", Long.class)
                .setParameter("autorId", autorId)
                .getSingleResult();
    }

    // Deleta um livro (deve ser chamado dentro de uma transação)
    public void delete(Livro livro) {
        if (entityManager.contains(livro)) {
            entityManager.remove(livro);
        } else {
            entityManager.remove(entityManager.merge(livro));
        }
    }
}