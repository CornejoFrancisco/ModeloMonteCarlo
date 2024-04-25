package com.simulacion.montecarlo.entities.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilaDto {
    private Integer dia;
    private float rndDemanda;
    private Integer cae;
    private float rndDemora;
    private Integer cantidadDia;
    private Integer pedido;
    private Integer stock;
    private Integer ko;
    private Integer km;
    private Integer ks;
    private Integer costo;
    private Integer costoSuma;
    private float promedio;

}
