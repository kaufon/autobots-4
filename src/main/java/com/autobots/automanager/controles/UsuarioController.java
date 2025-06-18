package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.repositorios.UsuarioRepository;
import com.autobots.automanager.servicos.AdicionarLinkUsuarioServico;
import com.autobots.automanager.servicos.AtualizaUsuarioServico;
import com.autobots.automanager.servicos.ObterUsuarioServico;
import com.autobots.automanager.servicos.ObterUsuariosServicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private AdicionarLinkUsuarioServico adicionarLink;

  @Autowired
  private AtualizaUsuarioServico atualizaUsuarioServico;

  @Autowired
  private ObterUsuarioServico obterUsuarioServico;

  @Autowired
  private ObterUsuariosServicos obterUsuariosServicos;

  @GetMapping("/listar")
  @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
  public ResponseEntity<List<Usuario>> listarUsuarios() {
    var usuarios = obterUsuariosServicos.obterUsuarios();
    adicionarLink.adicionarLink(new HashSet<>(usuarios));
    var listaUsuarios = new ArrayList<>(usuarios);
    return ResponseEntity.ok(listaUsuarios);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN','GERENTE','CLIENTE')")
  public ResponseEntity<Usuario> obterUsuario(@PathVariable Long id) {
    var usuario = obterUsuarioServico.obterUsuario(id);
    adicionarLink.adicionarLink(usuario);
    return ResponseEntity.ok(usuario);
  }

  @PreAuthorize("""
      hasRole('ADMIN') or
      (hasRole('GERENTE') and #usuario.perfil.name() != 'ADMIN') or
      (hasRole('VENDEDOR') and #usuario.perfil.name() == 'CLIENTE')
      """)
  @PostMapping("/cadastro")
  public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
    Usuario novoUsuario = usuarioRepository.save(usuario);
    adicionarLink.adicionarLink(novoUsuario);
    return ResponseEntity.ok(novoUsuario);
  }

  @PreAuthorize("""
      hasRole('ADMIN') or
      (hasRole('GERENTE') and #usuarioAtualizacao.perfil.name() != 'ADMIN') or
      (hasRole('VENDEDOR') and #usuarioAtualizacao.perfil.name() == 'CLIENTE')
      """)
  @PutMapping("/atualizar/{id}")
  public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioAtualizado) {
    Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
    if (usuarioOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Usuario usuario = usuarioOptional.get();
    atualizaUsuarioServico.atualizar(usuario, usuarioAtualizado);
    Usuario usuarioSalvo = usuarioRepository.save(usuario);
    adicionarLink.adicionarLink(usuarioSalvo);
    return ResponseEntity.ok(usuarioSalvo);
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
  @DeleteMapping("/deletar/{id}")
  public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
    Optional<Usuario> usuario = usuarioRepository.findById(id);
    if (usuario.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    usuarioRepository.delete(usuario.get());
    return ResponseEntity.noContent().build();
  }
}
