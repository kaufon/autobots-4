package com.autobots.automanager.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Telefone extends RepresentationModel<Telefone> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String ddd;
  @Column(nullable = false)
  private String numero;
  @Column(name = "cliente_id")
  @JsonIgnore
  private Long clienteId;
}
