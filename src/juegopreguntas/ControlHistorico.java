package juegopreguntas;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ControlHistorico {

    private static final ArrayList<Jugador> HISTORICO = new ArrayList<>();

    static void agregarRegistro(Jugador jugador) {
        HISTORICO.add(jugador);
    }

    static void guardarRegistro() {

        FileOutputStream salida;
        try {
            salida = new FileOutputStream("src/historico/historico.txt");
            ObjectOutputStream escrituraEnSalida = new ObjectOutputStream(salida);

            escrituraEnSalida.writeObject(HISTORICO);
            escrituraEnSalida.close();
        } catch (IOException e) {
            System.out.println("Error al guardar registro en el historial de juego\n" + e.getMessage());
        }
    }

    static void leerRegistros() {

        FileInputStream entrada;

        try {
            entrada = new FileInputStream("src/historico/historico.txt");
            ObjectInputStream lecturaEntrada = new ObjectInputStream(entrada);

            ArrayList<Jugador> jugadores = (ArrayList<Jugador>) lecturaEntrada.readObject();

            HISTORICO.addAll(jugadores);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer registros en el historial de juego\n" + e.getMessage());
        }

    }

    public static void mostrarHistorico() {
        HISTORICO.forEach((jugador) -> {
            System.out.println("Nombre: " + jugador.getNombre() + " puntos: " + jugador.getPuntos());
        });
    }
}
