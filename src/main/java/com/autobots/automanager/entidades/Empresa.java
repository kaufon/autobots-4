package com.autobots.automanager.entidades;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Empresa extends RepresentationModel<Empresa> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String razaoSocial;

  @Column
  private String nomeFantasia;

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<Telefone> telefones = new HashSet<>();

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  private Endereco endereco;

  @Column(nullable = false)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate cadastro;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<Usuario> usuarios = new HashSet<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<Mercadoria> mercadorias = new HashSet<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<Servico> servicos = new HashSet<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<Veiculo> veiculos = new HashSet<>();

  @OneToMany(mappedBy = "empresa", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Venda> vendas = new HashSet<>();

  public Set<Venda> obterVendasPorCliente(Usuario cliente) {
    return vendas
        .stream()
        .filter(venda -> venda.getCliente().getId() == cliente.getId())
        .collect(Collectors.toSet());
  }

  public Set<Venda> obterVendasPorVendedor(Usuario vendedor) {
    return vendas
        .stream()
        .filter(venda -> venda.getVendedor().getId() == vendedor.getId())
        .collect(Collectors.toSet());
  }

  public Set<Usuario> obterUsuariosPorPerfil(PerfilUsuario perfil) {
    switch (perfil) {
      case ADMIN:
        return usuarios;
      case GERENTE:
        return usuarios
            .stream()
            .filter(usuario -> usuario.getPerfil() != PerfilUsuario.ADMIN)
            .collect(Collectors.toSet());
      case VENDEDOR:
        return usuarios
            .stream()
            .filter(usuario -> usuario.getPerfil() == PerfilUsuario.CLIENTE)
            .collect(Collectors.toSet());
      default:
        return usuarios;
    }
  }
}
