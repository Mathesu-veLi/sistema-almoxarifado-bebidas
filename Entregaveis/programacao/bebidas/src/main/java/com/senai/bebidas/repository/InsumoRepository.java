package com.senai.bebidas.repository;

import com.senai.bebidas.entity.Insumo;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.persistence.LockModeType;
import java.util.List;

@Repository
public interface InsumoRepository extends JpaRepository<Insumo, Long>, JpaSpecificationExecutor<Insumo> {

    @Query("SELECT i FROM Insumo i WHERE (:nome IS NULL OR LOWER(i.nome) LIKE CONCAT('%', LOWER(:nome), '%')) AND (:ativo IS NULL OR i.ativo = :ativo) ORDER BY i.nome ASC")
    List<Insumo> findByNomeAndAtivo(@Param("nome") String nome, @Param("ativo") Boolean ativo);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM Insumo i WHERE i.id = :id")
    Insumo findByIdForUpdate(@Param("id") Long id);

    @Query("SELECT i FROM Insumo i WHERE i.ativo = true")
    List<Insumo> findByAtivoTrue(Sort sort);

    boolean existsByFornecedorId(Long fornecedorId);
}
