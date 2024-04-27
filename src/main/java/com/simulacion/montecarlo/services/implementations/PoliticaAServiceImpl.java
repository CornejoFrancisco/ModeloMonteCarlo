package com.simulacion.montecarlo.services.implementations;

import com.simulacion.montecarlo.entities.DTO.FilaDto;
import com.simulacion.montecarlo.entities.DTO.FilaDtoActual;
import com.simulacion.montecarlo.entities.Pedido;
import com.simulacion.montecarlo.services.PoliticaServis;
import org.springframework.boot.web.context.ServerPortInfoApplicationContextInitializer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PoliticaAServiceImpl implements PoliticaServis {

    @Override
    public FilaDtoActual actualizacionFila(FilaDto fila_anterior, FilaDtoActual fila_nueva, Pedido pedido_solicitado) {
        FilaDto filaAnterior = fila_anterior;
        Integer stock = filaAnterior.getStock();
        FilaDtoActual filaActual = fila_nueva;

        Integer dia = fila_anterior.getDia() + 1;
        Integer costoSuma = fila_anterior.getCostoSuma();
        filaActual.setCostoSuma(costoSuma);

        if(filaActual.getDia() == pedido_solicitado.getDia_llegada()) {
            filaActual.setStock(pedido_solicitado.getCantidad_pedido());
            FilaDtoActual nueva_fila = new FilaDtoActual(dia, stock, costoSuma);
            return nueva_fila;
        }
        FilaDtoActual nueva_fila = new FilaDtoActual(dia, stock, costoSuma);

        return nueva_fila;

    }



    @Override
    public List<FilaDto> carga_fila(Integer cantidad, double[] probabilidad_demanada, double[] probabilidad_demora, Integer desde, Integer hasta, Integer stock_solicitado) {
        List< FilaDto> lista_fila = new ArrayList<>();
        Integer stock = 20;
        Boolean solicitud_mostrar = Boolean.FALSE;
        Soporte soporte = new Soporte();
        Integer costo_suma = 0;

        if(probabilidad_demanada == null){
            probabilidad_demanada = new double[]{0.05, 0.12, 0.18, 0.25, 0.22, 0.18};
        }
        if(probabilidad_demora == null){
            probabilidad_demora = new double[]{0.150000000, 0.2000000000, 0.4000000000, 0.25000000};
        }

        if(stock_solicitado == null){
            stock_solicitado = 200;
        }

        Boolean pedido_realizado = Boolean.TRUE;
        Pedido pedido;
        if(desde != null && hasta != null){
            solicitud_mostrar = Boolean.TRUE;
        }
        /*Vectores de demora y demanda*/
        Integer[] vector_valores = {0, 10, 20, 30, 40, 50};
        Integer[] vector_demora = {1, 2, 3, 4};

        /* Las variables de inicio para la el uso posterior mente*/
        Integer dia_llegada_pedido = 0;
        Integer costo_mantenimiento = 0;
        Integer costo_ruptura = 0;

        for(int i=0; i <= cantidad; i ++){
            if(i == 0){
                /*Generacion de numero random*/
                double random1 = soporte.numeroRandom();
                double random2 = soporte.numeroRandom();

                /*Generador de intervalo */
                Integer intervalo_demanda = soporte.calculoValorIntervalo(vector_valores, probabilidad_demanada, random1);
                Integer intervalo_demora = soporte.calculoValorIntervalo(vector_demora, probabilidad_demora, random2);


                /* Creacion de la orden de pedido*/
                pedido = new Pedido(i, i + intervalo_demora, stock_solicitado, random2, 250);

                dia_llegada_pedido = i + intervalo_demora;
                costo_mantenimiento = 3 * stock;

                ArrayList<Integer> datos = soporte.calculoCostoruptura(stock, intervalo_demanda);
                costo_ruptura = datos.get(0);
                stock = datos.get(1);


                Integer costo = soporte.costo(pedido.getCosto_pedido(), costo_mantenimiento, 0 );

                FilaDto nueva_fila = new FilaDto(i, random1, intervalo_demanda, pedido, intervalo_demora, stock, 250, costo_mantenimiento, costo_ruptura, costo, costo, costo);
                lista_fila.add(nueva_fila);


            }else{
                /*Aca ya se comienza a hacer la segunda fila*/
                FilaDto elemento = lista_fila.get(0);

                Pedido pedido1 = elemento.getPedido();
                stock = elemento.getStock();
                double random1 = soporte.numeroRandom();
                Integer intervalo_demanda = soporte.calculoValorIntervalo(vector_valores, probabilidad_demanada, random1);

                Integer costo_pedido = 0;
                ArrayList<Integer> datos = soporte.calculoCostoruptura(stock, intervalo_demanda);
                costo_ruptura = datos.get(0);
                stock = datos.get(1);


                if(i == 1 ) {
                    dia_llegada_pedido = elemento.getPedido().getDia_llegada();

                }
                if(i == dia_llegada_pedido && pedido_realizado){
                    stock = elemento.getPedido().getCantidad_pedido() + stock;

                    dia_llegada_pedido = 0;
                    pedido_realizado = Boolean.FALSE;
                }
                if(80 > stock && pedido_realizado != Boolean.TRUE){
                    double random2 = soporte.numeroRandom();
                    Integer intervalo_demora = soporte.calculoValorIntervalo(vector_demora, probabilidad_demora, random2);
                    pedido1 = new Pedido(i, i + intervalo_demora, 200, random2, 250);
                    dia_llegada_pedido = i + intervalo_demora;
                    costo_pedido = pedido1.getCosto_pedido();
                    pedido_realizado = Boolean.TRUE;
                }

                costo_mantenimiento = 3 * stock;

                Integer costo = soporte.costo(costo_pedido, costo_mantenimiento, costo_ruptura);

                costo_suma = elemento.getCostoSuma() + costo;
                Integer promedio = costo_suma / (i+ 1);
                System.out.println(promedio);

                FilaDto nueva_fila = new FilaDto(
                        i+ 1,
                        random1,
                        intervalo_demanda,
                        pedido1,
                        elemento.getLlegada(),
                        stock,
                        costo_pedido,
                        costo_mantenimiento,
                        costo_ruptura,
                        costo,
                        costo_suma,
                        promedio);

                if(solicitud_mostrar){
                    soporte.mostrarFila(nueva_fila);
                }
                if(null != hasta) {
                    if (i + 1 == hasta) {
                        solicitud_mostrar = Boolean.FALSE;
                    }
                }
                lista_fila.add(nueva_fila);
                lista_fila.remove(0);
            }
        }
        return lista_fila;
    }


}
