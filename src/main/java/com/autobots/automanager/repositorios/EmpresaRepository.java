
package com.autobots.automanager.repositorios;

import com.autobots.automanager.entidades.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}
