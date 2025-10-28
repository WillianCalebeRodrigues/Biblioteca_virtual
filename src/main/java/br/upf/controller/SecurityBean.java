package br.upf.controller;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.RequestScoped;
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

    public boolean isAdmin() {
        return isLoggedIn() && securityIdentity.hasRole("ADMIN");
    }

    public boolean isBibliotecario() {
        return isLoggedIn() && securityIdentity.hasRole("BIBLIOTECARIO");
    }

    public boolean isUsuario() {
        return isLoggedIn() && securityIdentity.hasRole("USUARIO");
    }

    public boolean hasRole(String role) {
        return isLoggedIn() && securityIdentity.hasRole(role);
    }

    public String logout() {
        // Redireciona para logout
        return "/logout?faces-redirect=true";
    }
}