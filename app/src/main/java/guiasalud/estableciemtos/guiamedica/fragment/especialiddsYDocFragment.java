package guiasalud.estableciemtos.guiamedica.fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.jetbrains.annotations.NotNull;
import guiasalud.estableciemtos.guiamedica.R;
import guiasalud.estableciemtos.guiamedica.verificacionInternet;

public class especialiddsYDocFragment extends Fragment implements View.OnClickListener {
    private int codigoEstablecimiento;
    private LinearLayout llCardView;
    private verificacionInternet aInternt;
    Context context;



    public static especialiddsYDocFragment newInstance() {
        especialiddsYDocFragment fragment = new especialiddsYDocFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public especialiddsYDocFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context=getContext();
        aInternt=new verificacionInternet(getContext());
        return inflater.inflate(R.layout.fragment_especialidds_y_doc, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Animation animation1= AnimationUtils.loadAnimation(getContext(), R.anim.animationcovid1);
        Animation animation2= AnimationUtils.loadAnimation(getContext(), R.anim.animationcovid2);
        Animation animation3= AnimationUtils.loadAnimation(getContext(), R.anim.animationcovid3);
        Animation animation4= AnimationUtils.loadAnimation(getContext(), R.anim.animationcovid4);
        assert getArguments() != null;
        codigoEstablecimiento=getArguments().getInt("codigoEstablecimiento");
        //Toast.makeText(view.getContext(),"codigo traido es:"+getArguments().getInt("codigoEstablecimiento"),Toast.LENGTH_LONG).show();
        //txtcodigo.setText(String.valueOf(getArguments().getInt("codigoEstablecimiento")));
        Button especialidd=view.findViewById(R.id.btnEspecilaidds);
        Button doc=view.findViewById(R.id.btnDoc);
        llCardView=view.findViewById(R.id.idVistaUnaVez);
        CardView card1=view.findViewById(R.id.cardSignosAlarma);
        card1.setAnimation(animation4);
        CardView card2=view.findViewById(R.id.cardComoProtegerte);
        card2.setAnimation(animation2);
        CardView card3=view.findViewById(R.id.cardVulnerables);
        card3.setAnimation(animation1);
        TextView text=view.findViewById(R.id.cabecera);
        text.setAnimation(animation3);
        especialidd.setOnClickListener(this);
        doc.setOnClickListener(this);
    }
    @Override
    public void onResume() {
        super.onResume();
        if (getActivity()!=null)
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }


    //especialiddsFragment especFrag=new especialiddsFragment();
    @Override
    public void onClick(View v) {
        if (aInternt.HayInternet()){
            if (v.getId()==R.id.btnEspecilaidds){
                llCardView.setVisibility(View.GONE);
                try {
                    Fragment f;
                    if (getActivity() != null){
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    f = especialiddsFragment.createNewInstance(codigoEstablecimiento);
                    transaction.replace(R.id.contenedorFragment, f, getString(R.string.transaction_replace_especialidades));
                    transaction.commit();
                }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }else if (v.getId()==R.id.btnDoc){
                llCardView.setVisibility(View.GONE);
                try {
                    if (getActivity() != null) {
                        Fragment f;
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        f = docFragment.createNewInstance(codigoEstablecimiento);
                        transaction.replace(R.id.contenedorFragment, f, getString(R.string.transaction_replace_doctores));
                        transaction.commit();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }else mostrarDialog(getContext());


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

}