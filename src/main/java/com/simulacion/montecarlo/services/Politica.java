package com.simulacion.montecarlo.services;

import com.simulacion.montecarlo.entities.DTO.FilaDto;

import java.util.ArrayList;
import java.util.List;

public interface Politica {
    List<FilaDto> getFilas(Integer desde, Integer hasta);

    public double numeroRandom();
    public Integer costoMantenimiento();
    public Integer costo(Integer costo_pedido, Integer costo_mantenimiento, Integer costo_ruptura);

    Integer calculoValorIntervalo(Integer[] intervalos_valor, double[][] intervalos_probabilidad, double valor_random);
    public Integer costoPedido(Integer[] valores_costo, Integer[] intervalo_valores, double[][] intervalo_probabilidad, double numero_random);
    public Integer cantidadPedido(Integer[] intervalo_valores, double[][] intervalo_probabilidad, int numero_random);
    public Integer stock_actual();

    FilaDto getUltimafila(Integer ultimo);
    public float promedio();




}
