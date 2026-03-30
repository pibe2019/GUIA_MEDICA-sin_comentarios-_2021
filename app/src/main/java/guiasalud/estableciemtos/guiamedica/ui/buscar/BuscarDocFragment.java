package guiasalud.estableciemtos.guiamedica.ui.buscar;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Locale;

import guiasalud.estableciemtos.guiamedica.LoadingDialog;
import guiasalud.estableciemtos.guiamedica.modelsEntities.doctor;
import guiasalud.estableciemtos.guiamedica.R;
import guiasalud.estableciemtos.guiamedica.adacter.listBuscDocXapellAdapter;
import guiasalud.estableciemtos.guiamedica.services.IDoctor;
import guiasalud.estableciemtos.guiamedica.services.ServicioBuscarDoc;
import guiasalud.estableciemtos.guiamedica.verificacionInternet;

public class BuscarDocFragment extends Fragment implements SearchView.OnQueryTextListener, IDoctor {
    RecyclerView recyclerView;
    private SearchView sv;
    String checkBoxEstado="d";//d desactivado, a activado
    private CheckBox CBNombre, CBApellido;
    listBuscDocXapellAdapter lisBusDocAdapter;//reciclerAdapter(el adaptador)
    private verificacionInternet aInternt;
    ServicioBuscarDoc sbd=new ServicioBuscarDoc();
    private ImageButton voz;
    ActivityResultLauncher<Intent> launchSomeActivity;
    private EditText txtSearch;
    LoadingDialog loadingDialog;

    public BuscarDocFragment() {
        // Required empty public constructor
    }

    public static BuscarDocFragment newInstance() {
        BuscarDocFragment fragment = new BuscarDocFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        registerForActivity();//para q obtenga lo q gablo x el microfono, reemplasa al "OnActivityResult"
        super.onCreate(savedInstanceState);
        loadingDialog = new LoadingDialog(getContext());
        loadingDialog.startLoadingDialog();

        aInternt=new verificacionInternet(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ArrayList<doctor> xx=new ArrayList<>();
        View rootView = inflater.inflate(R.layout.fragment_buscar_doc, container, false);
        voz=rootView.findViewById(R.id.idVoz);
        sv=rootView.findViewById(R.id.svBuscar);
        CBNombre=rootView.findViewById(R.id.checkBoxNombre);
        CBApellido=rootView.findViewById(R.id.checkBoxApellido);
        recyclerView= rootView.findViewById(R.id.listRecyclerViewBuscarDoc);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        lisBusDocAdapter= new listBuscDocXapellAdapter(xx,getContext());
        recyclerView.setAdapter(lisBusDocAdapter);
        if (aInternt.HayInternet()) sbd.todoLosDoctores(getContext(),this);
        else mostrarDialog(getContext());

        return rootView;
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    public void checkBoxButonName(){
        if(CBNombre.isChecked()){
            sv.setQueryHint(getString(R.string.dialog_buscarDoc_SearVie_Nom));
            CBApellido.setChecked(false);
            checkBoxEstado="a";
        }else {
            checkBoxEstado="d";
            sv.setQueryHint(getString(R.string.dialog_buscarDoc_SearVie_Especi));
        }
    }
    public void checkBoxButonApellido(){
        if(CBApellido.isChecked()){
            sv.setQueryHint(getString(R.string.dialog_buscarDoc_SearVie_Ape));
            CBNombre.setChecked(false);
            checkBoxEstado="b";
        }else{
            checkBoxEstado="d";
            sv.setQueryHint(getString(R.string.dialog_buscarDoc_SearVie_Especi));
        }
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sv.setOnQueryTextListener(this);
        txtSearch =sv.findViewById(androidx.appcompat.R.id.search_src_text);
        txtSearch.setTextColor(Color.BLUE);//lo q ingresa texto
        txtSearch.setTextSize(16);
        //sv.setQueryHint(Html.fromHtml("<font size = #ffffff>" + getResources().getString(R.string.sear_view) + "</font>"));
        CBNombre.setOnClickListener(v -> checkBoxButonName());
        CBApellido.setOnClickListener(v -> checkBoxButonApellido());
        voz.setOnClickListener(v -> iniciarEntradaVoz());
    }
    public void iniciarEntradaVoz(){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,R.string.dialog_buscarDoc_voz);
        try {
            launchSomeActivity.launch(intent);
        }catch (ActivityNotFoundException e){
            Toast.makeText(getContext(),R.string.dialog_buscarDoc_toas,Toast.LENGTH_SHORT).show();
        }
    }
    public void registerForActivity(){
        launchSomeActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (null!=data){
                            ArrayList<String> speech=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                            txtSearch.setText(speech.get(0));
                        }
                    }
                });
    }

    private void mostrarDialog(Context c) {//dialogo basico
        new AlertDialog.Builder(c)
                .setTitle(R.string.dialog_general)
                .setMessage(R.string.dialog_buscarDoc)
                .setPositiveButton("Aceptar", (dialog, which) -> {
                    //getActivity().finish();
                    dialog.dismiss();
                }).show();
    }

    //se ejecuta cuando precionemos enter o el icono buscar del teclado
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    //escucha cada vez q precionamos una tecla en el searView
    @Override
    public boolean onQueryTextChange(String newText) {
        if (aInternt.HayInternet()) {
            lisBusDocAdapter.filter(newText,checkBoxEstado);
            CBNombre.setEnabled(newText.equals(""));               //cheqea el stado del checkBox y lo imnabilita dependiendo
            CBApellido.setEnabled(newText.equals(""));
        } else mostrarDialog(getContext());
        return false;
    }

    //de la interface doctor para q retorne los datos o el error
    @Override
    public void exitoTraerDoctores(ArrayList<doctor> x) {
        lisBusDocAdapter=new listBuscDocXapellAdapter(x,getContext());
        recyclerView.setAdapter(lisBusDocAdapter);//de aca se va a error->toas
        //
        loadingDialog.dismissDialog();
    }

    @Override
    public void errorTraerDoctores(String y) {

    }
}