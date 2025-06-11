package com.autobots.automanager.servicos;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import com.autobots.automanager.controles.UsuarioController;
import com.autobots.automanager.entidades.Usuario;


@Service
public class AdicionarLinkUsuarioServico implements AdicionarLinkServico<Usuario> {
	@Autowired
	private AdicionaLinkDocumentoServico adicionaLinkDocumentoServico;

	@Autowired
	private AdicionarLinkTelefoneServico adicionaLinkTelefoneServico;

	@Autowired
	private AdicionarLinkEnderecoServico adicionaLinkEnderecoServico;

	@Override
	public void adicionarLink(Set<Usuario> clientes) {
		for (Usuario cliente : clientes) {
			adicionarLink(cliente);
		}
	}

	@Override
	public void adicionarLink(Usuario cliente) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(UsuarioController.class)
						.listarUsuarios())
				.withRel("obter-todos");
		Link linkCadastrar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(UsuarioController.class)
						.criarUsuario(null))
				.withRel("cadastrar");
		Link linkExcluir = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(UsuarioController.class)
						.deletarUsuario(null))
				.withRel("excluir");
		adicionaLinkDocumentoServico.adicionarLink(cliente.getDocumentos());
		adicionaLinkTelefoneServico.adicionarLink(cliente.getTelefones());
		if (cliente.getEndereco() != null) {
			adicionaLinkEnderecoServico.adicionarLink(cliente.getEndereco());
		}
		cliente.add(linkProprio);
		cliente.add(linkCadastrar);
		cliente.add(linkExcluir);
	}
}
