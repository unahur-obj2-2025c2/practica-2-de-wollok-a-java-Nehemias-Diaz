package escuadron;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dron.Dron;
import dron.DronComercial;
import dron.DronDeSeguridad;
import mision.MisionExploracion;
import mision.MisionTransporte;
import zona.Zona;

public class EscuadronTest {

    @Test
    public void testAgregarDronNoSuperaCantidadMaxima() {
        Escuadron.setCantMaximaDeDrones(2);
        Escuadron escuadron = new Escuadron();
        Dron dron1 = new DronComercial(10, 5, new MisionTransporte());
        Dron dron2 = new DronDeSeguridad(10, 5, new MisionExploracion());
        escuadron.agregarDron(dron1);
        escuadron.agregarDron(dron2);
        // El test pasa si no se lanza una excepción
    }

    @Test
    public void testAgregarDronSuperaCantidadMaxima() {
        Escuadron.setCantMaximaDeDrones(1);
        Escuadron escuadron = new Escuadron();
        Dron dron1 = new DronComercial(10, 5, new MisionTransporte());
        Dron dron2 = new DronDeSeguridad(10, 5, new MisionExploracion());
        escuadron.agregarDron(dron1);
        assertThrows(RuntimeException.class, () -> escuadron.agregarDron(dron2));
    }

    @Test
    public void testPuedeOperarSobreConDronAvanzadoYEficienciaSuficiente() {
        Escuadron escuadron = new Escuadron();
        Zona zona = new Zona(10);
        // Un DronDeSeguridad con nivel de procesamiento > 50 es avanzado
        Dron dronAvanzado = new DronDeSeguridad(5, 60, new MisionExploracion());
        // Un DronComercial con baja autonomía, pero que sume la eficiencia
        Dron dronNoAvanzado = new DronComercial(10, 5, new MisionTransporte());
        escuadron.agregarDron(dronAvanzado);
        escuadron.agregarDron(dronNoAvanzado);
        // Eficiencia total: (5*10 + 0 + 0) + (10*10 + 15 + 100) = 50 + 215 = 265
        // Tamaño de la zona * 2 = 10 * 2 = 20
        // 265 > 20 -> true
        assertTrue(escuadron.puedeOperarSobre(zona));
    }

    @Test
    public void testNoPuedeOperarSinDronAvanzado() {
        Escuadron escuadron = new Escuadron();
        Zona zona = new Zona(10);
        // Un DronDeSeguridad con nivel de procesamiento bajo no es avanzado por tipo
        Dron dron1 = new DronDeSeguridad(10, 30, new MisionTransporte());
        // Un DronComercial no es avanzado por tipo
        Dron dron2 = new DronComercial(10, 5, new MisionTransporte());
        escuadron.agregarDron(dron1);
        escuadron.agregarDron(dron2);
        assertFalse(escuadron.puedeOperarSobre(zona));
    }

    @Test
    public void testNoPuedeOperarConEficienciaInsuficiente() {
        Escuadron escuadron = new Escuadron();
        Zona zona = new Zona(100); // Zona grande
        // Es un dron avanzado
        Dron dronAvanzado = new DronDeSeguridad(5, 60, new MisionExploracion());
        // Eficiencia total: (5*10 + 0 + 0) = 50
        // Tamaño de la zona * 2 = 100 * 2 = 200
        // 50 > 200 -> false
        escuadron.agregarDron(dronAvanzado);
        assertFalse(escuadron.puedeOperarSobre(zona));
    }
    // --- Tests de operarSobre() ---

    @Test
    public void testOperarSobreCasoExitoso() {
        Escuadron escuadron = new Escuadron();
        Zona zona = new Zona(10);

        Dron dron1 = new DronDeSeguridad(10, 60, new MisionExploracion());
        Dron dron2 = new DronComercial(15, 5, new MisionTransporte());

        escuadron.agregarDron(dron1);
        escuadron.agregarDron(dron2);

        // La operación debería ser exitosa
        escuadron.operarSobre(zona);

        // Verificamos que la zona registró la operación
        assertEquals(1, zona.getCantDeOperaciones());

        // Verificamos que la autonomía de cada dron disminuyó en 2
        assertEquals(8, dron1.getAutonomia());
        assertEquals(13, dron2.getAutonomia());
    }

    @Test
    public void testOperarSobreCasoFallido() {
        Escuadron escuadron = new Escuadron();
        // Cambiamos el tamaño de la zona para que sea un caso fallido
        Zona zona = new Zona(200);
        Dron dron1 = new DronDeSeguridad(10, 60, new MisionExploracion());
        Dron dron2 = new DronComercial(15, 5, new MisionTransporte());
        escuadron.agregarDron(dron1);
        escuadron.agregarDron(dron2);
        // La operación debería fallar ahora
        escuadron.operarSobre(zona);
        // Verificamos que la zona no registró la operación
        assertEquals(0, zona.getCantDeOperaciones());
        // Verificamos que la autonomía de los drones no cambió
        assertEquals(10, dron1.getAutonomia());
        assertEquals(15, dron2.getAutonomia());
    }
}
