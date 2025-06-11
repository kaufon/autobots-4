package com.autobots.automanager.servicos;

import java.util.Set;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.VendaController;
import com.autobots.automanager.entidades.Venda;

@Component
public class AdicionarLinkVendaServico implements AdicionarLinkServico<Venda> {

  @Override
  public void adicionarLink(Set<Venda> clientes) {
    for (Venda cliente : clientes) {
      long id = cliente.getId();
      Link linkProprio = WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder.methodOn(VendaController.class).obterVenda(id))
          .withSelfRel();
      cliente.add(linkProprio);
    }
  }

  @Override
  public void adicionarLink(Venda cliente) {
    Link linkProprio = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(VendaController.class)
            .listarVendas())
        .withRel("vendas");
    cliente.add(linkProprio);
  }
}
