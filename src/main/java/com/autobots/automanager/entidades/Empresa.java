package com.autobots.automanager.entidades;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

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

  @OneToMany(mappedBy = "empresa", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<Usuario> usuarios = new HashSet<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<Mercadoria> mercadorias = new HashSet<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<Servico> servicos = new HashSet<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<Veiculo> veiculos = new HashSet<>();

  @OneToMany(mappedBy = "empresa", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
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
