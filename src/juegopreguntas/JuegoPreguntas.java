package juegopreguntas;

public class JuegoPreguntas {

    public static void main(String[] args) {
        ControlHistorico.leerRegistros();
        Test test = new Test();
        test.inicio();
        ControlHistorico.guardarRegistro();
    }
}
