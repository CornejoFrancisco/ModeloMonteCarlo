package com.simulacion.montecarlo.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
    private Integer dia_solicitado;
    private Integer dia_llegada;
    private Integer cantidad_pedido;
    private double numero_random_solic;
    private Integer costo_pedido;
}
