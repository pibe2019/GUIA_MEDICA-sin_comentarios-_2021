package guiasalud.estableciemtos.guiamedica.modelsEntities;
import com.google.android.gms.maps.model.Polyline;

import org.jetbrains.annotations.NotNull;

public class PolylineData {
    private Polyline polyline;
    //private DirectionsLeg leg;

    public PolylineData(Polyline polyline/*, DirectionsLeg leg*/) {
        this.polyline = polyline;
        //this.leg = leg;
    }

    public Polyline getPolyline() {
        return polyline;
    }

    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }

    /*public DirectionsLeg getLeg() {
        return leg;
    }

    public void setLeg(DirectionsLeg leg) {
        this.leg = leg;
    }*/

    @NotNull
    @Override
    public String toString() {
        return "PolylineData{" +
                "polyline=" + polyline +
                '}';
    }
    /*
    @Override
    public String toString() {
        return "PolylineData{" +
                "polyline=" + polyline +
                ", leg=" + leg +
                '}';
    }
    */
}
