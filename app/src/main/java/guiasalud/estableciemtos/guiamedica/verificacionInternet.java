package guiasalud.estableciemtos.guiamedica;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

public class verificacionInternet {
    public Context con;

    public verificacionInternet(Context con){this.con=con;}

    public Context getCon() {
        return con;
    }

    public void setCon(Context con) {
        this.con = con;
    }

    public Boolean HayInternet(){
        //api > m(23)
        //api>p(28)
        ConnectivityManager cm = (ConnectivityManager) con.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        /* NetworkInfo is deprecated in API 29 so we have to check separately for higher API Levels */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {//p=28
            Network network = cm.getActiveNetwork();
            if (network == null) {
                return false;
            }
            NetworkCapabilities networkCapabilities = cm.getNetworkCapabilities(network);
            if (networkCapabilities == null) {
                return false;
            }
            boolean isInternetSuspended = !networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_SUSPENDED);
            return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                    && !isInternetSuspended;
        } else {
            //Toast.makeText(con,"entro a api 24 ",Toast.LENGTH_SHORT).show();

            RunnableFuture<Boolean> futureRun = new FutureTask<>(() -> {
                    try {
                        HttpURLConnection urlc = (HttpURLConnection) (new URL("https://www.google.com").openConnection());
                        urlc.setRequestProperty("User-Agent", "Test");
                        urlc.setRequestProperty("Connection", "close");
                        urlc.setConnectTimeout(1500);
                        urlc.connect();
                        return (urlc.getResponseCode() == 200);
                    } catch (IOException e) {
                        e.getStackTrace();
                    }
                return false;
            });

            new Thread(futureRun).start();

            try {
                return futureRun.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return false;
            }
        }
        /*else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
        }else return false;*/
    }
    //comprovar si hay acceso a internet haciendo ping
    public Boolean isOnlineNet() {
        /*try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val           = p.waitFor();
            return (val == 0);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        return false;
    }
    /*
    public boolean isOnline(){
        boolean res=false;
        ConnectivityManager cm;
        NetworkInfo ni;
        cm=(ConnectivityManager) this.con.getSystemService(Context.CONNECTIVITY_SERVICE);
        ni =cm.getActiveNetworkInfo();
        boolean tipoConexion1=false;
        boolean tipoConexion2=false;
        if (ni != null){
            ConnectivityManager connectivityManager1=(ConnectivityManager) this.con.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo mWifi=connectivityManager1.getActiveNetworkInfo();
            boolean resultado=mWifi != null && mWifi.isConnected();
            Toast.makeText(con,"hay internet??? : "+resultado,Toast.LENGTH_SHORT).show();
            Log.i("no hay INTERNET",""+ resultado);//provaraaa
            //ConnectivityManager connectivityManager2=(ConnectivityManager) this.con.getSystemService(Context.CONNECTIVITY_SERVICE);
            //NetworkInfo mMobile=connectivityManager1.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if(resultado){
                if (isOnlineNet()) res = true;
            }
            //if (mWifi.isConnected()){
              //  if (isOnlineNet()) tipoConexion1 = true;
            //}
            //if (mMobile.isConnected()){
              //  if (isOnlineNet()) tipoConexion2 = true;
            //}
            // Estas conectado a internet usando wifi o redes moviles, puedes enviar tus datos
            //if (tipoConexion1 || tipoConexion2) res=true;
        }
        //progressDialog.dismiss();
        return res;


    }
    */
}
