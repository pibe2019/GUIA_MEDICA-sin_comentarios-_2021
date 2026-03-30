package guiasalud.estableciemtos.guiamedica.services;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;

import guiasalud.estableciemtos.guiamedica.modelsEntities.establecimientoMapa;

public interface IRecepcionEstablecimientoCcordenads {
    void exitoCorde(ArrayList<establecimientoMapa> x, GoogleMap googleMap);
    void exitoImagen(ArrayList<establecimientoMapa> x);
    void errorCorde(String x);
}
