package guiasalud.estableciemtos.guiamedica.services;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

import guiasalud.estableciemtos.guiamedica.R;
import guiasalud.estableciemtos.guiamedica.modelsEntities.doctor;

public class ServicioBuscarDoc {
    //todo los doctores activos de la base de datos
    public void todoLosDoctores(final Context context, final IDoctor x){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://guiamedica.xyz/servicios/datosDocTodos.php";
        JsonObjectRequest post=new JsonObjectRequest(
                Request.Method.POST,url,null,
                response -> {
                    doctor objeDoctor;
                    JSONArray arrax = response.optJSONArray("allDoctores");
                    if (arrax != null) {
                    try {
                        ArrayList<doctor> listaDoctores = new ArrayList<>();
                        for (int i = 0; i < arrax.length(); i++) {
                            objeDoctor = new doctor();
                            JSONObject jSOB;
                            jSOB = arrax.getJSONObject(i);
                            objeDoctor.setCodDoctor(jSOB.optInt("codigoDoctor"));
                            objeDoctor.setEspecialidad(jSOB.optString("nombreEspecialidad"));
                            objeDoctor.setNombre(jSOB.optString("nombreDoctor"));
                            objeDoctor.setApellido(jSOB.optString("apellidoDoctor"));
                            objeDoctor.setRne(jSOB.optString("rneDoctor"));//la edad se cambio x rne
                            objeDoctor.setNumColegiatura(jSOB.optString("numColegiaturaDoctor"));
                            objeDoctor.setDescripTipoAtencioDoc(jSOB.optString("descripcionTipoAtencionDoctor"));
                            objeDoctor.setPrecio(jSOB.optDouble("precioAtencionDoctor"));
                            objeDoctor.setDirFacebook(jSOB.optString("facebookDoctor"));//horario se cambio x FB
                            objeDoctor.setDirInstagram(jSOB.optString("instagramDoctor"));
                            objeDoctor.setNuContacto(jSOB.optString("telefonoDoctor"));
                            objeDoctor.setNombreEstableci(jSOB.optString("nombreEstablecimiento"));
                            objeDoctor.setLatitudEstableci(jSOB.optString("latitudEstablecimiento"));
                            objeDoctor.setLongitudEstableci(jSOB.optString("longitudEstablecimiento"));
                            objeDoctor.setCodEstablecimientDoc(jSOB.optInt("codigoEstablecimiento"));
                            listaDoctores.add(objeDoctor);
                        }
                        x.exitoTraerDoctores(listaDoctores);//de acac me voy defrente alexito
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }//else x.errortRAERdoctores();
                },error -> Toast.makeText(context, R.string.error_toas_service_mostrarDoc_all, Toast.LENGTH_LONG).show()//de acacse va a lisdocAadapter
        );
        queue.add(post);
    }

}
