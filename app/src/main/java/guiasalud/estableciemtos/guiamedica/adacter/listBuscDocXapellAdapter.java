package guiasalud.estableciemtos.guiamedica.adacter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.media.audiofx.AudioEffect;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import guiasalud.estableciemtos.guiamedica.modelsEntities.doctor;
import guiasalud.estableciemtos.guiamedica.R;
import guiasalud.estableciemtos.guiamedica.verificacionInternet;
import pl.droidsonroids.gif.GifImageButton;

public class listBuscDocXapellAdapter extends RecyclerView.Adapter<listBuscDocXapellAdapter.ViewHolderBuscDoc> {
    private List<doctor>mData;//almacena el listado original y no cambia cuando busca
    private final List<doctor>ItemsCambia;//este cambiara de acuerdo a la busqueda
    private final LayoutInflater mInflater;
    private final Context context;
    private verificacionInternet aInternt;
    private Vibrator vb;

    //este es el construtor con parametros
    public listBuscDocXapellAdapter(List<doctor> itemsList,Context context){
        this.mInflater=LayoutInflater.from(context);
        this.context=context;
        this.ItemsCambia=itemsList;//cambia deacuerdo a la busqueda
        this.mData=new ArrayList<>();
        mData.addAll(itemsList);
    }
    @Override
    public int getItemCount() {
        try {
            return ItemsCambia.size();
        }catch (Exception e){return 0;}
    }

