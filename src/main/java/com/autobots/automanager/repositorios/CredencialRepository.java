package com.autobots.automanager.repositorios;

import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredencialRepository extends JpaRepository<Credencial, Long> {
}
