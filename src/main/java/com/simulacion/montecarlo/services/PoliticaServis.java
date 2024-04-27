package com.simulacion.montecarlo.services;

import com.simulacion.montecarlo.entities.DTO.FilaDto;
import com.simulacion.montecarlo.entities.DTO.FilaDtoActual;
import com.simulacion.montecarlo.entities.Pedido;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public interface PoliticaServis {

    FilaDtoActual actualizacionFila(FilaDto fila_anterior, FilaDtoActual fila_nueva, Pedido pedido_solicitado);

    List<FilaDto> carga_fila(Integer cantidad, double[] probabilidad_demanada, double[] probabilidad_demora, Integer desde, Integer hasta, Integer stock_soli);


}
