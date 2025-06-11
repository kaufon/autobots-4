package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.repositorios.MercadoriaRepository;
import com.autobots.automanager.servicos.AdicionarLinkMercadoriaServico;
import com.autobots.automanager.servicos.AtualizaMercadoriaServico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mercadoria")
public class MercadoriaController {

  @Autowired
  private MercadoriaRepository mercadoriaRepository;

  @Autowired
  private AdicionarLinkMercadoriaServico adicionarLink;

  @Autowired
  private AtualizaMercadoriaServico atualizaMercadoriaServico;

  @GetMapping("/listar")
  public ResponseEntity<List<Mercadoria>> listarMercadorias() {
    List<Mercadoria> mercadorias = mercadoriaRepository.findAll();
    if (mercadorias.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    adicionarLink.adicionarLink(new HashSet<>(mercadorias));
    return ResponseEntity.ok(mercadorias);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Mercadoria> obterMercadoria(@PathVariable Long id) {
    Optional<Mercadoria> mercadoria = mercadoriaRepository.findById(id);
    if (mercadoria.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    adicionarLink.adicionarLink(mercadoria.get());
    return ResponseEntity.ok(mercadoria.get());
  }

  @PostMapping("/cadastro")
  public ResponseEntity<Mercadoria> criar(@RequestBody Mercadoria mercadoria) {
    Mercadoria nova = mercadoriaRepository.save(mercadoria);
    adicionarLink.adicionarLink(nova);
    return ResponseEntity.ok(nova);
  }

  @PutMapping("/atualizar/{id}")
  public ResponseEntity<Mercadoria> atualizar(@PathVariable Long id, @RequestBody Mercadoria novaMercadoria) {
    Optional<Mercadoria> mercadoriaOptional = mercadoriaRepository.findById(id);
    if (mercadoriaOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Mercadoria mercadoria = mercadoriaOptional.get();

    atualizaMercadoriaServico.atualizar(mercadoria, novaMercadoria);

    Mercadoria atualizada = mercadoriaRepository.save(mercadoria);
    adicionarLink.adicionarLink(atualizada);
    return ResponseEntity.ok(atualizada);
  }

  @DeleteMapping("/deletar/{id}")
  public ResponseEntity<Void> deletar(@PathVariable Long id) {
    Optional<Mercadoria> mercadoria = mercadoriaRepository.findById(id);
    if (mercadoria.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    mercadoriaRepository.delete(mercadoria.get());
    return ResponseEntity.noContent().build();
  }
}
