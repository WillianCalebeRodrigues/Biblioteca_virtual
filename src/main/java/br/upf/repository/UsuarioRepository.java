package br.upf.repository;

import br.upf.entity.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class UsuarioRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Encontra um usuário pelo seu nome de usuário (username).
     * Este método é crucial para obter o 'nomeCompleto' e 'email' do
     * usuário logado, que são necessários para registrar um empréstimo.
     * * @param username O username do usuário.
     * @return A entidade Usuario ou null se não for encontrado.
     */
    public Usuario findByUsername(String username) {
        try {
            return entityManager.createQuery(
                    "SELECT u FROM Usuario u WHERE u.username = :username", Usuario.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}