package com.simulacion.montecarlo.controllers;

import com.simulacion.montecarlo.entities.DTO.FilaDto;
import com.simulacion.montecarlo.services.PoliticaServis;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/politicas")
public class PoliticaController {
    private final PoliticaServis politicaAService;

    private final PoliticaServis politicaBService;

    public PoliticaController(@Qualifier("politicaAServiceImpl") PoliticaServis politicaAService,
                              @Qualifier("politicaAServiceImpl")PoliticaServis politicaBService) {
        this.politicaAService = politicaAService;
        this.politicaBService = politicaBService;
    }



    @GetMapping("/cosas")
    public ResponseEntity<List<FilaDto>> carga_fila(@RequestParam("cantidad") Integer cantidad,
                                                    @RequestParam("probabilidad_demanda") double[] probabilidad_demanda,
                                                    @RequestParam("probabilidad_demora") double[] probabilidad_demora,
                                                    @RequestParam("desde") Integer desde,
                                                    @RequestParam("hasta") Integer hasta,
                                                    @RequestParam("stock_soli") Integer stock_soli,
                                                    @RequestParam("servicio") String servicio) {
        PoliticaServis servicioSeleccionado = servicio.equalsIgnoreCase("A") ? politicaAService : politicaBService;

        List<FilaDto> values = servicioSeleccionado.carga_fila(cantidad, probabilidad_demanda, probabilidad_demora, desde, hasta, stock_soli);
        return ResponseEntity.ok(values);
    }



}