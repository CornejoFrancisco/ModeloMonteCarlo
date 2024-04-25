package com.simulacion.montecarlo;

import com.simulacion.montecarlo.services.implementations.PoliticaAServiceImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class MonteCarloApplication {

	public static void main(String[] args) {

		PoliticaAServiceImpl politicaAService = new PoliticaAServiceImpl();
		Integer[] valoresCosto = {200, 280, 350};
		Integer[] intervaloValores = {0, 150, 250};
		double[][] intervaloProbabilidad;
		intervaloProbabilidad = new double[][]{{0, 0.15}, {0.15, 0.35}, {0.35, 0.75}, {0.75, 0.99}};

		double numeroRandom = politicaAService.numeroRandom(); // Ejemplo de número aleatorio
		System.out.println(numeroRandom);
		Integer costoPedido = politicaAService.costoPedido(valoresCosto, intervaloValores, intervaloProbabilidad, numeroRandom);
		System.out.println("El costo del pedido es: " + costoPedido);

	}

}
