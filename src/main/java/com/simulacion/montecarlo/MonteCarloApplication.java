package com.simulacion.montecarlo;

import com.simulacion.montecarlo.entities.DTO.FilaDto;
import com.simulacion.montecarlo.entities.DTO.FilaDtoActual;
import com.simulacion.montecarlo.entities.Fila;
import com.simulacion.montecarlo.entities.Pedido;
import com.simulacion.montecarlo.services.implementations.PoliticaAServiceImpl;
import com.simulacion.montecarlo.services.implementations.PoliticaBServiceImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class MonteCarloApplication {

	public static void main(String[] args) {
		PoliticaBServiceImpl politicaAService = new PoliticaBServiceImpl();
		politicaAService.carga_fila(100, null, null, null,null, 200);


	}

}
