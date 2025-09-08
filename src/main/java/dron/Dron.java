package dron;

import mision.Mision;

public abstract class Dron {
    private Integer autonomia;
    private Integer nivelDeProcesamiento;
    private Mision tipoDeMision;

    public Dron(Integer autonomia, Integer nivelDeProcesamiento, Mision tipoDeMision) {
        this.autonomia = autonomia;
        this.nivelDeProcesamiento = nivelDeProcesamiento;
        this.tipoDeMision = tipoDeMision;
    }

    public void cambiarMision(Mision nuevaMision) {
        this.tipoDeMision = nuevaMision;
    }

    public Integer eficienciaOperativa() {
        return (autonomia * 10) + tipoDeMision.extra() + extraPorTipoDeDron();
    }

    protected abstract Integer extraPorTipoDeDron();

    public Boolean esDronAvanzado() {
        return esAvanzadoSegunTipo() || tipoDeMision.esAvanzadoEnMision(this);
    }

    protected abstract Boolean esAvanzadoSegunTipo();

    public Integer getAutonomia() {
        return autonomia;
    }

    public Integer getNivelDeProcesamiento() {
        return nivelDeProcesamiento;
    }

    public Mision getTipoDeMision() {
        return tipoDeMision;
    }
}