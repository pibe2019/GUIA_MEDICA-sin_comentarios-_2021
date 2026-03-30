package guiasalud.estableciemtos.guiamedica.modelsEntities;

public class doctor {
    int codDoctor;
    String especialidad;
    String nombre;
    String apellido;
    String rne;
    String numColegiatura;
    String descripTipoAtencioDoc;
    double precio;
    String dirFacebook;
    String dirInstagram;
    String NuContacto;
    int    codEspecialidd;
    String nombreEstableci;
    String longitudEstableci;
    String latitudEstableci;
    int codEstablecimientDoc;
    private boolean selecciono=false;
    private boolean estadoCardV=false;

    public doctor() {
    }

    public doctor(int codDoctor,String especialidad ,String nombre, String apellido, String rne, String numColegiatura, String descripTipoAtencioDoc, double precio, String dirFacebook,String dirInstagram,String NuContacto, int codEspecialidd,String nombreEstableci,String longitudEstableci,String latitudEstableci,int codEstablecimientDoc) {
        this.codDoctor = codDoctor;
        this.especialidad=especialidad;
        this.nombre = nombre;
        this.apellido = apellido;
        this.rne = rne;
        this.numColegiatura = numColegiatura;
        this.descripTipoAtencioDoc = descripTipoAtencioDoc;
        this.precio = precio;
        this.dirFacebook = dirFacebook;
        this.dirInstagram = dirInstagram;
        this.NuContacto = NuContacto;
        this.codEspecialidd = codEspecialidd;
        this.nombreEstableci = nombreEstableci;
        this.longitudEstableci = longitudEstableci;
        this.latitudEstableci = latitudEstableci;
        this.codEstablecimientDoc =codEstablecimientDoc;
    }

    public boolean isSelecciono() { return selecciono;  }
    public void setSelecciono(boolean selecciono) { this.selecciono = selecciono; }

    public boolean isEstadoCardV() { return estadoCardV; }
    public void setEstadoCardV(boolean estadoCardV) { this.estadoCardV = estadoCardV; }

    public int getCodDoctor() {
        return codDoctor;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public void setCodDoctor(int codDoctor) {
        this.codDoctor = codDoctor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getRne() {
        return rne;
    }

    public void setRne(String rne) {
        this.rne = rne;
    }

    public String getNumColegiatura() {
        return numColegiatura;
    }

    public void setNumColegiatura(String numColegiatura) {
        this.numColegiatura = numColegiatura;
    }

    public String getDescripTipoAtencioDoc() {
        return descripTipoAtencioDoc;
    }

    public void setDescripTipoAtencioDoc(String descripTipoAtencioDoc) {
        this.descripTipoAtencioDoc = descripTipoAtencioDoc;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDirFacebook() {
        return dirFacebook;
    }

    public void setDirFacebook(String dirFacebook) {
        this.dirFacebook = dirFacebook;
    }

    public String getDirInstagram() {
        return dirInstagram;
    }

    public void setDirInstagram(String dirInstagram) {
        this.dirInstagram = dirInstagram;
    }

    public String getNuContacto() {
        return NuContacto;
    }

    public void setNuContacto(String nuContacto) {
        NuContacto = nuContacto;
    }

    public int getCodEspecialidd() {
        return codEspecialidd;
    }

    public void setCodEspecialidd(int codEspecialidd) {
        this.codEspecialidd = codEspecialidd;
    }

    public String getNombreEstableci() {
        return nombreEstableci;
    }

    public void setNombreEstableci(String nombreEstableci) {
        this.nombreEstableci = nombreEstableci;
    }

    public String getLongitudEstableci() {
        return longitudEstableci;
    }

    public void setLongitudEstableci(String longitudEstableci) {
        this.longitudEstableci = longitudEstableci;
    }

    public String getLatitudEstableci() {
        return latitudEstableci;
    }

    public void setLatitudEstableci(String latitudEstableci) {
        this.latitudEstableci = latitudEstableci;
    }

    public int getCodEstablecimientDoc() {
        return codEstablecimientDoc;
    }

    public void setCodEstablecimientDoc(int codEstablecimientDoc) {
        this.codEstablecimientDoc = codEstablecimientDoc;
    }
}
