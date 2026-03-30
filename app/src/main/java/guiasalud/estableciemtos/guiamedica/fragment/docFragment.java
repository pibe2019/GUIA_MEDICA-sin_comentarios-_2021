package guiasalud.estableciemtos.guiamedica.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

import guiasalud.estableciemtos.guiamedica.LoadingDialog;
import guiasalud.estableciemtos.guiamedica.modelsEntities.doctor;
import guiasalud.estableciemtos.guiamedica.modelsEntities.especialidadMedica;
import guiasalud.estableciemtos.guiamedica.R;
import guiasalud.estableciemtos.guiamedica.adacter.listDocAdapter;
import guiasalud.estableciemtos.guiamedica.services.IDoctor;
import guiasalud.estableciemtos.guiamedica.services.IEspecialidadesMedicas;
import guiasalud.estableciemtos.guiamedica.services.OnItemDoctorClick;
import guiasalud.estableciemtos.guiamedica.services.ServicioDocSegunEstableci;
import guiasalud.estableciemtos.guiamedica.verificacionInternet;

public class docFragment extends Fragment implements IEspecialidadesMedicas, IDoctor, OnItemDoctorClick {
    private static int codigoEstab;
    RecyclerView recyclerView;
    listDocAdapter listDoctorAdapt;
    ServicioDocSegunEstableci sdsEsta=new ServicioDocSegunEstableci();
    private static ArrayList<especialidadMedica> listaEspecialdades=new ArrayList<>();
    ArrayList<String> listaStringEspecialidades;
    private Spinner especialiddmedica;
    private verificacionInternet aInternt;
    private Context contex;
    LoadingDialog loadingDialog;
    private int contador=0;


    public docFragment() {
        // Required empty public constructor
    }
    public static docFragment createNewInstance(int codEstablecimient){
        codigoEstab=codEstablecimient;
        return new docFragment();
    }

    public static docFragment newInstance() {
        docFragment fragment = new docFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contador=1;
        contex=getContext();
        loadingDialog = new LoadingDialog(contex);
        loadingDialog.startLoadingDialog();
        aInternt=new verificacionInternet(getContext());
        sdsEsta.traerEspecialidadesConMiniUnDocSegunEstableci(getContext(),codigoEstab,this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_doc, container, false);
        ArrayList<doctor> xx=new ArrayList<>();
        especialiddmedica=root.findViewById(R.id.spinnerEspecConMiniUnDoc);
        recyclerView= root.findViewById(R.id.listaRecyclerViewDoctores);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listDoctorAdapt = new listDocAdapter(xx, getContext(),this);
        recyclerView.setAdapter(listDoctorAdapt);
        return root;
    }
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selectSpinnerEspecialiddFrag(codigoEstab);
    }
    public void selectSpinnerEspecialiddFrag(int codEstablecimiento){
        especialiddmedica.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int posicioReal=position-1;
                if (aInternt.HayInternet()){
                    if (position!=0){
                        loadingDialog.startLoadingDialog();
                        int codEspecialidad;
                        codEspecialidad=listaEspecialdades.get(posicioReal).getCodigoEspecialidad();
                        sdsEsta.traerDoctorsSegunEspecialiddEstableci(getContext(),codEstablecimiento,codEspecialidad,docFragment.this);
                    }else{
                        if (contador==0) loadingDialog.startLoadingDialog();
                        else contador=0;
                        sdsEsta.traerTodosLosDoctoresDelEstablecimiento(getContext(),codEstablecimiento,docFragment.this);
                    }
                }else mostrarDialog(getContext(),"e");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
// de la interface especialidades medicas
    @Override
    public void exitoTraerEspecialidades(ArrayList<especialidadMedica> x) {
        listaEspecialdades=x;
        listaStringEspecialidades=new ArrayList<>();
        listaStringEspecialidades.add("Todos");
        for (int i=0;i < x.size();i++){
            listaStringEspecialidades.add(listaEspecialdades.get(i).getNombreEspecialidad());
        }
        llenarSpinerEspecialidds(listaStringEspecialidades);
    }
    public void llenarSpinerEspecialidds(ArrayList<String> lista)
    {
        ArrayAdapter<String> adapter=new ArrayAdapter<>(contex, android.R.layout.simple_spinner_item,lista);
        especialiddmedica.setAdapter(adapter);
    }

    @Override
    public void errorTraerEspecialidades(String y) {

    }
//interfas doctores
    @Override
    public void exitoTraerDoctores(ArrayList<doctor> x) {
        listDoctorAdapt = new listDocAdapter(x, getContext(), this);
        recyclerView.setAdapter(listDoctorAdapt);
        loadingDialog.dismissDialog();
    }

    @Override
    public void errorTraerDoctores(String y) {

    }
    private void mostrarDialog(Context c,String e) {//dialogo basico
        //dialogo en buscar x especialidd
        if (e.equals("e")){
            new AlertDialog.Builder(c)
                    .setTitle(R.string.dialog_general)
                    .setMessage(R.string.dialog_buscarDoc)/*reciclo*/
                    .setPositiveButton("Aceptar", (dialog, which) -> {
                        //getActivity().finish();
                        dialog.dismiss();
                    }).show();
        }else {
            new AlertDialog.Builder(c)
                    .setTitle(R.string.dialog_general)
                    .setMessage(R.string.dialog_internet_colegiatura)
                    .setPositiveButton("Aceptar", (dialog, which) -> {
                        //getActivity().finish();
                        dialog.dismiss();
                    }).show();
        }
    }

    @Override
    public void onItemClick(View view, int position, RelativeLayout click, RelativeLayout expande) {
        if (aInternt.HayInternet()){
            Uri uri=Uri.parse(getString(R.string.link_colegioMedico));
            Intent intent=new Intent(Intent.ACTION_VIEW, uri);
            contex.startActivity(intent);
        }else mostrarDialog(getContext(),"vc");
    }
}