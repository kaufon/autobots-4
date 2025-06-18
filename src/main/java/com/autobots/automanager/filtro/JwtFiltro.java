package com.autobots.automanager.filtro;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.providers.JwtProvedor;
import com.autobots.automanager.repositorios.UsuarioRepository;
import com.autobots.automanager.seguranca.SegurancaUsuario;

@Component
public class JwtFiltro extends OncePerRequestFilter {
  @Autowired
  JwtProvedor jwtProvedor;

  @Autowired
  UsuarioRepository usuarioRepositorio;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {

    var token = recoverToken(request);
    if (token != null) {
      var subject = jwtProvedor.validarToken(token);
      try {
        var usuario = getUsuario(subject);
        var segurancaUsuario = new SegurancaUsuario(usuario);
        var authentication = new UsernamePasswordAuthenticationToken(
            segurancaUsuario, null,
            segurancaUsuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
      } catch (Exception e) {
        throw new BadCredentialsException("Credenciais inválidas");
      }
    }
    filterChain.doFilter(request, response);
  }

  private String recoverToken(HttpServletRequest request) {
    var authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return null;
    }
    return authHeader.replace("Bearer ", "");
  }

  private Usuario getUsuario(String nomeUsuario) {
    var usuario = usuarioRepositorio.findByCredencialNomeUsuario(nomeUsuario);
    if (usuario.isEmpty()) {
      throw new BadCredentialsException("Credenciais inválidas");
    }
    return usuario.get();
  }
}
