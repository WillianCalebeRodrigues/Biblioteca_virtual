package br.upf.controller;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("securityBean")
@RequestScoped
public class SecurityBean {

    @Inject
    SecurityIdentity securityIdentity;

    public boolean isLoggedIn() {
        return securityIdentity != null && !securityIdentity.isAnonymous();
    }

    public String getUsername() {
        return isLoggedIn() ? securityIdentity.getPrincipal().getName() : "Anônimo";
    }

    // Perfil de Administrador (Acesso total)
    public boolean isAdmin() {
        return isLoggedIn() && securityIdentity.hasRole("ADMIN");
    }

    // Perfil de Bibliotecário (Gerenciamento de acervo)
    public boolean isBibliotecario() {
        return isLoggedIn() && securityIdentity.hasRole("BIBLIOTECARIO");
    }

    // NOVO: Perfil de Usuário Leitor (Acesso a empréstimos)
    public boolean isUsuario() {
        return isLoggedIn() && securityIdentity.hasRole("USUARIO");
    }

    // Verifica se possui uma role específica (genérico)
    public boolean hasRole(String role) {
        return isLoggedIn() && securityIdentity.hasRole(role);
    }

    public String logout() {
        // Invalida a sessão HTTP atual para garantir segurança
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        // Redireciona para a página de logout (que fará o refresh para o login)
        return "/logout?faces-redirect=true";
    }
}