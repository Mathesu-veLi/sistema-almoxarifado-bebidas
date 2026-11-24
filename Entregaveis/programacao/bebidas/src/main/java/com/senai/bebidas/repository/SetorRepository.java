package com.senai.bebidas.repository;

import com.senai.bebidas.entity.Setor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SetorRepository extends JpaRepository<Setor, Long>, JpaSpecificationExecutor<Setor> {

    @Query("SELECT s FROM Setor s WHERE s.ativo = true")
    List<Setor> findByAtivoTrue();

    @Query("SELECT s FROM Setor s WHERE s.ativo = false")
    List<Setor> findByAtivoFalse();

    @Query("SELECT COUNT(s) > 0 FROM Setor s WHERE s.nome = :nome AND s.ativo = true")
    boolean existsByNomeAndAtivoTrue(@Param("nome") String nome);
}