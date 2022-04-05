package com.monolitico.repository;

import com.monolitico.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    @Query(value = "select * from persona WHERE numero_identificacion=?1", nativeQuery=true)
    Optional<Persona> findByDocumento(int documento);

}
