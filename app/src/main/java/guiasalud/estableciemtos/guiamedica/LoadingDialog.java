package guiasalud.estableciemtos.guiamedica;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.appcompat.app.AlertDialog;

public class LoadingDialog {
    Context context;
    AlertDialog alertDialog;

    public LoadingDialog(Context myContext){
        context = myContext;
    }

    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        final ViewGroup nullParent = null;//para q desaparesca el warning cuando pones "null"
        LayoutInflater inflater =  LayoutInflater.from(context);
        builder.setView(inflater.inflate(R.layout.dialogprogressbar,nullParent));
        //builder.setView(view);
        builder.setCancelable(true);

        alertDialog = builder.create();
        //alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back,30,0,30,0);
        alertDialog.getWindow().setBackgroundDrawable(inset);
        alertDialog.getWindow().setElevation(20);

        alertDialog.show();
    }
    public void dismissDialog(){
        alertDialog.dismiss();
    }
}
