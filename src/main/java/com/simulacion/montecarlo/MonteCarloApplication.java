package com.simulacion.montecarlo;

import com.simulacion.montecarlo.entities.DTO.FilaDto;
import com.simulacion.montecarlo.entities.DTO.FilaDtoActual;
import com.simulacion.montecarlo.entities.Fila;
import com.simulacion.montecarlo.entities.Pedido;
import com.simulacion.montecarlo.services.implementations.PoliticaServiceImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class MonteCarloApplication {

	public static void main(String[] args) {

		List<Fila> lista_fila = new ArrayList<>();
		System.out.print("Ingrese la cantidad de filas que desea: ");
		Scanner teclado = new Scanner(System.in);
		int cantidad  = teclado.nextInt();
		Integer dia_llegada_pedido = 100;
		Integer stock = 20;
		Boolean pedido_realizado = Boolean.FALSE;
		Pedido pedido = new Pedido();
		for(int i = 0 ; i < cantidad; i ++ ){
			if(i == 0) {
				PoliticaServiceImpl politicaAService = new PoliticaServiceImpl();
				double random1 = politicaAService.numeroRandom();
				Integer[] vector_valores = {0, 10, 20, 30, 40, 50};
				double[] probabilidad_demanda = {0.05, 0.12, 0.18, 0.25, 0.22, 0.18};
				Integer intervalo_demanda = politicaAService.calculoValorIntervalo(vector_valores, probabilidad_demanda, random1);

				Integer[] vector_demora = {1, 2, 3, 4};
				double random2 = politicaAService.numeroRandom();
				double[] probabilidad_demora = {0.15, 0.2, 0.4, 0.25};
				Integer intervalo_demora = politicaAService.calculoValorIntervalo(vector_valores, probabilidad_demora, random2);

				pedido = new Pedido(i, i + intervalo_demora, 200, random2, 250);
				Integer costo_mantenimiento = 3 * stock;
				Integer costo = politicaAService.costo(pedido.getCosto_pedido(), costo_mantenimiento, 0 );
				Integer KM = 0;
				Fila nueva_fila = new Fila(i, random1, intervalo_demanda, pedido, intervalo_demora, stock, costo_mantenimiento, KM, costo, costo, costo);
				lista_fila.add(nueva_fila);
			}else{

				Fila elemento = lista_fila.get(i - 1);
				stock = elemento.getStock();
				PoliticaServiceImpl politicaAService = new PoliticaServiceImpl();
				double random1 = politicaAService.numeroRandom();
				Integer[] vector_valores = {0, 10, 20, 30, 40, 50};
				double[] probabilidad_demanda = {0.05, 0.12,0.18,0.25,0.22,0.18};
				Integer intervalo_demanda = politicaAService.calculoValorIntervalo(vector_valores, probabilidad_demanda, random1);
				Integer costo_pedido = 0;

				if(i == 1 ) {
					dia_llegada_pedido = elemento.getPedido().getDia_llegada();
					pedido_realizado = Boolean.TRUE;
				}
				if(i == dia_llegada_pedido && pedido_realizado){
					stock = elemento.getPedido().getCantidad_pedido() + stock;
					pedido_realizado = Boolean.FALSE;
				}
				if(80 > stock ){
					Integer[] vector_demora = {1,2,3,4};
					double random2 = politicaAService.numeroRandom();
					double[] probabilidad_demora = {0.15, 0.2, 0.4, 0.25};
					Integer intervalo_demora = politicaAService.calculoValorIntervalo(vector_demora, probabilidad_demora, random2);
					pedido = new Pedido(i, i + intervalo_demora, 200, random2, 250);
					costo_pedido = pedido.getCosto_pedido();
					pedido_realizado = Boolean.TRUE;
				}


				Integer costo_mantenimiento = 3 * stock;
				Integer KM = 0;
				Integer costo = politicaAService.costo(costo_pedido, costo_mantenimiento, KM);
				Integer costo_suma = elemento.getCosto() + costo;

				Fila nueva_fila = new Fila(i, random1,
						intervalo_demanda,
						pedido,
						pedido.getDia_llegada() - pedido.getDia_solicitado(),
						stock,
						costo_mantenimiento,
						KM,
						costo,
						costo_suma, costo/i);
				lista_fila.add(nueva_fila);
				if(i + 1 == cantidad){
					System.out.println(lista_fila.get(i));
				}
			}
		}


	}

}
