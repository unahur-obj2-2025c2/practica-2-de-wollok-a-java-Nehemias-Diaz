package sensor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class SensorTest {

    @Test
    public void testGetters() {
        Sensor sensor = new Sensor(10, 50, true);
        assertEquals(10, sensor.getCapacidad());
        assertEquals(50, sensor.getDurabilidad());
        assertTrue(sensor.tieneMejorasTecnologicas());
    }

    @Test
    public void testEficienciaConMejoras() {
        Sensor sensor = new Sensor(5, 10, true);
        // Eficiencia = capacidad * 2 = 5 * 2 = 10
        assertEquals(10, sensor.eficiencia());
    }

    @Test
    public void testEficienciaSinMejoras() {
        Sensor sensor = new Sensor(5, 10, false);
        // Eficiencia = capacidad = 5
        assertEquals(5, sensor.eficiencia());
    }

    @Test
    public void testEficienciaConCapacidadCero() {
        Sensor sensor = new Sensor(0, 10, true);
        assertEquals(0, sensor.eficiencia());
    }

    @Test
    public void testEficienciaConDurabilidadCero() {
        Sensor sensor = new Sensor(5, 0, true);
        // La durabilidad no afecta la eficiencia directamente
        assertEquals(10, sensor.eficiencia());
    }
}
