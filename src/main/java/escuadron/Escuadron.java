package escuadron;

import java.util.ArrayList;
import java.util.List;

import dron.Dron;
import zona.Zona;

public class Escuadron {
    private static Integer cantMaximaDeDrones = 10;
    private List<Dron> drones;

    public static void setCantMaximaDeDrones(Integer cantMaximaDeDrones) {
        Escuadron.cantMaximaDeDrones = cantMaximaDeDrones;
    }

    public Escuadron() {
        this.drones = new ArrayList<>();
    }

    public void agregarDron(Dron unDron) {
        if (this.drones.size() < cantMaximaDeDrones) {
            this.drones.add(unDron);
        } else {
            throw new RuntimeException("Supera la cantidad máxima definida por la ciudad");
        }
    }

    public Boolean tieneAlMenosUnDronAvanzado() {
        return this.drones.stream().anyMatch(d -> d.esDronAvanzado());
    }

    public Boolean puedeOperarSobre(Zona unaZona) {
        return tieneAlMenosUnDronAvanzado() && this.drones.stream().mapToInt(d -> d.eficienciaOperativa()).sum() > unaZona.getTamañoTotal() * 2;
    }

    public void operarSobre(Zona unaZona) {
        if (this.puedeOperarSobre(unaZona)){
            unaZona.registrarOperacion();
            this.drones.forEach(d -> d.disminuirAutonomia(2));
        }
    }
}
