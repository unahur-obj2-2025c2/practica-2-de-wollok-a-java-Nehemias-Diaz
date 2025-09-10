package dron;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import mision.Mision;
import mision.MisionExploracion;
import mision.MisionTransporte;
import mision.MisionVigilancia;
import sensor.Sensor;

public class DronDeSeguridadTest {
    @Test
    public void testEficienciaOperativa() {
        Dron dronSeguridad = new DronDeSeguridad(7, 60, new MisionExploracion());
        // Cálculo base: 7(autonomia) * 10 = 70
        // Extra por tipo de dron (DronDeSeguridad): 0
        // Extra por tipo de misión (MisionExploracion): 0
        assertEquals(70, dronSeguridad.eficienciaOperativa());
    }

    @Test
    public void testEsDronAvanzado() {
        Dron dronSeguridad = new DronDeSeguridad(7, 60, new MisionExploracion());
        // Un DronDeSeguridad es avanzado por tipo si su nivel de procesamiento > 50
        assertTrue(dronSeguridad.esDronAvanzado());
    }

    @Test
    public void testDisminuirAutonomia() {
        Dron dronSeguridad = new DronDeSeguridad(12, 40, new MisionExploracion());
        dronSeguridad.disminuirAutonomia(4);
        assertEquals(8, dronSeguridad.getAutonomia());
    }

    @Test
    public void testCambiarMision() {
        Dron dronSeguridad = new DronDeSeguridad(12, 40, new MisionExploracion());
        Mision nuevaMision = new MisionTransporte();
        dronSeguridad.cambiarMision(nuevaMision);
        assertEquals(nuevaMision, dronSeguridad.getTipoDeMision());
    }

    @Test
    public void testEsDronAvanzadoConMisionVigilancia() {
        Dron dronSeguridad = new DronDeSeguridad(7, 40, new MisionExploracion());
        // Cambiamos la misión a MisionVigilancia con sensores duraderos
        ArrayList<Sensor> sensores = new ArrayList<>();
        sensores.add(new Sensor(2, 10, true));
        sensores.add(new Sensor(3, 10, true));
        sensores.add(new Sensor(8, 20, true));
        dronSeguridad.cambiarMision(new MisionVigilancia(sensores));
        // MisionVigilancia considera avanzado si todos los sensores son duraderos
        assertTrue(dronSeguridad.esDronAvanzado());
    }

    @Test
    public void testNoEsDronAvanzadoConMisionVigilancia() {
        Dron dronSeguridad = new DronDeSeguridad(7, 40, new MisionExploracion());
        // Cambiamos la misión a MisionVigilancia con un sensor no duradero
        ArrayList<Sensor> sensores = new ArrayList<>();
        sensores.add(new Sensor(2, 10, true));
        sensores.add(new Sensor(7, 10, false)); // Sensor no duradero
        sensores.add(new Sensor(8, 20, true));
        dronSeguridad.cambiarMision(new MisionVigilancia(sensores));
        // MisionVigilancia no considera avanzado si algún sensor no es duradero
        assertFalse(dronSeguridad.esDronAvanzado());
    }

    @Test
    public void testEficienciaOperativaConMisionVigilancia() {
        Dron dronSeguridad = new DronDeSeguridad(5, 40, new MisionExploracion());
        // Cambiamos la misión a MisionVigilancia con sensores
        ArrayList<Sensor> sensores = new ArrayList<>();
        sensores.add(new Sensor(2, 10, true));
        sensores.add(new Sensor(3, 15, true));
        sensores.add(new Sensor(4, 20, true));
        dronSeguridad.cambiarMision(new MisionVigilancia(sensores));
        // Cálculo base: 5(autonomia) * 10 = 50
        // Extra por tipo de dron (DronDeSeguridad): 0
        /*
         * Extra por misión de vigilancia: Es la suma de la eficiencia de los sensores.
         * Sensor 1: capacidad=2, mejoras=true -> eficiencia es 2 * 2 = 4.
         * Sensor 2: capacidad=3, mejoras=true -> eficiencia es 3 * 2 = 6.
         * Sensor 3: capacidad=4, mejoras=true -> eficiencia es 4 * 2 = 8.
         */
        assertEquals(68, dronSeguridad.eficienciaOperativa());
    }

    @Test
    public void testEficienciaOperativaConMisionTransporte() {
        Dron dronSeguridad = new DronDeSeguridad(6, 40, new MisionExploracion());
        // Cambiamos la misión a MisionTransporte con peso máximo 15
        dronSeguridad.cambiarMision(new MisionTransporte());
        /*
         * Eficiencia base del dron: 6 (autonomía) * 10 = 60.
         * 
         * Extra por tipo de dron (DronDeSeguridad): 0.
         * 
         * Extra por misión de transporte: 100.
         * 
         * Eficiencia total esperada: 60 (base) + 0 (dron) + 100 (misión) = 160.
         */
        assertEquals(160, dronSeguridad.eficienciaOperativa());
    }

    @Test
    public void testEsDronAvanzadoAmbasCondicionesVerdaderas() {
        // Es avanzado por tipo (nivel de procesamiento > 50) y por misión (sensores
        // duraderos)
        ArrayList<Sensor> sensores = new ArrayList<>();
        sensores.add(new Sensor(1, 10, true));
        sensores.add(new Sensor(1, 10, true));

        MisionVigilancia mision = new MisionVigilancia(sensores);
        Dron dronSeguridad = new DronDeSeguridad(10, 60, mision);

        assertTrue(dronSeguridad.esDronAvanzado());
    }

    @Test
    public void testEsDronAvanzadoConMisionExploracionYEficienciaPar() {
        // Dron no avanzado por tipo (nivel de procesamiento < 50),
        // pero si por la misión (eficiencia operativa par)
        Dron dronDeSeguridad = new DronDeSeguridad(5, 40, new MisionExploracion());
        // Eficiencia: 5 (autonomia) * 10 = 50 (par)
        assertTrue(dronDeSeguridad.esDronAvanzado());
    }

    @Test
    public void testAutonomiaNoSeVuelveNegativa() {
        Dron dronDeSeguridad = new DronDeSeguridad(5, 40, new MisionExploracion());
        dronDeSeguridad.disminuirAutonomia(10);
        // La autonomía no debería ser negativa en este caso.
        assertEquals(-5, dronDeSeguridad.getAutonomia());
    }
}
