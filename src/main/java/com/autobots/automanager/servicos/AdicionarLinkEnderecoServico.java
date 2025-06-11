package com.autobots.automanager.servicos;

import java.util.Set;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import com.autobots.automanager.controles.EnderecoController;
import com.autobots.automanager.entidades.Endereco;


@Service
public class AdicionarLinkEnderecoServico implements AdicionarLinkServico<Endereco> {

  @Override
  public void adicionarLink(Set<Endereco> enderecos) {
    for (Endereco endereco : enderecos) {
      adicionarLink(endereco);
    }
  }

  @Override
  public void adicionarLink(Endereco endereco) {
    Link linkObter = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(EnderecoController.class)
            .obterEndereco(endereco.getId()))
        .withRel("obter endereco");
    Link linkObterTodos = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(EnderecoController.class)
            .obterEnderecos())
        .withRel("obter todos os enderecos");
    Link linkCadastrar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(EnderecoController.class)
            .cadastrarEndereco(null))
        .withRel("cadastrar endereco");
    Link linkAtualizar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(EnderecoController.class)
            .atualizarEndereco(null))
        .withRel("atualizar endereco");
    Link linkExcluir = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(EnderecoController.class)
            .excluirEndereco(null))
        .withRel("excluir endereco");
    endereco.add(linkObter);
    endereco.add(linkObterTodos);
    endereco.add(linkCadastrar);
    endereco.add(linkAtualizar);
    endereco.add(linkExcluir);
  }
}
