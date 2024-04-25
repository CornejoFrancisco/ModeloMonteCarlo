package com.simulacion.montecarlo.entities.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class FilaDtoActual {
    private Integer dia;
    private Integer stock;
    private Integer costoSuma;

}
