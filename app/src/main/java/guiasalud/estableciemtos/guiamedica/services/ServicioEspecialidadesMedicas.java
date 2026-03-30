package guiasalud.estableciemtos.guiamedica.services;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import guiasalud.estableciemtos.guiamedica.R;
import guiasalud.estableciemtos.guiamedica.modelsEntities.especialidadMedica;

public class ServicioEspecialidadesMedicas {
    //especialidades medicas que estan registradas en algun establecimiento
    public void traerEspecialidadesMedicas(final Context context,final IEspecialidadesMedicas x){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://guiamedica.xyz/servicios/datosEspecialidades.php";
        JsonObjectRequest post=new JsonObjectRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                especialidadMedica objeEspecialidd;
                JSONArray arrax = response.optJSONArray("especialidades");
                if (arrax != null){
                    try {
                        ArrayList<especialidadMedica> listaEspecialidades = new ArrayList<>();
                        for (int i = 0; i < arrax.length(); i++) {
                            objeEspecialidd = new especialidadMedica();
                            JSONObject jSOB;
                            jSOB = arrax.getJSONObject(i);
                            objeEspecialidd.setCodigoEspecialidad(jSOB.optInt("codigoEspecialidad"));
                            objeEspecialidd.setNombreEspecialidad(jSOB.optString("nombreEspecialidad"));
                            listaEspecialidades.add(objeEspecialidd);
                        }
                        x.exitoTraerEspecialidades(listaEspecialidades);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, R.string.toas_problem_especialidades,Toast.LENGTH_SHORT).show();
            }
        }
        );
        queue.add(post);

    }
}
