package com.simulacion.montecarlo.controllers;

import com.simulacion.montecarlo.entities.DTO.FilaDto;
import com.simulacion.montecarlo.entities.DTO.FilaDtoActual;
import com.simulacion.montecarlo.services.PoliticaServis;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/politicas")
public class PoliticaController {
    private final PoliticaServis politicaService;

    public PoliticaController(PoliticaServis politicaService) {
        this.politicaService = politicaService;
    }


    @GetMapping("/")
    public ResponseEntity<FilaDto> getUltima(){
        FilaDtoActual entity = new FilaDtoActual();
        FilaDto value = politicaService.getUltimafila(entity);
        return ResponseEntity.ok(value);
    }
}
