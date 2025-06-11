package com.autobots.automanager.entidades;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.hateoas.RepresentationModel;

import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends RepresentationModel<Usuario> {
  @Id
  private Long id;

  @Column(nullable = false)
  private String nome;

  @Column
  private String nomeSocial;

  @Enumerated(EnumType.STRING)
  private PerfilUsuario perfil;

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "cliente_id")
  private Set<Documento> documentos = new HashSet<>();

  @OneToOne(optional = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "endereco_id", nullable = true)
  private Endereco endereco;

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "cliente_id")
  private Set<Telefone> telefones = new HashSet<>();

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "cliente_id")
  private Set<Email> emails = new HashSet<>();

  @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private CredencialUsuarioSenha credencialUsuarioSenha;

  @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private CredencialCodigoBarra credencialCodigoBarra;

  @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  private Empresa empresa;

  @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
  @JoinColumn(name = "cliente_id")
  @JsonIgnore
  private Set<Mercadoria> mercadorias = new HashSet<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  @JoinColumn(name = "cliente_id")
  private Set<Veiculo> veiculos = new HashSet<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  @JoinColumn(name = "cliente_id")
  private List<Venda> vendas = new ArrayList<>();
}
