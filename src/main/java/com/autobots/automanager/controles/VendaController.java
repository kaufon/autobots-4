package com.autobots.automanager.controles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorios.VendaRepository;
import com.autobots.automanager.servicos.AdicionarLinkVendaServico;
import com.autobots.automanager.servicos.AtualizaVendaServico;
import com.autobots.automanager.servicos.ObterUsuariosServicos;
import com.autobots.automanager.servicos.ObterVendaServico;
import com.autobots.automanager.servicos.ObterVendasServico;

@RestController
@RequestMapping("/venda")
public class VendaController {

  @Autowired
  private VendaRepository vendaRepository;

  @Autowired
  private AtualizaVendaServico atualizaVendaServico;

  @Autowired
  private AdicionarLinkVendaServico adicionarLink;

  @Autowired
  private ObterVendaServico obterVendaServico;

  @Autowired
  private ObterVendasServico obterVendasServico;

  @GetMapping("/listar")
  public ResponseEntity<List<Venda>> listarVendas() {
    var vendas = obterVendasServico.obterVendas();
    adicionarLink.adicionarLink(new HashSet<>(vendas));
    var lista = new ArrayList<>(vendas);
    return ResponseEntity.ok(lista);
  }

  @PreAuthorize("""
      hasRole('ADMIN') or
      hasRole('GERENTE') or
      (hasRole('VENDEDOR') and #venda.vendedor.id == authentication.principal.usuario.id)
      """)
  @PostMapping("/cadastro")
  public ResponseEntity<Venda> criar(@RequestBody Venda venda) {
    Venda novaVenda = vendaRepository.save(venda);
    return ResponseEntity.ok(novaVenda);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Venda> obterVenda(@PathVariable Long id) {
    var venda = obterVendaServico.obterVenda(id);
    adicionarLink.adicionarLink(venda);
    return ResponseEntity.ok(venda);
  }

  @PutMapping("/atualizar/{id}")
  @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
  public ResponseEntity<Venda> atualizar(@PathVariable Long id, @RequestBody Venda novaVenda) {
    return vendaRepository.findById(id)
        .map(venda -> {
          atualizaVendaServico.atualizar(venda, novaVenda);
          Venda vendaAtualizada = vendaRepository.save(venda);
          adicionarLink.adicionarLink(vendaAtualizada);
          return ResponseEntity.ok(vendaAtualizada);
        })
        .orElse(ResponseEntity.notFound().build());
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletar(@PathVariable Long id) {
    Optional<Venda> vendaOptional = vendaRepository.findById(id);
    if (vendaOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    vendaRepository.delete(vendaOptional.get());
    return ResponseEntity.noContent().build();
  }
}
