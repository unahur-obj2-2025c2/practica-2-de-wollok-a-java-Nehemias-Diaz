package mision;

import java.util.ArrayList;
import java.util.List;

import dron.Dron;
import sensor.Sensor;

public class MisionVigilancia implements Mision {
    private List<Sensor> sensores;

    public MisionVigilancia(List<Sensor> sensores) {
        this.sensores = new ArrayList<>(sensores);
    }

    @Override
    public Integer extra() {
        return this.sensores.stream().mapToInt(s -> s.eficiencia()).sum();
    }

    @Override
    public Boolean esAvanzadoEnMision(Dron dron) {
        return this.sensores.stream().allMatch(s -> s.esDuradero());
    }
}
