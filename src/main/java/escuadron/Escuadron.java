package escuadron;

import java.util.ArrayList;
import java.util.List;

import dron.Dron;

public class Escuadron {
    private static Integer cantMaximaDeDrones = 10;
    private List<Dron> drones;

    public static void setCantMaximaDeDrones(Integer cantMaximaDeDrones) {
        Escuadron.cantMaximaDeDrones = cantMaximaDeDrones;
    }

    public Escuadron(List<Dron> drones) {
        this.drones = new ArrayList<>();
    }

    public void agregarDron(Dron unDron) {
        if (this.drones.size() < cantMaximaDeDrones) {
            this.drones.add(unDron);
        }
        else{
            throw new RuntimeException("Supera la cantidad máxima definida por la ciudad");
        }
    }

    public Boolean puedeOperarSobre(Zona unaZona) {
        return this.drones.stream().anyMatch(d -> d.esDronAvanzado()) && this.drones.stream().mapToInt(d -> d.eficienciaOperativa()).sum() > unaZona.tamaño() * 2;
    }
}
