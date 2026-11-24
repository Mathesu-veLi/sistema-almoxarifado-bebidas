package com.senai.bebidas.service;

import com.senai.bebidas.dto.AlertaDTO;
import com.senai.bebidas.entity.Insumo;
import com.senai.bebidas.repository.InsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        Pageable pageable = PageRequest.of(0, 1000, Sort.by("nome").ascending());
        Page<Insumo> pagina = insumoRepository.findByNomeAndAtivo(null, true, pageable);
        List<AlertaDTO> alertas = new ArrayList<>();
        for (Insumo i : pagina.getContent()) {
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
