package com.example.juegouno;

public class Carta {
    public enum Color { ROJO, AZUL, VERDE, AMARILLO, COMODIN }
    public enum Tipo { NUMERO, SALTA, REVERSA, TOMA2, COMODIN, TOMA4 }

    private Color color;
    private Tipo tipo;
    private int numero;

    public Carta(Color color, Tipo tipo, int numero) {
        this.color = color;
        this.tipo = tipo;
        this.numero = numero;
    }

    public Carta(Color color, Tipo tipo) {
        this(color, tipo, -1);
    }

    public Color getColor() { return color; }
    public Tipo getTipo() { return tipo; }
    public int getNumero() { return numero; }

    @Override
    public String toString() {
        if(tipo == Tipo.NUMERO) return color + " " + numero;
        else return color + " " + tipo;
    }

    public String getImagenPath() {
        if(tipo == Tipo.NUMERO) return "/imagenes/" + color + "_" + numero + ".png";
        else if(tipo == Tipo.COMODIN || tipo == Tipo.TOMA4) return "/imagenes/" + tipo + ".png";
        else return "/imagenes/" + color + "_" + tipo + ".png";
    }
}
