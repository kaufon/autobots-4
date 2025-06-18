package com.autobots.automanager.servicos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.providers.AutenticacaoProvedor;
import com.autobots.automanager.repositorios.EmpresaRepository;
import com.autobots.automanager.repositorios.VendaRepository;


@Service
public class CadastrarVendaServico {
  @Autowired
  private EmpresaRepository empresaRepositorio;


  @Autowired
  private VendaRepository vendaRepositorio;

  @Autowired
  private AutenticacaoProvedor autenticacaoProvedor;

  public void cadastrarVenda(Venda venda) {
    var perfil = autenticacaoProvedor.getPerfil();
    if (perfil == PerfilUsuario.VENDEDOR) {
      var usuario = autenticacaoProvedor.getUsuario();
      venda.setVendedor(usuario);
    }
  }
}
