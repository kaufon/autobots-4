package com.autobots.automanager.servicos;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.providers.AutenticacaoProvedor;

@Service
public class ObterUsuariosServicos {
  @Autowired
  private AutenticacaoProvedor autenticacaoProvedor;

  public Set<Usuario> obterUsuarios() {
    var empresa = autenticacaoProvedor.getEmpresa();
    var usuarios = empresa.obterUsuariosPorPerfil(autenticacaoProvedor.getPerfil());
    if (usuarios.isEmpty()) {
      throw new RuntimeException("Nenhum usuario cadastrado");
    }
    return usuarios;
  }
}
