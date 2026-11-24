package com.senai.bebidas.service;

import com.senai.bebidas.dto.AlertaDTO;
import com.senai.bebidas.entity.Insumo;
import com.senai.bebidas.repository.InsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertaService {

    private final InsumoRepository insumoRepository;

    @Transactional(readOnly = true)
    public List<AlertaDTO> estoqueCritico() {
        List<Insumo> lista = insumoRepository.findAll(Sort.by("nome").ascending());
        List<AlertaDTO> alertas = new ArrayList<>();
        for (Insumo i : lista) {
            if (i.getQuantidadeAtual().compareTo(i.getEstoqueMinimo()) <= 0) {
                alertas.add(new AlertaDTO(
                        i.getId(),
                        i.getNome(),
                        i.getQuantidadeAtual(),
                        i.getEstoqueMinimo(),
                        "CRITICO"));
            }
        }
        return alertas;
    }
}
