package com.simulacion.montecarlo.services.implementations;

import com.simulacion.montecarlo.entities.DTO.FilaDto;
import com.simulacion.montecarlo.services.Politica;
import org.hibernate.sql.exec.spi.JdbcOperationQueryDelete;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PoliticaAServiceImpl implements Politica {


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
    public Integer costoMantenimiento() {
        return null;
    }



    @Override
    public Integer costo(Integer costo_pedido, Integer costo_mantenimiento, Integer costo_ruptura) {
        return costo_mantenimiento + costo_ruptura + costo_pedido;
    }


    public ArrayList<ArrayList<Double>> intervalos(double[] intervalos_probabilidad){

        int i =0;
        ArrayList<ArrayList<Double>> intervalos = new ArrayList<>();
        for(double valores : intervalos_probabilidad){
            ArrayList<Double> intervalo1 = new ArrayList<>();
            if(i == 0){
                intervalo1.add(0.0);
                intervalo1.add(valores);
            }else{
                double valorAnterior = intervalo1.get(i - 1);
                intervalo1.add(valorAnterior);
                intervalo1.add(valores);
            }
            i +=1;
            intervalos.add(intervalo1);
        }

        return intervalos;
    }
    @Override
    public Integer calculoValorIntervalo( Integer[] intervalos_valor, double[][] intervalos_probabilidad, double valor_random) {
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
    public Integer costoPedido(Integer[] valores_costo, Integer[] intervalo_valores, double[][] intervalo_probabilidad, double numero_random) {
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

    @Override
    public Integer cantidadPedido(Integer[] intervalo_valores, double[][] intervalo_probabilidad, int numero_random) {
        int valor = calculoValorIntervalo(intervalo_valores, intervalo_probabilidad, numero_random);
        return valor;
    }

    @Override
    public Integer stock_actual() {
        return null;
    }

    @Override
    public FilaDto getUltimafila(Integer ultimo) {
        return null;
    }

    @Override
    public float promedio() {
        return 0;
    }
}
