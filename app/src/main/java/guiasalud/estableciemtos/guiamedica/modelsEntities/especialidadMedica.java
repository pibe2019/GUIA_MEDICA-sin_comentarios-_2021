package guiasalud.estableciemtos.guiamedica.modelsEntities;

public class especialidadMedica {
    int codigoEspecialidad;
    String nombreEspecialidad;
    String estadoEspecialidad;
    String tipoServicioESE;

    public especialidadMedica() {
    }

    public especialidadMedica(int codigoEspecialidad, String nombreEspecialidad, String estadoEspecialidad, String tipoServicioESE) {
        this.codigoEspecialidad = codigoEspecialidad;
        this.nombreEspecialidad = nombreEspecialidad;
        this.estadoEspecialidad = estadoEspecialidad;
        this.tipoServicioESE    = tipoServicioESE;
    }

    public int getCodigoEspecialidad() {
        return codigoEspecialidad;
    }

    public void setCodigoEspecialidad(int codigoEspecialidad) {
        this.codigoEspecialidad = codigoEspecialidad;
    }

    public String getNombreEspecialidad() {
        return nombreEspecialidad;
    }

    public void setNombreEspecialidad(String nombreEspecialidad) {
        this.nombreEspecialidad = nombreEspecialidad;
    }

    public String getEstadoEspecialidad() {
        return estadoEspecialidad;
    }

    public void setEstadoEspecialidad(String estadoEspecialidad) {
        this.estadoEspecialidad = estadoEspecialidad;
    }

    public String getTipoServicioESE() {
        return tipoServicioESE;
    }

    public void setTipoServicioESE(String tipoServicioESE) {
        this.tipoServicioESE = tipoServicioESE;
    }
}
