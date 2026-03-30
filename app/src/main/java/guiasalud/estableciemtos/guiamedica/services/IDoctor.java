package guiasalud.estableciemtos.guiamedica.services;
import java.util.ArrayList;
import guiasalud.estableciemtos.guiamedica.modelsEntities.doctor;

public interface IDoctor {
    void exitoTraerDoctores(ArrayList<doctor> x);
    void errorTraerDoctores(String y);
}
