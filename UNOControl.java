package com.example.juegouno;
import java.util.*;

public class UNOControl {
    private List<Jugador> jugadores;
    private Baraja baraja;
    private Stack<Carta> descartes;
    private int turno;
    private int direccion;

    public UNOControl(List<String> nombres) {
        jugadores = new ArrayList<>();
        for(String n: nombres) jugadores.add(new Jugador(n));
        baraja = new Baraja();
        descartes = new Stack<>();
        turno = 0;
        direccion = 1;

        for(int i=0;i<7;i++) for(Jugador j: jugadores) j.recibirCarta(baraja.robar());

        Carta inicio;
        do {
            inicio = baraja.robar();
        } while(inicio.getTipo() != Carta.Tipo.NUMERO);
        descartes.push(inicio);
    }

    public Carta getCartaActual() { return descartes.peek(); }
    public Jugador getJugadorActual() { return jugadores.get(turno); }

    public void siguienteTurno() {
        turno = (turno + direccion + jugadores.size()) % jugadores.size();
    }

    public boolean jugarCarta(Jugador jugador, Carta c, Carta.Color nuevoColor) {
        Carta actual = descartes.peek();
        if(jugador.cartasJugables(actual).contains(c)){
            jugador.jugarCarta(c);

            if(c.getColor() == Carta.Color.COMODIN && nuevoColor != null){
                Carta copia = new Carta(nuevoColor, c.getTipo());
                descartes.push(copia);
            } else {
                descartes.push(c);
            }

            aplicarEfecto(c);
            return true;
        }
        return false;
    }

    private void aplicarEfecto(Carta c){
        switch(c.getTipo()){
            case SALTA -> {
                siguienteTurno();
            }
            case REVERSA -> {
                direccion *= -1;
            }
            case TOMA2 -> {
                siguienteTurno();
                for(int i = 0; i < 2; i++)
                    jugadores.get(turno).recibirCarta(baraja.robar());
            }
            case TOMA4 -> {
                siguienteTurno();
                for(int i = 0; i < 4; i++)
                    jugadores.get(turno).recibirCarta(baraja.robar());
            }
        }
        siguienteTurno();
    }

    public Carta robarCarta() {
        Carta c = baraja.robar();
        getJugadorActual().recibirCarta(c);
        return c;
    }

    public boolean hayGanador() {
        return jugadores.stream().anyMatch(Jugador::haGanado);
    }

    public Jugador getGanador() {
        return jugadores.stream().filter(Jugador::haGanado).findFirst().orElse(null);
    }
}
