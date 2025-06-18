package com.autobots.automanager.entidades;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nome;

  @Column
  private String nomeSocial;

  @Enumerated(EnumType.STRING)
  private PerfilUsuario perfil;

  @OneToOne(optional = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "endereco_id", nullable = true)
  private Endereco endereco;

  @Column(nullable = false)
  private Boolean inativo;

  @Column(nullable = true)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate ultimoAcesso = LocalDate.now();

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "cliente_id")
  private Set<Documento> documentos = new HashSet<>();

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "cliente_id")
  private Set<Telefone> telefones = new HashSet<>();

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "cliente_id")
  private Set<Email> emails = new HashSet<>();

  @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JsonIgnore
  private Credencial credencial;

  @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  @JoinColumn(name = "cliente_id")
  private Set<Veiculo> veiculos = new HashSet<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  @JoinColumn(name = "cliente_id")
  @JsonIgnore
  private Set<Venda> vendas = new HashSet<>();

}
