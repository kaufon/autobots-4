package com.autobots.automanager.servicos;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.providers.AutenticacaoProvedor;

@Service
public class ObterVendasServico {

  @Autowired
  private AutenticacaoProvedor autenticacaoProvedor;

  public Set<Venda> obterVendas() {
    var perfil = autenticacaoProvedor.getPerfil();
    var empresa = autenticacaoProvedor.getEmpresa();
    if (perfil == PerfilUsuario.CLIENTE) {
      var vendas = empresa.obterVendasPorCliente(autenticacaoProvedor.getUsuario());
      return vendas;
    }

    if (perfil == PerfilUsuario.VENDEDOR) {
      var vendas = empresa.obterVendasPorCliente(autenticacaoProvedor.getUsuario());
      return vendas;
    }

    return autenticacaoProvedor.getEmpresa().getVendas();
  }
}
