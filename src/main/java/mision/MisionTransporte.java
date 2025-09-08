package mision;

import dron.Dron;

public class MisionTransporte implements Mision {

    @Override
    public Integer extra() {
        return 100;
    }

    @Override
    public Boolean esAvanzadoEnMision(Dron dron) {
        return dron.getAutonomia() > 50;
    }
}
