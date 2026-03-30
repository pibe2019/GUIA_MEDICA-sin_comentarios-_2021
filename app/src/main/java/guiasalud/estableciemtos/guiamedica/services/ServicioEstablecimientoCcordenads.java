package guiasalud.estableciemtos.guiamedica.services;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import guiasalud.estableciemtos.guiamedica.R;
import guiasalud.estableciemtos.guiamedica.modelsEntities.PolylineData;
import guiasalud.estableciemtos.guiamedica.modelsEntities.establecimientoMapa;

public class ServicioEstablecimientoCcordenads {
    private RequestQueue queuee;
    //trae todo los establecimientos
    public void coordenadas(final Context context, final IRecepcionEstablecimientoCcordenads x, GoogleMap googleMap ){
        if (queuee==null) queuee= Volley.newRequestQueue(context);
        String url = "https://guiamedica.xyz/servicios/datosEstablecimiento.php";
        JsonObjectRequest post=new JsonObjectRequest(
                Request.Method.POST, url, null,
                response -> {
                    establecimientoMapa objeMapa;
                    JSONArray arrax = response.optJSONArray("establecimientos");
                    if (arrax != null) {
                    try {
                        ArrayList<establecimientoMapa> listaEstablecimiento = new ArrayList<>();
                        for (int i = 0; i < arrax.length(); i++) {
                            objeMapa = new establecimientoMapa();
                            JSONObject jSOB;
                            jSOB = arrax.getJSONObject(i);
                            objeMapa.setCodigoEst(jSOB.optInt("codigoEstablecimiento"));
                            objeMapa.setNombreEst(jSOB.optString("nombreEstablecimiento"));
                            objeMapa.setTipoEst(jSOB.optString("tipoEstablecimiento"));
                            objeMapa.setDireccionEst(jSOB.optString("direccionEstablecimiento"));
                            objeMapa.setLongitudEst(jSOB.optDouble("longitudEstablecimiento"));
                            objeMapa.setLatitudEst(jSOB.optDouble("latitudEstablecimiento"));
                            objeMapa.setAginawebEst(jSOB.optString("paginawebEstablecimiento"));
                            objeMapa.setFacebookEst(jSOB.optString("facebookEstablecimiento"));
                            objeMapa.setNumeroAtencionEst(jSOB.optString("numeroDeAtencionEstablecimiento"));
                            objeMapa.setWhatssapEst(jSOB.optString("whatssapEstablecimiento"));
                            objeMapa.setCorreoInstitucionalEst(jSOB.optString("correoInstitucionalEstablecimiento"));
                            objeMapa.setNumeroEmergenciaEst(jSOB.optString("numeroDeEmergenciaEstablecimiento"));
                            objeMapa.setLicenciaFuncionamientoEst(jSOB.optString("licenciaDeFuncionamientoEstablecimiento"));
                            objeMapa.setDescripcionServiciosEst(jSOB.optString("descripcionServicioEstablecimiento"));
                            objeMapa.setCodigoUsuarioRepresentante(jSOB.optInt("codigoUsuarioRepresentante"));

                            listaEstablecimiento.add(objeMapa);
                        }
                        x.exitoCorde(listaEstablecimiento, googleMap);
                    } catch (Exception e) {
                       e.printStackTrace();
                    }
                }else x.errorCorde("0");//falta el else si desea (valor q significa q no hay datos q obtener)
                }, error -> Toast.makeText(context, R.string.toas_problem_coordenadas,Toast.LENGTH_SHORT).show()
        );
        queuee.add(post);
    }
    //solo establecimientos de acuerdo a la especialidad seleccionada
    public void establecimientosPorEspecialidad(final Context context,int codEspecialidad ,final IRecepcionEstablecimientoCcordenads x,GoogleMap googleMap){
        if (queuee==null) queuee= Volley.newRequestQueue(context);
        //RequestQueue queue= Volley.newRequestQueue(context);
        String url = "https://guiamedica.xyz/servicios/datosEstablecimientosSegunEspecialidad.php";
        Map<String,Integer> parametros=new HashMap<>();
        parametros.put("codEspecialidd",codEspecialidad);
        JsonObjectRequest post=new JsonObjectRequest(
                Request.Method.POST, url, new JSONObject(parametros),
                response -> {
                    establecimientoMapa objeMapa;
                    JSONArray arrax = response.optJSONArray("establecimientosxespecialidd");
                    if (arrax != null){
                    try {
                        ArrayList<establecimientoMapa> listaEstablecimiento = new ArrayList<>();
                        for (int i = 0; i < arrax.length(); i++) {
                            objeMapa = new establecimientoMapa();
                            JSONObject jSOB;
                            jSOB = arrax.getJSONObject(i);

                            objeMapa.setCodigoEst(jSOB.optInt("codigoEstablecimiento"));
                            objeMapa.setNombreEst(jSOB.optString("nombreEstablecimiento"));
                            objeMapa.setTipoEst(jSOB.optString("tipoEstablecimiento"));
                            objeMapa.setDireccionEst(jSOB.optString("direccionEstablecimiento"));
                            objeMapa.setLongitudEst(jSOB.optDouble("longitudEstablecimiento"));
                            objeMapa.setLatitudEst(jSOB.optDouble("latitudEstablecimiento"));
                            objeMapa.setAginawebEst(jSOB.optString("paginawebEstablecimiento"));
                            objeMapa.setFacebookEst(jSOB.optString("facebookEstablecimiento"));

                            objeMapa.setNumeroAtencionEst(jSOB.optString("numeroDeAtencionEstablecimiento"));
                            objeMapa.setWhatssapEst(jSOB.optString("whatssapEstablecimiento"));
                            objeMapa.setCorreoInstitucionalEst(jSOB.optString("correoInstitucionalEstablecimiento"));
                            objeMapa.setNumeroEmergenciaEst(jSOB.optString("numeroDeEmergenciaEstablecimiento"));
                            objeMapa.setLicenciaFuncionamientoEst(jSOB.optString("licenciaDeFuncionamientoEstablecimiento"));
                            objeMapa.setDescripcionServiciosEst(jSOB.optString("descripcionServicioEstablecimiento"));
                            objeMapa.setCodigoUsuarioRepresentante(jSOB.optInt("codigoUsuarioRepresentante"));
                            listaEstablecimiento.add(objeMapa);
                        }
                        x.exitoCorde(listaEstablecimiento,googleMap);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, R.string.toas_exepcion_coordenadas, Toast.LENGTH_LONG).show();
                    }
                    }//falta el else si desea (valor q significa q no hay datos q obtener)

                }, error -> Toast.makeText(context, R.string.toas_problem_coordenadas, Toast.LENGTH_LONG).show()
        );
        queuee.add(post);
    }
    //solo trae imagen
    public void traerImagenDeEstabelcimeinto(final Context context,int codEstablecimiento ,final IRecepcionEstablecimientoCcordenads x) {
        if (queuee==null) queuee= Volley.newRequestQueue(context);
        //RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://guiamedica.xyz/servicios/datoEstablecimientoImagen.php";
        Map<String, Integer> parametros = new HashMap<>();
        parametros.put("codEstablecimiento", codEstablecimiento);
        JsonObjectRequest post=new JsonObjectRequest(
                Request.Method.POST, url, new JSONObject(parametros),
                response -> {
                    establecimientoMapa objeMapa;
                    JSONArray arrax = response.optJSONArray("imagenDeEstablecimiento");
                    if (arrax != null) {
                        try {
                            ArrayList<establecimientoMapa> listaEstablecimiento = new ArrayList<>();
                            for (int i = 0; i < arrax.length(); i++) {
                                objeMapa = new establecimientoMapa();
                                JSONObject jSOB;
                                jSOB = arrax.getJSONObject(i);
                                objeMapa.setDato(jSOB.optString("imagenEstablecimiento"));
                                objeMapa.setTipo(jSOB.optString("tipoImagenEstablecimiento"));
                                listaEstablecimiento.add(objeMapa);
                            }
                            x.exitoImagen(listaEstablecimiento);
                        } catch (Exception e) {
                            e.printStackTrace();
                            //Toast.makeText(context, R.string.toas_exepcion_coordenadas, Toast.LENGTH_LONG).show();
                        }
                    }//falta el else si desea (valor q significa q no hay datos q obtener)
                }, error -> Toast.makeText(context, R.string.toas_problem_imagen, Toast.LENGTH_LONG).show()
        );
        queuee.add(post);
    }

    //String url="https://maps.googleapis.com/maps/api/directions/json?origin=Disneyland&destination=Universal+Studios+Hollywood&key=AIzaSyAkWoUAfvgpltlSccld6M8w18L8Kc6K-90";
    //String url="https://maps.googleapis.com/maps/api/directions/json?origin=34.1358593,-118.3511633&destination=33.8160897,-117.9225226&key=AIzaSyAkWoUAfvgpltlSccld6M8w18L8Kc6K-90";
    public void ruta(Context context,GoogleMap map,Double latitudOrig,Double longitudOrig,Double latitudDesti,Double longitudDesti){
        String url="https://maps.googleapis.com/maps/api/directions/json?origin="+latitudOrig.toString()+","+longitudOrig.toString()+"&destination="+latitudDesti.toString()+","+longitudDesti.toString()+"&key=AIzaSyAkWoUAfvgpltlSccld6M8w18L8Kc6K-90";
        //Log.i("jsonRuta: ","latitudO: "+latitudOrig+" longitudO: "+longitudOrig+" latitudD: "+latitudDesti+" longitudD: "+longitudDesti);
        if (queuee==null) queuee= Volley.newRequestQueue(context);
        //RequestQueue queue= Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject jso=new JSONObject(response);
                trazarRuta(jso,map);
                Log.i("jsonRuta: ",""+response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
        });
        queuee.add(stringRequest);
    }
    ArrayList<PolylineData> mPolylineData=new ArrayList<>();
    private void trazarRuta(JSONObject jso, GoogleMap map) {
        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;
        if (mPolylineData.size()>0) {
            for (PolylineData polylineData : mPolylineData ){
                polylineData.getPolyline().remove();
            }
            mPolylineData.clear();
            mPolylineData = new ArrayList<>();
        }
        try {
            jRoutes = jso.getJSONArray("routes");
            for (int i=0; i<jRoutes.length();i++){
                jLegs = ((JSONObject)(jRoutes.get(i))).getJSONArray("legs");
                for (int j=0; j<jLegs.length();j++){
                    jSteps = ((JSONObject)jLegs.get(j)).getJSONArray("steps");
                    for (int k = 0; k<jSteps.length();k++){
                        Polyline ply;
                        String polyline = ""+((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                        List<LatLng> list = PolyUtil.decode(polyline);
                        ply=map.addPolyline(new PolylineOptions().addAll(list).color(Color.GRAY).width(10));
                        mPolylineData.add(new PolylineData(ply));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void borrarPolilyne(){
        if (mPolylineData.size()>0) {
            for (PolylineData polylineData : mPolylineData ){
                polylineData.getPolyline().remove();
            }
            mPolylineData.clear();
            mPolylineData = new ArrayList<>();
        }
    }
    /////////////////////////


}
