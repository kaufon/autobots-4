package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.repositorios.ServicoRepository;
import com.autobots.automanager.servicos.AdicionarLinkServicoServico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/servico")
public class ServicoController {

  @Autowired
  private ServicoRepository servicoRepository;

  @Autowired
  private AdicionarLinkServicoServico adicionarLink;

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
  @PostMapping("/cadastro")
  public ResponseEntity<Servico> criarServico(@RequestBody Servico servico) {
    Servico novoServico = servicoRepository.save(servico);
    adicionarLink.adicionarLink(novoServico);
    return ResponseEntity.ok(novoServico);
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
  @GetMapping("/listar")
  public ResponseEntity<List<Servico>> listarServicos() {
    List<Servico> servicos = servicoRepository.findAll();
    if (servicos.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    adicionarLink.adicionarLink(new HashSet<>(servicos));
    return ResponseEntity.ok(servicos);
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
  @GetMapping("/{id}")
  public ResponseEntity<Servico> obterServico(@PathVariable Long id) {
    Optional<Servico> servico = servicoRepository.findById(id);
    if (servico.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    adicionarLink.adicionarLink(servico.get());
    return ResponseEntity.ok(servico.get());
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
  @PutMapping("/atualizar/{id}")
  public ResponseEntity<Servico> atualizarServico(@PathVariable Long id, @RequestBody Servico dadosAtualizados) {
    Optional<Servico> servicoOptional = servicoRepository.findById(id);
    if (servicoOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Servico servico = servicoOptional.get();
    servico.setNome(dadosAtualizados.getNome());
    servico.setDescricao(dadosAtualizados.getDescricao());
    servico.setValor(dadosAtualizados.getValor());

    Servico atualizado = servicoRepository.save(servico);
    adicionarLink.adicionarLink(atualizado);
    return ResponseEntity.ok(atualizado);
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
  @DeleteMapping("/deletar/{id}")
  public ResponseEntity<Void> deletarServico(@PathVariable Long id) {
    Optional<Servico> servico = servicoRepository.findById(id);
    if (servico.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    servicoRepository.delete(servico.get());
    return ResponseEntity.noContent().build();
  }
}
