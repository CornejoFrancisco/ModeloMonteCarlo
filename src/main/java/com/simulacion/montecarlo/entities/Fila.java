package com.simulacion.montecarlo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Fila{

    private Integer dia;
    private double rndDemanda;
    private Integer cae;
    private Pedido pedido;
    private Integer llegada;
    private Integer stock;
    private Integer km;
    private Integer ks;
    private Integer costo;
    private Integer costoSuma;
    private float promedio;



}
