package com.autobots.automanager.servicos;

import java.util.Set;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import com.autobots.automanager.controles.MercadoriaController;
import com.autobots.automanager.entidades.Mercadoria;


@Service
public class AdicionarLinkMercadoriaServico implements AdicionarLinkServico<Mercadoria> {

  @Override
  public void adicionarLink(Set<Mercadoria> mercadorias) {
    for (Mercadoria mercadoria : mercadorias) {
      adicionarLink(mercadoria);
    }
  }

  @Override
  public void adicionarLink(Mercadoria mercadoria) {
    Link linkObter = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(MercadoriaController.class)
            .obterMercadoria(mercadoria.getId()))
        .withRel("obter mercadoria");
    Link linkObterTodos = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(MercadoriaController.class)
            .listarMercadorias())
        .withRel("obter todos os mercadorias");
    Link linkCadastrar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(MercadoriaController.class)
            .criar(null))
        .withRel("cadastrar mercadoria");
    Link linkExcluir = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(MercadoriaController.class)
            .deletar(null))
        .withRel("excluir mercadoria");
    mercadoria.add(linkObter);
    mercadoria.add(linkObterTodos);
    mercadoria.add(linkCadastrar);
    mercadoria.add(linkExcluir);
  }
}
