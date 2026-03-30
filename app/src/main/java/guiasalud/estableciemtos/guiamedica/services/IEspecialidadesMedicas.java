package guiasalud.estableciemtos.guiamedica.services;

import java.util.ArrayList;

import guiasalud.estableciemtos.guiamedica.modelsEntities.especialidadMedica;

public interface IEspecialidadesMedicas {
    void exitoTraerEspecialidades(ArrayList<especialidadMedica> x);
    void errorTraerEspecialidades(String y);
}
