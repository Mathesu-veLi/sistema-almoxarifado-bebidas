package com.senai.bebidas.controller;

import com.senai.bebidas.dto.AlertaDTO;
import com.senai.bebidas.service.AlertaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
@RequiredArgsConstructor
public class AlertaController {

    private final AlertaService alertaService;

    @GetMapping("/estoque")
    public ResponseEntity<List<AlertaDTO>> estoqueCritico() {
        List<AlertaDTO> alertas = alertaService.estoqueCritico();
        return ResponseEntity.ok(alertas);
    }
}
