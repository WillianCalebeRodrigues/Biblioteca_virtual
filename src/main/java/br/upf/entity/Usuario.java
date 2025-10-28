package br.upf.entity;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@UserDefinition
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Username
    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Password
    @Column(nullable = false)
    private String password;

    @Roles
    @Column(nullable = false, length = 50)
    private String roles; // Ex: "ADMIN", "BIBLIOTECARIO", "USUARIO"

    @Column(nullable = false, length = 100)
    private String nomeCompleto;

    @Column(unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    // Construtor padrão
    public Usuario() {
    }

    // Construtor para facilitar criação
    public Usuario(String username, String password, String roles, String nomeCompleto, String email) {
        this.username = username;
        this.password = BcryptUtil.bcryptHash(password); // Hash automático
        this.roles = roles;
        this.nomeCompleto = nomeCompleto;
        this.email = email;
    }

    // Método auxiliar para definir senha (com hash)
    public void setPasswordWithHash(String plainPassword) {
        this.password = BcryptUtil.bcryptHash(plainPassword);
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}