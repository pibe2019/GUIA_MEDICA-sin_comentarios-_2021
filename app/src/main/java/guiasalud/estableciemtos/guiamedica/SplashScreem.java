package guiasalud.estableciemtos.guiamedica;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreem extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    verificacionInternet aInternt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screem);

        aInternt=new verificacionInternet(this);
        //ANIMACIONES
        Animation animation1= AnimationUtils.loadAnimation(this,R.anim.desplazamiento_arriba);//android.view.aniamtion
        Animation animation2=AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo);
        ImageView logoImagen=findViewById(R.id.imageViewDoctor);
        TextView Guia=findViewById(R.id.textGuia);
        TextView salud=findViewById(R.id.textMedica);
        logoImagen.setAnimation(animation1);
        Guia.setAnimation(animation2);
        salud.setAnimation(animation2);
        activarMyLocation();
    }
    private void splashScreen(){
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (aInternt.HayInternet()){
                Intent inte= new Intent(SplashScreem.this, MainActivity.class);
                startActivity(inte);
                finish();
            }else{
                mostrarDialog();
            }
        },3000);
    }

    private void mostrarDialog() {//dialogo basico
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.R){
            new AlertDialog.Builder(this)
                    .setTitle(R.string.dialog_general)
                    .setCancelable(false)
                    .setMessage(R.string.dialog_splashScreem)
                    .setPositiveButton(android.R.string.ok,(dialog, which) -> finish())
                    .show();
        }else{
            new AlertDialog.Builder(this)
                    .setTitle(R.string.dialog_general)
                    .setCancelable(false)
                    .setMessage(R.string.dialog_splashScreem)
                    .setPositiveButton("Aceptar", (dialog, which) -> finish())
                    .show();
        }

    }

    private void activarMyLocation() {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            splashScreen();
        } else {
            avisoPermiss(this);
        }
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        //R=API30
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.R){
            final WindowInsetsController insetsController = getWindow().getInsetsController();
            if (insetsController != null){
                insetsController.hide(WindowInsets.Type.statusBars());
            }
        }else {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        }

        try {
            if (android.os.Build.VERSION.SDK_INT != Build.VERSION_CODES.O){
                if (this.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
                    this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    private void avisoPermiss(Context c){
        AlertDialog.Builder builder=new AlertDialog.Builder(c);
        LayoutInflater inflater=getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog,null);
        builder.setView(view);
        AlertDialog dialog=builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
        TextView txt=view.findViewById(R.id.text_dialog);
        txt.setTextSize(14);
        txt.setText(R.string.dialog_splashScreem_permiso_ubicacion);
        Button btn=view.findViewById(R.id.btn_dialogo_ok);
        btn.setOnClickListener(v -> {
            dialog.dismiss();
            //runOnUiThread(() -> {
                ActivityCompat.requestPermissions(SplashScreem.this, new String[]
                                {Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
                ultimoPermiss(SplashScreem.this);
            //});
        });
    }

    @SuppressLint("SetTextI18n")
    private void ultimoPermiss(Context c){
        AlertDialog.Builder builder=new AlertDialog.Builder(c);
        LayoutInflater inflater=getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog,null);
        builder.setView(view);
        AlertDialog dialog=builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.setCancelable(false);
        TextView txt=view.findViewById(R.id.text_dialog);
        txt.setTextSize(17);
        txt.setText("Entrar...");
        Button btn=view.findViewById(R.id.btn_dialogo_ok);
        btn.setOnClickListener(v -> {
            dialog.dismiss();
            splashScreen();
        });
    }

}