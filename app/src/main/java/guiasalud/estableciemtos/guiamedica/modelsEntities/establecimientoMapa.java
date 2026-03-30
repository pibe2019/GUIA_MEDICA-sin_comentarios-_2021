package guiasalud.estableciemtos.guiamedica.modelsEntities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class establecimientoMapa {
    private String nombreEst;
    private String tipoEst;
    private String direccionEst;
    private Double longitudEst;
    private Double latitudEst;
    private String aginawebEst;
    private String facebookEst;
    private String numeroAtencionEst;
    private String whatssapEst;
    private String correoInstitucionalEst;
    private String numeroEmergenciaEst;
    private String licenciaFuncionamientoEst;
    private String descripcionServiciosEst;
    private int codigoEst;
    private int codigoUsuarioRepresentante;
    private char estadoEst;//a:habilitado, d:desabilitado
    private char mostrarEst;//s:si, n:no
    private int resultado;
    private String tipo;
    private Bitmap imagen;
    private String dato;//este recibe la cadena de base64 de la imagen

    public establecimientoMapa() {
    }

    public establecimientoMapa(String nombreEst, String tipoEst, String direccionEst, Double longitudEst, Double latitudEst, String aginawebEst, String facebookEst, String numeroAtencionEst, String whatssapEst, String correoInstitucionalEst, String numeroEmergenciaEst, String licenciaFuncionamientoEst, String descripcionServiciosEst, int codigoEst, int codigoUsuarioRepresentante, char estadoEst, char mostrarEst,int resultado,String tipo,Bitmap imagen,String dato) {
        this.nombreEst = nombreEst;
        this.tipoEst = tipoEst;
        this.direccionEst = direccionEst;
        this.longitudEst = longitudEst;
        this.latitudEst = latitudEst;
        this.aginawebEst = aginawebEst;
        this.facebookEst = facebookEst;
        this.numeroAtencionEst = numeroAtencionEst;
        this.whatssapEst = whatssapEst;
        this.correoInstitucionalEst = correoInstitucionalEst;
        this.numeroEmergenciaEst = numeroEmergenciaEst;
        this.licenciaFuncionamientoEst = licenciaFuncionamientoEst;
        this.descripcionServiciosEst = descripcionServiciosEst;
        this.codigoEst = codigoEst;
        this.codigoUsuarioRepresentante = codigoUsuarioRepresentante;
        this.estadoEst = estadoEst;
        this.mostrarEst = mostrarEst;
        this.resultado = resultado;
        this.tipo = tipo;
        this.imagen = imagen;
        this.dato = dato;
    }

    public String getTipo() { return tipo; }

    public void setTipo(String tipo) { this.tipo = tipo; }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public String getDato() { return dato; }

    public void setDato(String dato) {
        this.dato = dato;
        try {
            byte[] byteCode= Base64.decode(dato,Base64.DEFAULT);
            this.imagen= BitmapFactory.decodeByteArray(byteCode,0,byteCode.length);
        }catch (Exception e) {e.printStackTrace();}

    }

    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }

    public String getNombreEst() {
        return nombreEst;
    }

    public void setNombreEst(String nombreEst) {
        this.nombreEst = nombreEst;
    }

    public String getTipoEst() {
        return tipoEst;
    }

    public void setTipoEst(String tipoEst) {
        this.tipoEst = tipoEst;
    }

    public String getDireccionEst() {
        return direccionEst;
    }

    public void setDireccionEst(String direccionEst) {
        this.direccionEst = direccionEst;
    }

    public Double getLongitudEst() {
        return longitudEst;
    }

    public void setLongitudEst(Double longitudEst) {
        this.longitudEst = longitudEst;
    }

    public Double getLatitudEst() {
        return latitudEst;
    }

    public void setLatitudEst(Double latitudEst) {
        this.latitudEst = latitudEst;
    }

    public String getAginawebEst() {
        return aginawebEst;
    }

    public void setAginawebEst(String aginawebEst) {
        this.aginawebEst = aginawebEst;
    }

    public String getFacebookEst() {
        return facebookEst;
    }

    public void setFacebookEst(String facebookEst) {
        this.facebookEst = facebookEst;
    }

    public String getNumeroAtencionEst() {
        return numeroAtencionEst;
    }

    public void setNumeroAtencionEst(String numeroAtencionEst) {
        this.numeroAtencionEst = numeroAtencionEst;
    }

    public String getWhatssapEst() {
        return whatssapEst;
    }

    public void setWhatssapEst(String whatssapEst) {
        this.whatssapEst = whatssapEst;
    }

    public String getCorreoInstitucionalEst() {
        return correoInstitucionalEst;
    }

    public void setCorreoInstitucionalEst(String correoInstitucionalEst) {
        this.correoInstitucionalEst = correoInstitucionalEst;
    }

    public String getNumeroEmergenciaEst() {
        return numeroEmergenciaEst;
    }

    public void setNumeroEmergenciaEst(String numeroEmergenciaEst) {
        this.numeroEmergenciaEst = numeroEmergenciaEst;
    }

    public String getLicenciaFuncionamientoEst() {
        return licenciaFuncionamientoEst;
    }

    public void setLicenciaFuncionamientoEst(String licenciaFuncionamientoEst) {
        this.licenciaFuncionamientoEst = licenciaFuncionamientoEst;
    }

    public String getDescripcionServiciosEst() {
        return descripcionServiciosEst;
    }

    public void setDescripcionServiciosEst(String descripcionServiciosEst) {
        this.descripcionServiciosEst = descripcionServiciosEst;
    }

    public int getCodigoEst() {
        return codigoEst;
    }

    public void setCodigoEst(int codigoEst) {
        this.codigoEst = codigoEst;
    }

    public int getCodigoUsuarioRepresentante() {
        return codigoUsuarioRepresentante;
    }

    public void setCodigoUsuarioRepresentante(int codigoUsuarioRepresentante) {
        this.codigoUsuarioRepresentante = codigoUsuarioRepresentante;
    }

    public char getEstadoEst() {
        return estadoEst;
    }

    public void setEstadoEst(char estadoEst) {
        this.estadoEst = estadoEst;
    }

    public char getMostrarEst() {
        return mostrarEst;
    }

    public void setMostrarEst(char mostrarEst) {
        this.mostrarEst = mostrarEst;
    }
}
