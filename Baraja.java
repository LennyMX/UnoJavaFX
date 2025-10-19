package com.example.juegouno;
import java.util.*;

public class Baraja {
    private Stack<Carta> cartas;

    public Baraja() {
        cartas = new Stack<>();
        generarBaraja();
        Collections.shuffle(cartas);
    }

    private void generarBaraja() {
        for (Carta.Color color : Carta.Color.values()) {
            if(color == Carta.Color.COMODIN) continue;
            for(int i = 0; i <= 9; i++) cartas.add(new Carta(color, Carta.Tipo.NUMERO, i));
            cartas.add(new Carta(color, Carta.Tipo.SALTA));
            cartas.add(new Carta(color, Carta.Tipo.REVERSA));
            cartas.add(new Carta(color, Carta.Tipo.TOMA2));
        }
        for(int i=0;i<4;i++){
            cartas.add(new Carta(Carta.Color.COMODIN, Carta.Tipo.COMODIN));
            cartas.add(new Carta(Carta.Color.COMODIN, Carta.Tipo.TOMA4));
        }
    }

    public Carta robar() {
        if(cartas.isEmpty()) throw new NoSuchElementException("Baraja vacía");
        return cartas.pop();
    }

}
