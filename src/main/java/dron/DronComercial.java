package dron;

import mision.Mision;

public class DronComercial extends Dron {

    public DronComercial(Integer autonomia, Integer nivelDeProcesamiento, Mision tipoDeMision) {
        super(autonomia, nivelDeProcesamiento, tipoDeMision);
    }

    @Override
    protected Integer extraPorTipoDeDron() {
        return 15;
    }

    @Override
    protected Boolean esAvanzadoSegunTipo() {
        return false;
    }
}
