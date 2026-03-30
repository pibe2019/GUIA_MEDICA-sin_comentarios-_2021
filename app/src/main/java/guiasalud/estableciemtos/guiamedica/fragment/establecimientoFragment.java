package guiasalud.estableciemtos.guiamedica.fragment;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.textfield.TextInputLayout;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import guiasalud.estableciemtos.guiamedica.modelsEntities.establecimientoMapa;
import guiasalud.estableciemtos.guiamedica.R;
import guiasalud.estableciemtos.guiamedica.services.IRecepcionEstablecimientoCcordenads;
import guiasalud.estableciemtos.guiamedica.services.ServicioEstablecimientoCcordenads;
import guiasalud.estableciemtos.guiamedica.verificacionInternet;
import me.saket.bettermovementmethod.BetterLinkMovementMethod;
import pl.droidsonroids.gif.GifImageButton;

public class establecimientoFragment extends Fragment implements View.OnClickListener, IRecepcionEstablecimientoCcordenads {
    private String nombre,paginaWebe,facebooke;
    private int codigoEstablecimiento;
    private ImageView imgEstablecimiento;
    Fragment especialiddsYDoctoresFragmento;
    ServicioEstablecimientoCcordenads sec=new ServicioEstablecimientoCcordenads();
    private ProgressBar progressBar;
    private verificacionInternet aInternt;
    ImageButton pageWeb,imgfacebook;
    private Vibrator vb;
    TextInputLayout tilNumEmergencia;
    Context context;
    protected View mView;

    public establecimientoFragment() {
        // Required empty public constructor
    }

    public static establecimientoFragment createNewInstance(ArrayList<establecimientoMapa> especi){
        return new establecimientoFragment();
    }


    public static establecimientoFragment newInstance() {
        establecimientoFragment fragment = new establecimientoFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        especialiddsYDoctoresFragmento=new especialiddsYDocFragment();
        context=getContext();
    }
    private void onCopyAddressClick(Context context, String phoneUrl) {
        final ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        final ClipData clip = ClipData.newPlainText(context.getPackageName(), phoneUrl);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, R.string.toas_confir_copy, Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assert getArguments() != null;
        aInternt=new verificacionInternet(this.getActivity());
        codigoEstablecimiento= getArguments().getInt("codigoEstablecimiento");//--------
        return inflater.inflate(R.layout.fragment_establecimiento, container, false);
    }

    @SuppressLint("CutPasteId")
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (aInternt.HayInternet()) {
            sec.traerImagenDeEstabelcimeinto(getContext(),codigoEstablecimiento,this);
        }else mostrarDialog(this.getContext());
        this.mView = view;
        progressBar=view.findViewById(R.id.loadingProgresBar);
        LinearLayout lltAtencion=view.findViewById(R.id.llNuAtencion);
        LinearLayout llWhatss=view.findViewById(R.id.llWhatssap);
        LinearLayout llCorreo=view.findViewById(R.id.llEmail);
        LinearLayout llLicencia=view.findViewById(R.id.llLicencia);
        imgfacebook=view.findViewById(R.id.btnFacebook);
        pageWeb=view.findViewById(R.id.imgButtonWeb);
        assert getArguments() != null;
        paginaWebe= getArguments().getString("paginaWeb");//--------
        facebooke= getArguments().getString("facebook");//--------

        EditText edtnombre=view.findViewById(R.id.edtNombre);
        assert getArguments() != null;
        edtnombre.setText(getArguments().getString("nombre"));
        nombre=getArguments().getString("nombre");

        EditText edttipo=view.findViewById(R.id.edtTipo);
        edttipo.setText(getArguments().getString("tipo"));

        EditText edtdireccion=view.findViewById(R.id.edtDireccion);//-------
        edtdireccion.setText(getArguments().getString("direccion"));

        EditText edtnumeroAtencion=view.findViewById(R.id.edtNumeroAtencion);//-------
        if (getArguments().getString("numeroAtencion").length()<1) lltAtencion.setVisibility(View.GONE);
        else edtnumeroAtencion.setText(getArguments().getString("numeroAtencion"));

        EditText edtwhatssap=view.findViewById(R.id.edtWhatssap);//-------
        if(getArguments().getString("whatssap").length()<1) llWhatss.setVisibility(View.GONE);
        else edtwhatssap.setText(getArguments().getString("whatssap"));

        EditText edtcorreo=view.findViewById(R.id.edtCorreo);//-------
        if (getArguments().getString("correo").length()<1) llCorreo.setVisibility(View.GONE);
        else {
            edtcorreo.setText(getArguments().getString("correo"));
        }
        EditText edtnumeroEmergencia=view.findViewById(R.id.edtNumEmergencia);//-------
        tilNumEmergencia=view.findViewById(R.id.numEmergenciaTIL);

