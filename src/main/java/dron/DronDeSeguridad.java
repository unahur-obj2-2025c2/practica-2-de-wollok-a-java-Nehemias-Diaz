package dron;

import mision.Mision;

public class DronDeSeguridad extends Dron {

    public DronDeSeguridad(Integer autonomia, Integer nivelDeProcesamiento, Mision tipoDeMision) {
        super(autonomia, nivelDeProcesamiento, tipoDeMision);
    }

    @Override
    protected Integer extraPorTipoDeDron() {
        return 0;
    }

    @Override
    protected Boolean esAvanzadoSegunTipo() {
        return this.getNivelDeProcesamiento() > 50;
    }
}
