package com.simulacion.montecarlo.services.implementations;

import com.fasterxml.jackson.datatype.jdk8.WrappedIOException;
import com.simulacion.montecarlo.entities.DTO.FilaDto;
import com.simulacion.montecarlo.entities.DTO.FilaDtoActual;
import com.simulacion.montecarlo.entities.Pedido;
import com.simulacion.montecarlo.services.PoliticaServis;

import java.util.List;
import java.util.Random;

public class PoliticaServiceImpl implements PoliticaServis {


    @Override
    public List<FilaDto> getFilas(Integer desde, Integer hasta) {
        return null;
    }

    @Override
    public double numeroRandom() {
        Random random = new Random();
        double numero_radom = random.nextDouble();
        return numero_radom;
    }





    @Override
    public Integer costo(Integer costo_pedido, Integer costo_mantenimiento, Integer costo_ruptura) {
        return costo_mantenimiento + costo_ruptura + costo_pedido;
    }


    @Override
    public double[][] intervalos(double[] intervalos_probabilidad){
        int n = intervalos_probabilidad.length;
        double[][] intervalo_proba = new double[n][2];
        double primero = 0.0;
        for(int i = 0; i < n; i++){
            if(i + 1 == n){
                intervalo_proba[i][0] = primero;
                intervalo_proba[i][1] = 0.99;
                return intervalo_proba;
            }

            double ultimo = primero + intervalos_probabilidad[i];
            intervalo_proba[i][0] = primero;
            intervalo_proba[i][1] = ultimo;
            primero = ultimo;


        }
        return intervalo_proba;
    }
    @Override
    public Integer calculoValorIntervalo( Integer[] intervalos_valor, double[] valores_probabilidad, double valor_random) {
        double[][] intervalos_probabilidad = intervalos(valores_probabilidad);
        for (int i = 0; i < intervalos_probabilidad.length; i++) {
            double linf = intervalos_probabilidad[i][0];
            double lsup = intervalos_probabilidad[i][1];

                if (valor_random >= linf && valor_random < lsup) {
                    return intervalos_valor[i];
                }
            }
        return null;
    }
    @Override
    public Integer costoPedido(Integer[] valores_costo, Integer[] intervalo_valores, double[] intervalo_probabilidad, double numero_random) {
        int valor = calculoValorIntervalo(intervalo_valores, intervalo_probabilidad, numero_random);
        int costoPedido = 0;
        System.out.println(valor);
        if( valor >= 0 &&  valor <= 150 ){
            costoPedido = valores_costo[0];
        }
        if(valor >= 151 && valor <= 250 ){
            costoPedido = valores_costo[1];
        }
        if(valor >= 251){
            costoPedido = valores_costo[2];
        }
        return costoPedido;
    }

    /*
    @Override
    public Integer cantidadPedido(Integer[] intervalo_valores, double[] intervalo_probabilidad, int numero_random) {
        int valor = calculoValorIntervalo(intervalo_valores, intervalo_probabilidad, numero_random);
        return valor;
    }

     */

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
    public FilaDto getUltimafila(FilaDtoActual entity) {
        FilaDto fila = new FilaDto();
        fila.setDia(entity.getDia() + 1);
        fila.setRndDemanda(numeroRandom());
        Pedido pedido = new Pedido();
        fila.setPedido(pedido);
        fila.setStock(entity.getStock());
        Integer costo_mantenimiento = entity.getStock() * 3;
        Integer costo_ruptura = 1;
        fila.setKm(costo_mantenimiento);
        fila.setKs(1);
        Integer costo_fila = costo(pedido.getCosto_pedido(), costo_mantenimiento, costo_ruptura);
        fila.setCosto(costo_fila);
        Integer costo_suma = entity.getCostoSuma() + costo_fila;
        fila.setCostoSuma(costo_suma);
        fila.setPromedio((float) costo_suma / entity.getDia());

        return fila;
    }

}
