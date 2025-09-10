package sensor;

public class Sensor {
    private Integer capacidad;
    private Integer durabilidad;
    private Boolean tieneMejorasTecnologicas;
    
    public Sensor(Integer capacidad, Integer durabilidad, Boolean tieneMejorasTecnologicas) {
        this.capacidad = capacidad;
        this.durabilidad = durabilidad;
        this.tieneMejorasTecnologicas = tieneMejorasTecnologicas;
    }

    public Boolean esDuradero(){
        return this.durabilidad > capacidad * 2;
    }

    public Integer eficiencia(){
        Integer eficiencia = 0;
        if (!this.tieneMejorasTecnologicas){
            eficiencia = this.capacidad;
        }
        else{
            eficiencia = this.capacidad * 2;
        }
        return eficiencia;
    }

    public Integer getCapacidad() {
        return this.capacidad;
    }

    public Integer getDurabilidad() {
        return this.durabilidad;
    }

    public Boolean tieneMejorasTecnologicas() {
        return this.tieneMejorasTecnologicas;
    }
}
