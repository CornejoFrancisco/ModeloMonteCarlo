package com.simulacion.montecarlo.services;

import com.simulacion.montecarlo.entities.DTO.FilaDto;
import com.simulacion.montecarlo.entities.DTO.FilaDtoActual;
import com.simulacion.montecarlo.entities.Pedido;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface PoliticaServis {
    List<FilaDto> getFilas(Integer desde, Integer hasta);

    public double numeroRandom();
    public Integer costo(Integer costo_pedido, Integer costo_mantenimiento, Integer costo_ruptura);

    public double[][] intervalos(double[] intervalos_probabilidad);
    Integer calculoValorIntervalo(Integer[] intervalos_valor, double[] vector_probabilidad, double valor_random);
    public Integer costoPedido(Integer[] valores_costo, Integer[] intervalo_valores, double[] intervalo_probabilidad, double numero_random);
    /*public Integer cantidadPedido(Integer[] intervalo_valores, double[][] intervalo_probabilidad, int numero_random);
    */
    FilaDtoActual actualizacionFila(FilaDto fila_anterior, FilaDtoActual fila_nueva, Pedido pedido_solicitado);


    FilaDto getUltimafila(FilaDtoActual entity);




}
