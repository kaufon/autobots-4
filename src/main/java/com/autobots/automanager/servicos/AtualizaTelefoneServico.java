package com.autobots.automanager.servicos;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Telefone;


@Service
public class AtualizaTelefoneServico {
  @Autowired
  private VerificaStringNuloServico verificaStringServico;

  public void atualizar(Telefone telefone, Telefone atualizacao) {
    if (atualizacao != null) {
      if (!verificaStringServico.verificar(atualizacao.getDdd())) {
        telefone.setDdd(atualizacao.getDdd());
      }
      if (!verificaStringServico.verificar(atualizacao.getNumero())) {
        telefone.setNumero(atualizacao.getNumero());
      }
    }
  }

  public void atualizar(Set<Telefone> telefones, Set<Telefone> atualizacoes) {
    for (Telefone atualizacao : atualizacoes) {
      for (Telefone telefone : telefones) {
        if (atualizacao.getId() != null) {
          if (atualizacao.getId() == telefone.getId()) {
            atualizar(telefone, atualizacao);
          }
        }
      }
    }
  }
}
