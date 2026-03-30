package guiasalud.estableciemtos.guiamedica.fragment;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import guiasalud.estableciemtos.guiamedica.LoadingDialog;
import guiasalud.estableciemtos.guiamedica.modelsEntities.especialidadMedica;
import guiasalud.estableciemtos.guiamedica.R;
import guiasalud.estableciemtos.guiamedica.adacter.listEspecialidAdapter;
import guiasalud.estableciemtos.guiamedica.services.IEspecialidadesMedicas;
import guiasalud.estableciemtos.guiamedica.services.ServicioEspecialiddsSegunEstableci;

public class especialiddsFragment extends Fragment implements IEspecialidadesMedicas{
    private static int codigoEstab;
    RecyclerView recyclerView;
    listEspecialidAdapter listEspeciAdapt;
    ServicioEspecialiddsSegunEstableci sesgEstablecimient=new ServicioEspecialiddsSegunEstableci();
    LoadingDialog loadingDialog;

    public especialiddsFragment() {
        // Required empty public constructor
    }
    public static especialiddsFragment createNewInstance(int codEstablecimient){
        codigoEstab=codEstablecimient;
        return new especialiddsFragment();
    }

    public static especialiddsFragment newInstance() {
        especialiddsFragment fragment = new especialiddsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = new LoadingDialog(getContext());
        loadingDialog.startLoadingDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rooView=inflater.inflate(R.layout.fragment_especialidds, container, false);
        ArrayList<especialidadMedica> xx=new ArrayList<>();
        recyclerView= rooView.findViewById(R.id.listRecyclerViewEspecialidades);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listEspeciAdapt = new listEspecialidAdapter(xx,getContext());
        recyclerView.setAdapter(listEspeciAdapt);
        sesgEstablecimient.especialiddsSegunEstableci(getContext(),codigoEstab,this);
        return rooView;
    }
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void exitoTraerEspecialidades(ArrayList<especialidadMedica> x) {
        listEspeciAdapt = new listEspecialidAdapter(x,getContext());
        recyclerView.setAdapter(listEspeciAdapt);
        loadingDialog.dismissDialog();
    }

    @Override
    public void errorTraerEspecialidades(String y) {

    }
}