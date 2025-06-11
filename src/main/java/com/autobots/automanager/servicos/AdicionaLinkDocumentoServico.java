package com.autobots.automanager.servicos;

import java.util.List;
import java.util.Set;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import com.autobots.automanager.controles.DocumentoController;
import com.autobots.automanager.entidades.Documento;


@Service
public class AdicionaLinkDocumentoServico implements AdicionarLinkServico<Documento> {

  @Override
  public void adicionarLink(Set<Documento> documentos) {
    for (Documento documento : documentos) {
      adicionarLink(documento);
    }
  }

  @Override
  public void adicionarLink(Documento documento) {
    Link linkObter = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(DocumentoController.class)
            .obterDocumento(documento.getId()))
        .withRel("obter documento");
    Link linkObterTodos = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(DocumentoController.class)
            .obterDocumentos())
        .withRel("obter todos os documentos");
    Link linkCadastrar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(DocumentoController.class)
            .cadastrarDocumento(null))
        .withRel("cadastrar documento");
    Link linkAtualizar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(DocumentoController.class)
            .atualizarDocumento(null))
        .withRel("atualizar documento");
    Link linkExcluir = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(DocumentoController.class)
            .excluirDocumento(null))
        .withRel("excluir documento");
    documento.add(linkObter);
    documento.add(linkObterTodos);
    documento.add(linkCadastrar);
    documento.add(linkAtualizar);
    documento.add(linkExcluir);
  }
}
