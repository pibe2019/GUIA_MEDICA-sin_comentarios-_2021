package guiasalud.estableciemtos.guiamedica.adacter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

import guiasalud.estableciemtos.guiamedica.modelsEntities.especialidadMedica;
import guiasalud.estableciemtos.guiamedica.R;

public class listEspecialidAdapter extends RecyclerView.Adapter<listEspecialidAdapter.ViewHolderEspecialidds> {
    //relaciona los datos con el cardview que hicimos "doclist.xml y especialiddlista.xml"
    private List<especialidadMedica>mData;
    private final LayoutInflater mInflater;//ayuda q describrir de que layaut, de q archivo proviene...infla
    private final Context context;//para saber de q clase se esta llamando a ste adaptador

    public listEspecialidAdapter(List<especialidadMedica> itemList, Context context){
        this.mInflater=LayoutInflater.from(context);
        this.context=context;
        this.mData=itemList;
    }

    @Override
    public int getItemCount(){
        try {
            return mData.size();
        }catch (Exception e){return 0;}

    }//nos devuelve le tamaño de la lista

    @NotNull
    @Override
    public listEspecialidAdapter.ViewHolderEspecialidds onCreateViewHolder(@NotNull ViewGroup parent, int viewType){
        @SuppressLint("InflateParams")
        View view=mInflater.inflate(R.layout.especialiddlista,null);
        return new ViewHolderEspecialidds(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull final listEspecialidAdapter.ViewHolderEspecialidds holder,final int position) {
        //super.onBindViewHolder(holder, position, payloads);
        holder.cvEspecialidds.setAnimation(AnimationUtils.loadAnimation(context,R.anim.slide));
        holder.bindData(mData.get(position));
    }

    //puede servir para hacer un listado nuevo vuelves a definir todo los items de esta lista
     public void setItems(List<especialidadMedica> items){mData=items;}

     public static class ViewHolderEspecialidds extends RecyclerView.ViewHolder{
        TextView especialidd,tipoServiceESE;
        ImageView icono;
        CardView cvEspecialidds;
        RelativeLayout rlcv;
         LinearLayout llprovar;

        ViewHolderEspecialidds(View itemView){
            super(itemView);
            especialidd=itemView.findViewById(R.id.datoEspecialidd);
            tipoServiceESE=itemView.findViewById(R.id.datoEspecialiddTipoService);
            icono=itemView.findViewById(R.id.iconEspecialidd);
            cvEspecialidds=itemView.findViewById(R.id.espeListaCardview);
            rlcv=itemView.findViewById(R.id.rlCardView);
            llprovar=itemView.findViewById(R.id.idllprovar);
        }
        void bindData(final especialidadMedica item){
            especialidd.setText(item.getNombreEspecialidad());
            tipoServiceESE.setText(item.getTipoServicioESE());

            Random random=new Random();
            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(23);
            gd.setStroke(2, Color.argb(160, random.nextInt(255), random.nextInt(255), random.nextInt(155)));
            llprovar.setBackground(gd);
            Picasso.get()
                    .load("https://img.icons8.com/doodle/68/000000/doctors-bag.png")
                    .into(icono);
        }

     }
}
