package juegopreguntas;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import juegopreguntas.preguntas.Preguntas;

public class Test {

    private final ArrayList<Preguntas> preguntas;
    private int puntos;
    private final Jugador jugador;

    public Test() {
        this.puntos = 0;
        this.preguntas = new ArrayList<>();
        this.jugador = new Jugador();
    }

    public void inicarJuego() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("");
        System.out.print("Ingrese su nombre: ");
        this.jugador.setNombre(scanner.nextLine());
        System.out.println("");
        jugarNivel("nivel 1");
        jugarNivel("nivel 2");
        jugarNivel("nivel 3");
        jugarNivel("nivel 4");
        jugarNivel("nivel 5");

        this.jugador.setPuntos(this.puntos);
        System.out.println("Genial " + this.jugador.getNombre() + "!! respondiste todas las preguntas\ntu puntaje total fue de: " + this.puntos);
        actualizarHistorico(this.jugador);
        inicio();
    }

    private void jugarNivel(String nivel) {
        System.out.println("************** " + nivel + " ******************");
        System.out.println("***************para salir en cualquier momento ingrese la palabra \"salir\"");
        filtrarPregunta("arte", nivel);
        filtrarPregunta("deporte", nivel);
        filtrarPregunta("geografia", nivel);
        filtrarPregunta("entretenimiento", nivel);
        filtrarPregunta("ciencia", nivel);
    }

    private boolean mostrarPreguntar(Preguntas pregunta) {

        boolean contains = this.preguntas.contains(pregunta);
        if (!contains) {
            this.preguntas.add(pregunta);
            pregunta.mostrarPregunta();
            System.out.println("\nIngrese el numero de la respuesta que considera correcta");
        }

        return contains;
    }

    private void filtrarPregunta(String categoria, String nivel) {
        boolean noListada;
        Scanner scanner = new Scanner(System.in);
        Preguntas pregunta;
        int respuesta = 0;
        do {
            pregunta = obtenerPregunta(categoria);
            noListada = mostrarPreguntar(pregunta);
        } while (noListada);

        boolean flag;
        do {
            try {
                String texto = scanner.nextLine();
                //sale del juego si ingresan salir
                if (texto.equalsIgnoreCase("salir")) {
                    System.out.println("gracias por jugar con nosotros");
                    this.jugador.setPuntos(this.puntos);
                    actualizarHistorico(this.jugador);
                    System.exit(0);
                }
                respuesta = Integer.parseInt(texto);
                if (respuesta > 4 || respuesta < 1) {
                    System.out.println("debe ingresar un valor entero entre 1 y 4");
                    flag = true;
                } else {
                    flag = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("debe ingresar un valor numerico");
                flag = true;
            }
        } while (flag);

        validarRespuesta(respuesta, pregunta, nivel);
    }

    private Preguntas obtenerPregunta(String categoria) {
        boolean encontro = true;
        Preguntas[] values = Preguntas.values();
        Random r = new Random();
        Preguntas value;
        do {
            int i = r.nextInt(values.length);
            value = values[i];
            if (value.getCategoria().equals(categoria)) {
                encontro = false;
            }
        } while (encontro);

        return value;
    }

    private void validarRespuesta(int respuesta, Preguntas pregunta, String nivel) {
        if (pregunta.getPosicionRespuesta() == respuesta) {
            switch (nivel) {
                case "nivel 1":
                    this.puntos = this.puntos + 5;
                    break;
                case "nivel 2":
                    this.puntos = this.puntos + 10;
                    break;
                case "nivel 3":
                    this.puntos = this.puntos + 15;
                    break;
                case "nivel 4":
                    this.puntos = this.puntos + 25;
                    break;
                case "nivel 5":
                    this.puntos = this.puntos + 30;
                    break;
            }
            System.out.println("respuesta correcta!");
            System.out.println("Total puntos: " + this.puntos);
        } else {
            System.out.println("respuesta erronea :/ la respuesta correcta era la número " + pregunta.getPosicionRespuesta());
            System.out.println("Juego finalizado");
            this.jugador.setPuntos(this.puntos);
            actualizarHistorico(this.jugador);
            System.exit(0);
        }
    }

    private void actualizarHistorico(Jugador jugador) {
        ControlHistorico.agregarRegistro(jugador);
        ControlHistorico.guardarRegistro();
    }

    void verHistoral() {
        Scanner scanner = new Scanner(System.in);
        ControlHistorico.mostrarHistorico();
        System.out.println("Pulse enter para regresar...");
        scanner.nextLine();
        inicio();
    }

    void inicio() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("************************************************");
        System.out.println("**********        BIENVENIDO      **************");
        System.out.println("**********                        **************");
        System.out.println("********** PREGUNTAS Y RESPUESTAS **************");
        System.out.println("**********                        **************");
        System.out.println("************************************************");
        System.out.println("");
        System.out.println("1. Iniciar Juego");
        System.out.println("2. Ver historial");
        System.out.println("3. Salir");

        System.out.println("Ingrese una de las opciones para continuar:");
        int respuesta = 0;
        boolean flag;
        do {
            try {
                String texto = scanner.nextLine();
                respuesta = Integer.parseInt(texto);
                if (respuesta > 3 || respuesta < 1) {
                    System.out.println("debe ingresar un valor entero entre 1 y 3");
                    flag = true;
                } else {
                    flag = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("debe ingresar un valor numerico");
                flag = true;
            }
        } while (flag);

        switch (respuesta) {
            case 1:
                inicarJuego();
                break;
            case 2:
                verHistoral();
                break;
            default:
                System.exit(0);
        }
    }

}
