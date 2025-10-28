package br.upf.resources;

import br.upf.dto.LivroSearchDTO;
import br.upf.entity.Livro;
import br.upf.service.BibliotecaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/livros")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LivroResource {

    @Inject
    private BibliotecaService bibliotecaService;

    // Qualquer usuário autenticado pode buscar
    @GET
    @Path("/buscar")
    @RolesAllowed({"ADMIN", "BIBLIOTECARIO", "USUARIO"})
    public List<Livro> buscarLivros(
            @QueryParam("titulo") String titulo,
            @QueryParam("autor") String nomeAutor,
            @QueryParam("disponivel") Boolean disponivel,
            @QueryParam("paginasMin") Integer paginasMin,
            @QueryParam("paginasMax") Integer paginasMax,
            @QueryParam("ano") Integer anoPublicacao) {

        LivroSearchDTO searchDTO = new LivroSearchDTO();
        searchDTO.setTitulo(titulo);
        searchDTO.setNomeAutor(nomeAutor);
        searchDTO.setDisponivel(disponivel);
        searchDTO.setPaginasMin(paginasMin);
        searchDTO.setPaginasMax(paginasMax);
        searchDTO.setAnoPublicacao(anoPublicacao);

        return bibliotecaService.buscarLivrosComFiltros(searchDTO);
    }

    // Apenas ADMIN e BIBLIOTECARIO podem listar todos
    @GET
    @RolesAllowed({"ADMIN", "BIBLIOTECARIO"})
    public List<Livro> listarTodos() {
        return bibliotecaService.listarTodosLivros();
    }

    // Apenas ADMIN pode deletar
    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public void deletar(@PathParam("id") Long id) {
        bibliotecaService.excluirLivro(id);
    }
}