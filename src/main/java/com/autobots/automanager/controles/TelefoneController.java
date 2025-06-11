package com.autobots.automanager.controles;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.repositorios.TelefoneRepository;
import com.autobots.automanager.servicos.AdicionarLinkTelefoneServico;
import com.autobots.automanager.servicos.AtualizaTelefoneServico;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Telefone", description = "CRUD de telefones")
public class TelefoneController {
  @Autowired
  private TelefoneRepository repositorio;

  @Autowired
  private AdicionarLinkTelefoneServico adicionaLinkTelefoneServico;

  @Autowired
  private AtualizaTelefoneServico atualizaTelefoneServico;

  @PostMapping("/telefone/cadastrar")
  @Operation(summary = "Cadastrar telefone", description = "Cadastra um novo telefone")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Telefone cadastrado com sucesso"),
      @ApiResponse(responseCode = "409", description = "Telefone já cadastrado")
  })
  public ResponseEntity<?> cadastrarTelefone(@RequestBody Telefone telefone) {
    System.out.println("Número	: " + telefone.getNumero());
    System.out.println("DDD: " + telefone.getDdd());
    System.out.println("ID: " + telefone.getId());
    Optional<Telefone> telefoneExistente = repositorio.findById(telefone.getId());
    if (telefoneExistente.isPresent()) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    repositorio.save(telefone);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/telefones")
  @Operation(summary = "Obter todos os telefones", description = "Retorna uma lista de todos os telefones cadastrados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Telefones encontrados", content = @Content(schema = @Schema(implementation = List.class))),
      @ApiResponse(responseCode = "404", description = "Nenhum telefone cadastrado")
  })
  public ResponseEntity<List<Telefone>> obterTelefones() {
    List<Telefone> telefones = repositorio.findAll();
    if (telefones.isEmpty()) {
      ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkTelefoneServico.adicionarLink(new HashSet<>(telefones));
      ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(telefones, HttpStatus.OK);
      return resposta;
    }
  }

  @GetMapping("/telefone/{id}")
  @Operation(summary = "Obter telefone", description = "Retorna um telefone específico com base no ID fornecido")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Telefone encontrado", content = @Content(schema = @Schema(implementation = Telefone.class))),
      @ApiResponse(responseCode = "404", description = "Telefone não encontrado")
  })
  public ResponseEntity<Telefone> obterTelefone(@PathVariable long id) {
    Optional<Telefone> cliente = repositorio.findById(id);
    if (cliente.isEmpty()) {
      ResponseEntity<Telefone> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkTelefoneServico.adicionarLink(cliente.get());
      return ResponseEntity.status(HttpStatus.OK).body(cliente.get());
    }
  }

  @PutMapping("/telefone/atualizar")
  @Operation(summary = "Atualizar telefone", description = "Atualiza as informações de um telefone existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Telefone atualizado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Telefone não encontrado")
  })
  public ResponseEntity<?> atualizarTelefone(@RequestBody Telefone telefoneAtualizado) {
    Optional<Telefone> telefone = repositorio.findById(telefoneAtualizado.getId());
    if (telefone.isPresent()) {
      atualizaTelefoneServico.atualizar(telefone.get(), telefoneAtualizado);
      repositorio.save(telefone.get());
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/telefone/excluir")
  @Operation(summary = "Excluir telefone", description = "Exclui um telefone existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Telefone excluído com sucesso"),
      @ApiResponse(responseCode = "404", description = "Telefone não encontrado")
  })
  public ResponseEntity<?> excluirTelefone(@RequestBody Telefone exclusao) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    Optional<Telefone> telefone = repositorio.findById(exclusao.getId());
    if (telefone.isPresent()) {
      repositorio.delete(telefone.get());
      status = HttpStatus.OK;
    }
    return new ResponseEntity<>(status);
  }
}
