
package com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.autobots.automanager.entidades.Telefone;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
}
