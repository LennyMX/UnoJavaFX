package com.example.juegouno;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class Juego extends Application {

    private UNOControl control;
    private VBox root;
    private HBox cartasJugador;
    private Text infoTurno;
    private HBox cartaActualBox;

    @Override
    public void start(Stage stage) {
        control = new UNOControl(List.of("Bryan", "Luis", "alberto"));

        root = new VBox(10);
        cartasJugador = new HBox(5);
        infoTurno = new Text();
        cartaActualBox = new HBox(5);

        root.getChildren().addAll(infoTurno, cartaActualBox, cartasJugador);

        actualizarVista();

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("UNO JavaFX");
        stage.show();
    }

    private void actualizarVista() {
        Jugador jugador = control.getJugadorActual();
        infoTurno.setText("Turno de: " + jugador.getNombre());

        cartaActualBox.getChildren().clear();
        Carta cartaActual = control.getCartaActual();

        System.out.println("Cargando carta actual: " + cartaActual.getImagenPath());
        ImageView imgActual;
        try {
            imgActual = new ImageView(new Image(getClass().getResourceAsStream(cartaActual.getImagenPath())));
        } catch (Exception e) {
            System.out.println("ERROR: No se encontró la imagen " + cartaActual.getImagenPath());
            e.printStackTrace();
            return;
        }
        imgActual.setFitWidth(80);
        imgActual.setFitHeight(120);
        cartaActualBox.getChildren().add(imgActual);

        cartasJugador.getChildren().clear();
        List<Carta> mano = jugador.getMano();
        for (Carta c : mano) {
            System.out.println("Cargando carta de mano: " + c.getImagenPath());
            ImageView imgCarta;
            try {
                imgCarta = new ImageView(new Image(getClass().getResourceAsStream(c.getImagenPath())));
            } catch (Exception e) {
                System.out.println("RROR: No se encontró la imagen " + c.getImagenPath());
                continue;
            }
            imgCarta.setFitWidth(80);
            imgCarta.setFitHeight(120);

            Button btnCarta = new Button("", imgCarta);
            btnCarta.setOnAction(e -> {
                Carta.Color nuevoColor = null;

                if (c.getColor() == Carta.Color.COMODIN) {
                    Stage colorStage = new Stage();
                    VBox box = new VBox(10);
                    box.setStyle("-fx-padding: 20; -fx-alignment: center;");

                    Text txt = new Text("Elige un color:");
                    Button rojo = new Button("Rojo");
                    Button verde = new Button("Verde");
                    Button azul = new Button("Azul");
                    Button amarillo = new Button("Amarillo");

                    rojo.setOnAction(ev -> {
                        elegirColor(colorStage, Carta.Color.ROJO, jugador, c);
                    });
                    verde.setOnAction(ev -> {
                        elegirColor(colorStage, Carta.Color.VERDE, jugador, c);
                    });
                    azul.setOnAction(ev -> {
                        elegirColor(colorStage, Carta.Color.AZUL, jugador, c);
                    });
                    amarillo.setOnAction(ev -> {
                        elegirColor(colorStage, Carta.Color.AMARILLO, jugador, c);
                    });

                    box.getChildren().addAll(txt, rojo, verde, azul, amarillo);
                    Scene sc = new Scene(box);
                    colorStage.setScene(sc);
                    colorStage.setTitle("Selecciona Color");
                    colorStage.show();
                    return;
                }
                if (control.jugarCarta(jugador, c, null)) {
                    if (control.hayGanador()) {
                        infoTurno.setText("¡Ganador: " + control.getGanador().getNombre() + "!");
                        cartasJugador.getChildren().clear();
                        return;
                    }
                    actualizarVista();
                }
            });

            cartasJugador.getChildren().add(btnCarta);
        }

        Button robar = new Button("Robar carta");
        robar.setOnAction(e -> {
            control.robarCarta();
            actualizarVista();
        });
        cartasJugador.getChildren().add(robar);
    }

    private void elegirColor(Stage ventana, Carta.Color color, Jugador jugador, Carta carta) {
        ventana.close();
        control.jugarCarta(jugador, carta, color);
        actualizarVista();
    }
}
