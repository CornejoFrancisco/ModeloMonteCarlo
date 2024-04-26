package com.simulacion.montecarlo.entities.DTO;


import com.simulacion.montecarlo.entities.Pedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilaDto {
    private int dia;
    private double rndDemanda;
    private Integer cae;
    private Pedido pedido;
    private Integer llegada;
    private Integer stock;
    private Integer ko;
    private Integer km;
    private Integer ks;
    private Integer costo;
    private Integer costoSuma;
    private float promedio;

    public FilaDto(int i,
                   double random1,
                   Integer intervaloDemanda,
                   Pedido pedido,
                   Integer intervaloDemora,
                   Integer stock,
                   Integer costoPedido,
                   Integer costoMantenimiento,
                   Integer costoRuptura
            , Integer costo,
                   Integer costo1) {
    }
}
