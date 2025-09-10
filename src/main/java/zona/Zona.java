package zona;

public class Zona {
    private Integer tamañoTotal;
    private Integer cantDeOperaciones = 0;

    public Zona(Integer tamañoTotal) {
        this.tamañoTotal = tamañoTotal;
    }

    public void registrarOperacion() {
        this.cantDeOperaciones += 1;
    }

    public Integer getTamañoTotal() {
        return tamañoTotal;
    }

    public Integer getCantDeOperaciones() {
        return cantDeOperaciones;
    }
}
