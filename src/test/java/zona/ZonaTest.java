package zona;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class ZonaTest {

    @Test
    public void testConstructorYGetters() {
        Zona zona = new Zona(150);
        assertNotNull(zona);
        assertEquals(150, zona.getTamañoTotal());
        assertEquals(0, zona.getCantDeOperaciones());
    }

    @Test
    public void testRegistrarOperacionIncrementaElContador() {
        Zona zona = new Zona(150);
        assertEquals(0, zona.getCantDeOperaciones());
        
        zona.registrarOperacion();
        assertEquals(1, zona.getCantDeOperaciones());
        
        zona.registrarOperacion();
        assertEquals(2, zona.getCantDeOperaciones());
    }
}
