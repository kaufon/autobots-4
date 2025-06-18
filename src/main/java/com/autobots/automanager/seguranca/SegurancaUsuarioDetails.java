
package com.autobots.automanager.seguranca; // Or a more appropriate package like `com.autobots.automanager.servicos.seguranca`

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.autobots.automanager.repositorios.UsuarioRepository;

@Service // Mark it as a Spring Service so it's discovered and becomes a bean
public class SegurancaUsuarioDetails implements UserDetailsService {

  private final UsuarioRepository usuarioRepositorio; // Inject your User Repository

  public SegurancaUsuarioDetails(UsuarioRepository usuarioRepositorio) {
    this.usuarioRepositorio = usuarioRepositorio;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return usuarioRepositorio.findByCredencialNomeUsuario(username) 
        .map(SegurancaUsuario::new) 
        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
  }
}
