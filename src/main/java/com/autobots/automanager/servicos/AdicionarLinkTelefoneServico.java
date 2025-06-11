package com.autobots.automanager.servicos;

import java.util.Set;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import com.autobots.automanager.controles.TelefoneController;
import com.autobots.automanager.entidades.Telefone;


@Service
public class AdicionarLinkTelefoneServico implements AdicionarLinkServico<Telefone> {

  @Override
  public void adicionarLink(Set<Telefone> telefones) {
    for (Telefone telefone : telefones) {
      adicionarLink(telefone);
    }
  }

  @Override
  public void adicionarLink(Telefone telefone) {
    Link linkObter = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(TelefoneController.class)
            .obterTelefone(telefone.getId()))
        .withRel("obter telefone");
    Link linkObterTodos = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(TelefoneController.class)
            .obterTelefones())
        .withRel("obter todos os telefones");
    Link linkCadastrar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(TelefoneController.class)
            .cadastrarTelefone(null))
        .withRel("cadastrar telefone");
    Link linkAtualizar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(TelefoneController.class)
            .atualizarTelefone(null))
        .withRel("atualizar telefone");
    Link linkExcluir = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(TelefoneController.class)
            .excluirTelefone(null))
        .withRel("excluir telefone");
    telefone.add(linkObter);
    telefone.add(linkObterTodos);
    telefone.add(linkCadastrar);
    telefone.add(linkAtualizar);
    telefone.add(linkExcluir);
  }
}
