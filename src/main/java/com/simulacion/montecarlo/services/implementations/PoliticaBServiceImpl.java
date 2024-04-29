package com.simulacion.montecarlo.services.implementations;

import com.simulacion.montecarlo.entities.DTO.FilaDto;
import com.simulacion.montecarlo.entities.DTO.FilaDtoActual;
import com.simulacion.montecarlo.entities.Pedido;
import com.simulacion.montecarlo.services.PoliticaServis;
import com.zaxxer.hikari.util.SuspendResumeLock;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PoliticaBServiceImpl implements PoliticaServis {
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
        List< FilaDto> lista_fila1 = new ArrayList<>();
        Integer stock = 20;
        Boolean solicitud_mostrar = Boolean.FALSE;
        Soporte soporte = new Soporte();

        Integer[] decena_pedida = new Integer[]{0, 151, 251};
        Integer[] costo_decena = new Integer[]{200, 280, 350};
        if(probabilidad_demanada == null){
            probabilidad_demanada = new double[]{0.05, 0.12, 0.18, 0.25, 0.22, 0.18};
        }
        if(probabilidad_demora == null){
            probabilidad_demora = new double[]{0.15, 0.2, 0.4, 0.25};
        }


        Boolean pedido_realizado = Boolean.FALSE;
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
        Integer contador = 0;
        Integer costo_pedido = 0;


        List<FilaDto> lista_mostrar = new ArrayList<>();


        for(int i=0; i <= cantidad; i ++){
            if(i == 0){
                /*Generacion de numero random*/
                double random1 = soporte.numeroRandom();
                double random2 = soporte.numeroRandom();

                /*Generador de intervalo */
                Integer intervalo_demanda = soporte.calculoValorIntervalo(vector_valores, probabilidad_demanada, random1);
                Integer intervalo_demora = soporte.calculoValorIntervalo(vector_demora, probabilidad_demora, random2);

                stock_solicitado = intervalo_demanda;
                costo_pedido = soporte.costoPedido(costo_decena, stock_solicitado);

                /* Creacion de la orden de pedido*/
                pedido = new Pedido(i + 1 , i + 1  + intervalo_demora, stock_solicitado, random2, costo_pedido);

                dia_llegada_pedido = i + 1 + intervalo_demora;
                costo_mantenimiento = 3 * stock;

                ArrayList<Integer> datos = soporte.calculoCostoruptura(stock, intervalo_demanda);
                costo_ruptura = datos.get(0);
                stock = datos.get(1);


                Integer costo = soporte.costo(pedido.getCosto_pedido(), costo_mantenimiento, costo_ruptura );

                FilaDto nueva_fila = new FilaDto(i + 1, random1, intervalo_demanda, pedido, intervalo_demora, stock,costo_pedido, costo_mantenimiento, costo_ruptura, costo, costo, costo);

                lista_fila1.add(nueva_fila);
                if(solicitud_mostrar){
                    lista_mostrar.add(nueva_fila);
                }
                contador += 1;
            }else{


                /*Aca ya se comienza a hacer la segunda fila*/
                FilaDto elemento = lista_fila1.get(0);

                Pedido pedido1 = elemento.getPedido();
                stock = elemento.getStock();
                double random1 = soporte.numeroRandom();
                Integer intervalo_demanda = soporte.calculoValorIntervalo(vector_valores, probabilidad_demanada, random1);
                stock_solicitado += intervalo_demanda;

                costo_pedido = 0;
                ArrayList<Integer> datos = soporte.calculoCostoruptura(stock, intervalo_demanda);
                costo_ruptura = datos.get(0);
                stock = datos.get(1);

                /*Contador para saber en que dia estoy parado para realizar el pedido correspodiente*/
                contador += 1;

                if(i == 1 ) {
                    dia_llegada_pedido = elemento.getPedido().getDia_llegada();

                }
                if(i == dia_llegada_pedido && pedido_realizado){
                    stock = elemento.getPedido().getCantidad_pedido() + stock;

                }
                if(contador == 10){
                    double random2 = soporte.numeroRandom();
                    contador = 0;

                    /*Demora para guarlos datos en el pedido, te da un numero*/
                    Integer intervalo_demora = soporte.calculoValorIntervalo(vector_demora, probabilidad_demora, random2);

                    costo_pedido = soporte.costoPedido(costo_decena, stock_solicitado);

                    pedido1 = new Pedido(i + 1, i + 1 + intervalo_demora, stock_solicitado, random2, 250);

                    stock_solicitado = 0;
                }


                costo_mantenimiento = 3 * stock;

                Integer costo = soporte.costo(costo_pedido, costo_mantenimiento, costo_ruptura);
                Integer costo_suma = elemento.getCosto() + costo;
                Integer promedio = costo_suma / (i+ 1);

                FilaDto nueva_fila = new FilaDto(
                        i + 1,
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
                    System.out.print(nueva_fila);
                }
                if(hasta != null) {
                    if (i + 1 == hasta) {
                        solicitud_mostrar = Boolean.FALSE;
                    }
                }
                if(solicitud_mostrar){
                    lista_mostrar.add(nueva_fila);
                }

                lista_fila1.add(nueva_fila);
                lista_fila1.remove(0);
                if(cantidad == i +1){
                    lista_fila1.addAll(lista_mostrar);
                }

            }
        }

        return lista_fila1;
    }


}
