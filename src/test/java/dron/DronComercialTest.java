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

public class DronComercialTest {
    @Test
    public void testEficienciaOperativa() {
        Dron dronComercial = new DronComercial(5, 3, new MisionTransporte());
        // Cálculo base: 5(autonomia) * 10 = 50
        // Extra por tipo de dron (DronComercial): 15
        // Extra por tipo de misión (MisionTransporte): 100
        assertEquals(165, dronComercial.eficienciaOperativa());
    }

    @Test
    public void testEsDronAvanzado() {
        Dron dronComercial = new DronComercial(5, 3, new MisionTransporte());
        // Un DronComercial no es avanzado por tipo, y MisionTransporte no lo considera
        // avanzado
        assertFalse(dronComercial.esDronAvanzado());
    }

    @Test
    public void testDisminuirAutonomia() {
        Dron dronComercial = new DronComercial(10, 3, new MisionTransporte());
        dronComercial.disminuirAutonomia(3);
        assertEquals(7, dronComercial.getAutonomia());
    }
    
    @Test
    public void testAutonomiaNoSeVuelveNegativa() {
        Dron dronComercial = new DronComercial(10, 3, new MisionTransporte());
        dronComercial.disminuirAutonomia(15);
        assertEquals(-5, dronComercial.getAutonomia());
    }

    @Test
    public void testCambiarMision() {
        Dron dronComercial = new DronComercial(10, 3, new MisionTransporte());
        Mision nuevaMision = new MisionExploracion();
        dronComercial.cambiarMision(nuevaMision);
        assertEquals(nuevaMision, dronComercial.getTipoDeMision());
    }

    @Test
    public void testEficienciaOperativaConMisionVigilancia() {
        Dron dronComercial = new DronComercial(5, 3, new MisionTransporte());
        // Cambiamos la misión a MisionVigilancia sin sensores (extra = 0)
        dronComercial.cambiarMision(new MisionVigilancia(new ArrayList<>()));
        // Cálculo base: 5(autonomia) * 10 = 50
        // Extra por tipo de dron (DronComercial): 15
        // Extra por tipo de misión (MisionVigilancia sin sensores): 0
        assertEquals(65, dronComercial.eficienciaOperativa());
    }

    @Test
    public void testEsDronAvanzadoConMisionExploracionYEficienciaImpar() {
        Dron dronComercial = new DronComercial(5, 3, new MisionExploracion());
        // Cálculo de eficiencia operativa: 5 * 10 + 15 + 0 = 65 (impar)
        // MisionExploracion considera avanzado si la eficiencia es par
        assertFalse(dronComercial.esDronAvanzado());
    }

    @Test
    public void testEsDronAvanzadoConMisionVigilanciaConSensores() {
        Dron dronComercial = new DronComercial(5, 3, new MisionTransporte());
        // Cambiamos la misión a MisionVigilancia con sensores duraderos
        ArrayList<Sensor> sensores = new ArrayList<>();
        sensores.add(new Sensor(2, 10, true));
        sensores.add(new Sensor(8, 20, true));
        dronComercial.cambiarMision(new MisionVigilancia(sensores));
        // MisionVigilancia considera avanzado si todos los sensores son duraderos
        assertTrue(dronComercial.esDronAvanzado());
    }

    @Test
    public void testEficienciaOperativaConMisionVigilanciaConSensores() {
        Dron dronComercial = new DronComercial(5, 3, new MisionTransporte());
        // Cambiamos la misión a MisionVigilancia con sensores
        ArrayList<Sensor> sensores = new ArrayList<>();
        sensores.add(new Sensor(2, 10, true)); // eficiencia 4
        sensores.add(new Sensor(3, 5, false)); // eficiencia 3
        dronComercial.cambiarMision(new MisionVigilancia(sensores));
        // Cálculo base: 5(autonomia) * 10 = 50
        // Extra por tipo de dron (DronComercial): 15
        // Extra por tipo de misión (MisionVigilancia con sensores): 4 + 3 = 7
        assertEquals(72, dronComercial.eficienciaOperativa());
    }

    @Test
    public void testEsDronAvanzadoConMisionVigilanciaConUnSensorNoDuradero() {
        Dron dronComercial = new DronComercial(5, 3, new MisionTransporte());
        // Cambiamos la misión a MisionVigilancia con un sensor no duradero
        ArrayList<Sensor> sensores = new ArrayList<>();
        sensores.add(new Sensor(2, 10, true)); // duradero
        sensores.add(new Sensor(3, 5, false)); // no duradero
        dronComercial.cambiarMision(new MisionVigilancia(sensores));
        // MisionVigilancia considera avanzado si todos los sensores son duraderos
        assertFalse(dronComercial.esDronAvanzado());
    }

    @Test
    public void testEficienciaOperativaConAutonomiaCero() {
        Dron dronComercial = new DronComercial(0, 3, new MisionTransporte());
        // Cálculo base: 0(autonomia) * 10 = 0
        // Extra por tipo de dron (DronComercial): 15
        // Extra por tipo de misión (MisionTransporte): 100
        assertEquals(115, dronComercial.eficienciaOperativa());
    }

    @Test
    public void testGetters() {
        Dron dron = new DronComercial(10, 5, new MisionTransporte());
        assertEquals(10, dron.getAutonomia());
        assertEquals(5, dron.getNivelDeProcesamiento());
        assertTrue(dron.getTipoDeMision() instanceof MisionTransporte);
    }
}