        if (getArguments().getString("numeroEmergencia").length()<1) tilNumEmergencia.setVisibility(View.GONE);
        else edtnumeroEmergencia.setText(getArguments().getString("numeroEmergencia"));

        EditText edtlicencia=view.findViewById(R.id.edtLicencia);//-------
        if (getArguments().getString("licencia").length()<1) llLicencia.setVisibility(View.GONE);
        else edtlicencia.setText(getArguments().getString("licencia"));

        EditText edtdescripcion=view.findViewById(R.id.edtDescripcion);//-------
        edtdescripcion.setText(getArguments().getString("descripcion"));

        imgEstablecimiento=view.findViewById(R.id.imageEstablecimiento);//--

        GifImageButton flecha=view.findViewById(R.id.flechaIcono);
        pageWeb.setOnClickListener(this);
        imgfacebook.setOnClickListener(this);
        flecha.setOnClickListener(this);

        BetterLinkMovementMethod.linkify(Linkify.ALL, edtnumeroAtencion,edtwhatssap,edtcorreo,edtnumeroEmergencia)
                .setOnLinkClickListener((textView, url) -> false).setOnLinkLongClickListener(((textView, url) -> {
            String[] copi=url.split(":");
            onCopyAddressClick(context, copi[1]);
            return true;
        }));
        vb=(Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (getActivity()!=null)
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
    }
    private void vibrar(){
        long[] pattern = {0, 38};//pause(dormid),vibra
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            vb.vibrate(VibrationEffect.createOneShot(38, VibrationEffect.DEFAULT_AMPLITUDE));
        }else vb.vibrate(pattern,-1);
        //if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) vb.vibrate(VibrationEffect.PARCELABLE_WRITE_RETURN_VALUE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btnFacebook){
            vibrar();
            if (facebooke.equals("")){
                Toast.makeText(getContext(), R.string.aviso_toas_fb_no, Toast.LENGTH_SHORT).show();
            }else{
                if (aInternt.HayInternet()) {
                    try {
                        Uri uri=Uri.parse(facebooke);
                        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                        startActivity(intent);
                        Toast.makeText(getContext(), R.string.aviso_toas_fb_si, Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(getContext(), R.string.aviso_toas_fb_error, Toast.LENGTH_SHORT).show();
                    }
                }else Toast.makeText(getContext(),R.string.toas_internet_no,Toast.LENGTH_SHORT).show();
            }
        }else if (v.getId()==R.id.imgButtonWeb){
            vibrar();
            if (paginaWebe.equals("")){
                Toast.makeText(getContext(), R.string.aviso_toas_web_no, Toast.LENGTH_SHORT).show();
            }else{
                if (aInternt.HayInternet()) {
                    try {
                        Uri uri=Uri.parse(paginaWebe);
                        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                        startActivity(intent);
                        Toast.makeText(getContext(), R.string.aviso_toas_web_si, Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(getContext(), R.string.aviso_toas_web_error, Toast.LENGTH_SHORT).show();
                    }
                }else Toast.makeText(getContext(),R.string.toas_internet_no,Toast.LENGTH_SHORT).show();
            }
        }else if (v.getId()==R.id.flechaIcono){
            if (aInternt.HayInternet()) {
                Bundle bundle = new Bundle();
                bundle.putString("nombre", nombre);
                bundle.putInt("codigoEstablecimiento", codigoEstablecimiento);
                Navigation.findNavController(mView).navigate(R.id.especialiddsYDocFragment, bundle);
            }else {
                mostrarDialogBtnFlecha(context);
            }
        }
    }

    @Override
    public void exitoCorde(ArrayList<establecimientoMapa> x, GoogleMap googleMap) {

    }
    List<establecimientoMapa> emImagen=new ArrayList<>();
    @Override
    public void exitoImagen(ArrayList<establecimientoMapa> x) {
        emImagen=x;
        if (emImagen.get(0).getImagen() != null){
            imgEstablecimiento.setImageBitmap(emImagen.get(0).getImagen());
        }else {
            imgEstablecimiento.setImageResource(R.drawable.imagen);
        }
        progressBar.setVisibility(View.GONE);
        imgEstablecimiento.setVisibility(View.VISIBLE);
    }

    @Override
    public void errorCorde(String x) {

    }
    private void mostrarDialog(Context c) {
        new AlertDialog.Builder(c)
                .setTitle(R.string.dialog_general)
                .setMessage(R.string.dialog_imagen_internet_no)
                .setPositiveButton("Aceptar", (dialog, which) -> dialog.dismiss()).show();
    }
    private void mostrarDialogBtnFlecha(Context c) {
        new AlertDialog.Builder(c)
                .setTitle(R.string.dialog_general)
                .setMessage(R.string.dialog_btnflecha_internet_no)
                .setPositiveButton("Aceptar", (dialog, which) -> dialog.dismiss()).show();
    }
}