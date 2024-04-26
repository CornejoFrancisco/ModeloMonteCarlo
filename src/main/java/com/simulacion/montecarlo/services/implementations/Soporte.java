package com.simulacion.montecarlo.services.implementations;

import com.simulacion.montecarlo.entities.DTO.FilaDto;

import java.util.ArrayList;
import java.util.Random;

public class Soporte {

    public double numeroRandom() {
        Random random = new Random();
        double numero_radom = random.nextDouble();
        return numero_radom;
    }

    public Integer calculoValorIntervalo( Integer[] intervalos_valor, double[] valores_probabilidad, double valor_random) {
        Soporte soporte = new Soporte();
        double[][] intervalos_probabilidad = soporte.intervalos(valores_probabilidad);
        for (int i = 0; i < intervalos_probabilidad.length; i++) {
            double linf = intervalos_probabilidad[i][0];
            double lsup = intervalos_probabilidad[i][1];

            if (valor_random >= linf && valor_random < lsup) {
                return intervalos_valor[i];
            }
        }
        return 3;
    }


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

    public void mostrarFila(FilaDto filaDto){
        System.out.println(filaDto);
    }
    public Integer costoPedido(Integer[] valores_costo, Integer valor) {
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


    ArrayList calculoCostoruptura(Integer stock, Integer intervaloDemanda) {
        ArrayList<Integer> cosas = new ArrayList<>();

        Integer costo_ruptura = 0;
        if(intervaloDemanda > stock){
            stock = 0;
            costo_ruptura = (intervaloDemanda - stock) * 4;
            cosas.add(costo_ruptura);
            cosas.add(stock);
        }else{
            stock = stock - intervaloDemanda;
            cosas.add(costo_ruptura);
            cosas.add(stock);
        }
        return cosas;
    }

    public Integer costo(Integer costo_pedido, Integer costo_mantenimiento, Integer costo_ruptura) {
        return costo_mantenimiento + costo_ruptura + costo_pedido;
    }
}
