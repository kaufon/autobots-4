package com.autobots.automanager.servicos;

import java.util.Set;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import com.autobots.automanager.controles.ServicoController;
import com.autobots.automanager.entidades.Servico;


@Service
public class AdicionarLinkServicoServico implements AdicionarLinkServico<Servico> {
  @Override
  public void adicionarLink(Set<Servico> servicos) {
    for (Servico servico : servicos) {
      adicionarLink(servico);
    }
  }

  @Override
  public void adicionarLink(Servico servico) {
    var linkObter = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(ServicoController.class)
            .obterServico(servico.getId()))
        .withRel("obter servico");
    var linkObterTodos = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(ServicoController.class)
            .listarServicos())
        .withRel("obter todos os servicos");
    var linkCadastrar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(ServicoController.class)
            .criarServico(null))
        .withRel("cadastrar servico");
    var linkExcluir = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(ServicoController.class)
            .deletarServico(null))
        .withRel("excluir servico");
    servico.add(linkObter);
    servico.add(linkObterTodos);
    servico.add(linkCadastrar);
    servico.add(linkExcluir);
  }
}
