package com.senai.bebidas.repository;

import com.senai.bebidas.entity.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long>, JpaSpecificationExecutor<Fornecedor> {

    boolean existsByCnpj(String cnpj);

    @Query("SELECT f FROM Fornecedor f WHERE f.ativo = true")
    List<Fornecedor> findByAtivoTrue();

    @Query("SELECT f FROM Fornecedor f WHERE f.ativo = false")
    List<Fornecedor> findByAtivoFalse();
}