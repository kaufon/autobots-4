package com.autobots.automanager.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.excecoes.SemAutorizacaoException;
import com.autobots.automanager.providers.AutenticacaoProvedor;
import com.autobots.automanager.repositorios.CredencialRepository;
import com.autobots.automanager.repositorios.EmpresaRepository;
import com.autobots.automanager.repositorios.UsuarioRepository;

@Service
public class ExcluirUsuarioServico {
  @Autowired
  private UsuarioRepository usuarioRepositorio;

  @Autowired
  private CredencialRepository credencialRepositorio;

  @Autowired
  private AutenticacaoProvedor autenticacaoProvedor;

  public void excluir( Usuario usuarioExcluido) {
    var usuario = usuarioRepositorio.findById(usuarioExcluido.getId());
    if (usuario.isEmpty()) {
      throw new RuntimeException("Usuario não encontrado");
    }

    var perfil = autenticacaoProvedor.getUsuario().getPerfil();
    switch (perfil) {
      case VENDEDOR:
        if (usuario.get().getPerfil() != PerfilUsuario.CLIENTE) {
          throw new SemAutorizacaoException();
        }
        break;
      case GERENTE:
        if (usuario.get().getPerfil() == PerfilUsuario.ADMIN) {
          throw new SemAutorizacaoException();
        }
        break;
      default:
        break;
    }

    usuarioRepositorio.delete(usuario.get());
    credencialRepositorio.delete(usuario.get().getCredencial());
  }

}