    @NotNull
    @Override
    public listBuscDocXapellAdapter.ViewHolderBuscDoc onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.buscarlista,parent,false);
        aInternt=new verificacionInternet(context);
        return new listBuscDocXapellAdapter.ViewHolderBuscDoc(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull listBuscDocXapellAdapter.ViewHolderBuscDoc holder, int position) {
        holder.bindData(ItemsCambia.get(position));
    }


    //puede servir para hacer un listado nuevo,vuelves a definir todo los items de esta lista
    public void setItems(List<doctor> items){mData=items;}

    //recibe lo q escribimos en el searchView a: nombre,b:apellido, d:especialidad
    public void filter(String strSearch,String estadoRB){
        if (strSearch.length()==0){
            ItemsCambia.clear();
            ItemsCambia.addAll(mData);
        }else {
            //ollenteEscrivio=true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//solo funciona la versiones de android 'N' en adelante
                switch (estadoRB) {
                    case "b":
                        ItemsCambia.clear();
                        List<doctor> collect = mData.stream()
                                .filter(i -> i.getApellido().toLowerCase().contains(strSearch.toLowerCase()))
                                .collect(Collectors.toList());//debuelve un listado de items
                        ItemsCambia.addAll(collect);
                        break;
                    case "a":
                        ItemsCambia.clear();
                        List<doctor> collect2 = mData.stream()
                                .filter(i -> i.getNombre().toLowerCase().contains(strSearch.toLowerCase()))
                                .collect(Collectors.toList());//debuelve un listado de items
                        ItemsCambia.addAll(collect2);
                        break;
                    case "d":
                        ItemsCambia.clear();
                        List<doctor> collect3 = mData.stream()
                                .filter(i -> i.getEspecialidad().toLowerCase().contains(strSearch.toLowerCase()))
                                .collect(Collectors.toList());//debuelve un listado de items
                        ItemsCambia.addAll(collect3);
                        break;
                }
            }else {
                switch (estadoRB) {
                    case "b":
                        ItemsCambia.clear();
                        //comparamos items x items
                        for (doctor i : mData) {
                            if (i.getApellido().toLowerCase().contains(strSearch.toLowerCase())) {
                                ItemsCambia.add(i);
                            }
                        }
                        break;
                    case "a":
                        for (doctor i : mData) {
                            if (i.getNombre().toLowerCase().contains(strSearch.toLowerCase())) {
                                ItemsCambia.add(i);
                            }
                        }
                        break;
                    case "d":
                        for (doctor i : mData) {
                            if (i.getEspecialidad().toLowerCase().contains(strSearch.toLowerCase())) {
                                ItemsCambia.add(i);
                            }
                        }
                        break;
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolderBuscDoc extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView nomEstablecimiento,nombreCompleto,rne,precio,especialidadDoc,NumColegiatura12;
        private final TextInputEditText tiedescripTipoAtencion,tieNumColegiatura;
        private final TextInputLayout TILcolegiatura,TILverificarColegiatura;
        private String numeroTelef,enlaceFaceb,enlaceInstag,nombreDoc;
        private final ImageView iconDoctors;
        private final RelativeLayout expand,click;
        private final LinearLayout llcbda;
        private final TextInputEditText tieImagen;
        private final Button botonMostrarDialog;

        ViewHolderBuscDoc(View itemView){
            super(itemView);
            //iconos
            iconDoctors=itemView.findViewById(R.id.iconEspecialiddocBuscar);
            tieImagen=itemView.findViewById(R.id.TIDConImgDocBuscar);
            //
            nomEstablecimiento=itemView.findViewById(R.id.textEstablecimientoBuscarDoc);
            especialidadDoc=itemView.findViewById(R.id.textEspecialidadDocBuscar);
            nombreCompleto=itemView.findViewById(R.id.textNombreYApellidoDocBuscar);
            rne=itemView.findViewById(R.id.textRNEBuscarDoc);
            NumColegiatura12=itemView.findViewById(R.id.textNumColegiaturaBuscarDoc);//Solo es visible cuando no hay RNE
            NumColegiatura12.setVisibility(View.GONE);
            tiedescripTipoAtencion=itemView.findViewById(R.id.TIEDescripcionAtencionDocBuscar);

            tieNumColegiatura=itemView.findViewById(R.id.TIENumColegiaturaDocBuscar);

            TILcolegiatura=itemView.findViewById(R.id.idColegDocBuscar);
            TILverificarColegiatura=itemView.findViewById(R.id.idVerifiColegDocBuscar);

            botonMostrarDialog = itemView.findViewById(R.id.btnContactameDocBuscar);
            precio=itemView.findViewById(R.id.textPrecioDocBuscar);
            //
            llcbda=itemView.findViewById(R.id.LLcardBuscarDocApellido);
            //
            CardView cardDoctor=itemView.findViewById(R.id.docListaCardviewBuscar);
            expand=itemView.findViewById(R.id.RLexpandibleBuscarDoc);
            click=itemView.findViewById(R.id.RLClickBuscarDoc);

            vb=(Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

            //provando
            /**/
            click.setOnClickListener(v -> {
                doctor doc =ItemsCambia.get(getAdapterPosition());
                doc.setEstadoCardV(!doc.isEstadoCardV());
                notifyItemChanged(getAdapterPosition());
            });

        }
        @SuppressLint("SetTextI18n")
        void bindData(doctor item){
            boolean isVisible= item.isEstadoCardV();
            expand.setVisibility(isVisible ? View.VISIBLE : View.GONE);
            numeroTelef= item.getNuContacto();
            enlaceFaceb= item.getDirFacebook();
            enlaceInstag= item.getDirInstagram();
            String longitud= item.getLongitudEstableci();
            String latitud= item.getLatitudEstableci();
            //
            nomEstablecimiento.setText(item.getNombreEstableci());
            especialidadDoc.setText(item.getEspecialidad());
            nombreCompleto.setText(item.getApellido()+" "+item.getNombre());
            nombreDoc=item.getApellido()+" "+item.getNombre();
            String nuColegiatura=item.getNumColegiatura();
            if (item.getRne().equals("")){
                rne.setVisibility(View.GONE);
                if(!nuColegiatura.equals("")){
                    NumColegiatura12.setText(context.getString(R.string.iniciales_cmp)+" "+nuColegiatura);
                    NumColegiatura12.setVisibility(View.VISIBLE);
                    TILcolegiatura.setVisibility(View.VISIBLE);
                    TILverificarColegiatura.setVisibility(View.VISIBLE);
                }else {
                    NumColegiatura12.setVisibility(View.GONE);
                    TILcolegiatura.setVisibility(View.GONE);
                    TILverificarColegiatura.setVisibility(View.GONE);
                }
            }else {
                rne.setText(context.getString(R.string.iniciales_rne)+" "+(item.getRne()));
                rne.setVisibility(View.VISIBLE);
                NumColegiatura12.setVisibility(View.GONE);
                TILcolegiatura.setVisibility(View.VISIBLE);
                TILverificarColegiatura.setVisibility(View.VISIBLE);
            }

            tiedescripTipoAtencion.setText(item.getDescripTipoAtencioDoc());

            tieNumColegiatura.setText(item.getNumColegiatura());

            if (item.getPrecio()<1) precio.setVisibility(View.GONE);
            else {
                precio.setText( context.getString(R.string.precio_consulta)+" "+item.getPrecio());
                precio.setVisibility(View.VISIBLE);
            }

            //iconos
            Picasso.get()
                    .load("https://img.icons8.com/dusk/112/000000/stethoscope.png")
                    .into(iconDoctors);

            Random random=new Random();
            //new Handler().post(() -> {
            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(36);
            gd.setStroke(4,Color.argb(110, random.nextInt(255), random.nextInt(255), random.nextInt(155)));
            llcbda.setBackground(gd);//click  llcbda expand
            //});

            tieNumColegiatura.setOnLongClickListener(v -> {
                onCopyAddressClick(context, nuColegiatura);
                return true;
            });
            ///////
            if(numeroTelef.equals("") && enlaceFaceb.equals("") && enlaceInstag.equals("")){
                botonMostrarDialog.setVisibility(View.GONE);
            }else botonMostrarDialog.setVisibility(View.VISIBLE);
            ////////
            rne.setOnClickListener(this);
            NumColegiatura12.setOnClickListener(this);//toas de las iniciales CMP
            tieNumColegiatura.setOnClickListener(this);
            botonMostrarDialog.setOnClickListener(this);
            tieImagen.setOnClickListener(v -> {
                if (aInternt.HayInternet()){
                    Uri uri=Uri.parse("https://www.cmp-trujillo.org.pe/conoce-tu-medico");
                    Intent intent=new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }else mostrarDialog(context);
            });
        }
        //////////
        @SuppressLint("SetTextI18n")
        private void avisoPermissContactame2(Context c){
            final GifImageButton iconllamarDialog;
            final ImageButton iconInstagramDialog,iconFacebookDialog;
            AlertDialog.Builder builder=new AlertDialog.Builder(c);
            View view=mInflater.inflate(R.layout.dialogo_contactame,null);
            builder.setView(view);
            AlertDialog dialog=builder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            TextView txt=view.findViewById(R.id.text_dialog);
            txt.setTextSize(14);
            txt.setText(R.string.subTitle_comunicasion);
            Button btn=view.findViewById(R.id.btn_dialogo_ok);
            TextInputEditText tietnombre=view.findViewById(R.id.TIETdialog_docNombre);
            tietnombre.setText(nombreDoc);
            iconllamarDialog=view.findViewById(R.id.iconLlamarDocDialog);
            iconInstagramDialog=view.findViewById(R.id.iconInstagramDocDialog);
            iconFacebookDialog=view.findViewById(R.id.iconFacebookDocDialog);
            //logica iconos
            if (numeroTelef.equals("")){
                iconllamarDialog.setVisibility(View.GONE);
            }
            if (enlaceFaceb.equals("")){
                iconFacebookDialog.setVisibility(View.GONE);
            }
            if (enlaceInstag.equals("")){
                iconInstagramDialog.setVisibility(View.GONE);
            }
            //evento de botones
            btn.setOnClickListener(v -> dialog.dismiss());
            iconllamarDialog.setOnClickListener(this);
            iconInstagramDialog.setOnClickListener(this);
            iconFacebookDialog.setOnClickListener(this);
        }
        //
        //onclick
        private void vibrar(){
            long[] pattern = {0, 38};//pause(dormid),vibra
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                vb.vibrate(VibrationEffect.createOneShot(38, VibrationEffect.DEFAULT_AMPLITUDE));
            }else vb.vibrate(pattern,-1);
        }

        @Override
        public void onClick(View v) {
            if (v.getId()==R.id.textRNEBuscarDoc){
                vibrar();
                Toast.makeText(context,R.string.rne_todo,Toast.LENGTH_SHORT).show();
            }else if (v.getId()==R.id.textNumColegiaturaBuscarDoc){
                vibrar();
                Toast.makeText(context,R.string.cmp_todo,Toast.LENGTH_SHORT).show();
            }else if (v.getId()==R.id.btnContactameDocBuscar){
                avisoPermissContactame2(context);
            }else if (v.getId()==R.id.TIENumColegiaturaDocBuscar){
                vibrar();
                Toast.makeText(context, R.string.toas_presione, Toast.LENGTH_SHORT).show();
            }else if (v.getId()== R.id.iconLlamarDocDialog){
                vibrar();
                int permiso= ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);
                if (permiso != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(context,R.string.permiso_llamada,Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE},255);
                }else {
                    String numero="tel:"+numeroTelef;
                    Intent i=new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse(numero));
                    context.startActivity(i);
                }
            }else if(v.getId()== R.id.iconFacebookDocDialog){
                vibrar();
                if (aInternt.HayInternet()) {
                    try {
                        Uri uriWfb;
                        Intent intent;
                        uriWfb = Uri.parse(enlaceFaceb);
                        intent = new Intent(Intent.ACTION_VIEW, uriWfb);
                        context.startActivity(intent);
                        Toast.makeText(context, R.string.facebook_ver, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(context, R.string.facebook_error, Toast.LENGTH_SHORT).show();
                    }
                }else Toast.makeText(context,R.string.no_internet,Toast.LENGTH_SHORT).show();
            }else if(v.getId()== R.id.iconInstagramDocDialog){
                vibrar();
                if (aInternt.HayInternet()) {
                    try {
                        Uri uri = Uri.parse(enlaceInstag);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        context.startActivity(intent);
                        Toast.makeText(context, R.string.instagran_ver, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(context, R.string.instagran_error, Toast.LENGTH_SHORT).show();
                    }
                }else Toast.makeText(context,R.string.no_internet,Toast.LENGTH_SHORT).show();
            }else if (v.getId()== R.id.TIENumColegiaturaDocBuscar){
                vibrar();
                Toast.makeText(context, R.string.toas_presione, Toast.LENGTH_SHORT).show();
            }
        }


    }
    //afuera
    private void mostrarDialog(Context c) {//dialogo basico
        new AlertDialog.Builder(c)
                .setTitle(R.string.dialog_general)
                .setMessage(R.string.error_internet_webColegioDeAbogads)
                .setPositiveButton("Aceptar", (dialog, which) -> {
                    //getActivity().finish();
                    dialog.dismiss();
                }).show();
    }
    //copiar al portaPapeles
    private void onCopyAddressClick(Context context, String phoneUrl) {
        final ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        final ClipData clip = ClipData.newPlainText(context.getPackageName(), phoneUrl);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, R.string.toas_confir_copy, Toast.LENGTH_SHORT).show();
    }
}
