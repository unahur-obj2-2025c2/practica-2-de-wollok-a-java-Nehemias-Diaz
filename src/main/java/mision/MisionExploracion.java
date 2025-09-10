package mision;

import dron.Dron;

public class MisionExploracion implements Mision {

    @Override
    public Integer extra() {
        return 0;
    }

    @Override
    public Boolean esAvanzadoEnMision(Dron dron) {
        return dron.eficienciaOperativa() % 2 == 0;
    }
}
