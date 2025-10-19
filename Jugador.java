package com.example.juegouno;
import java.util.*;
import java.util.stream.Collectors;

public class Jugador {
    private String nombre;
    private List<Carta> mano;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mano = new ArrayList<>();
    }

    public void recibirCarta(Carta c) { mano.add(c); }
    public void jugarCarta(Carta c) { mano.remove(c); }

    public List<Carta> getMano() { return mano; }

    public List<Carta> cartasJugables(Carta cartaActual) {
        List<Carta> jugables = new ArrayList<>();

        for (Carta c : mano) {
            boolean mismoColor = c.getColor() == cartaActual.getColor();
            boolean mismoNumero = (c.getTipo() == Carta.Tipo.NUMERO && cartaActual.getTipo() == Carta.Tipo.NUMERO
                    && c.getNumero() == cartaActual.getNumero());
            boolean mismoTipoEspecial = (c.getTipo() != Carta.Tipo.NUMERO
                    && c.getTipo() == cartaActual.getTipo());
            boolean esComodin = (c.getTipo() == Carta.Tipo.COMODIN || c.getTipo() == Carta.Tipo.TOMA4);

            if (mismoColor || mismoNumero || mismoTipoEspecial || esComodin) {
                jugables.add(c);
            }
        }

        return jugables;
    }

    public String getNombre() { return nombre; }
    public boolean haGanado() { return mano.isEmpty(); }
}
