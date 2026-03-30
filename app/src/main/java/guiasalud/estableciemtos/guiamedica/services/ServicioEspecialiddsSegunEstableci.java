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
import java.util.HashMap;
import java.util.Map;

import guiasalud.estableciemtos.guiamedica.R;
import guiasalud.estableciemtos.guiamedica.modelsEntities.especialidadMedica;

public class ServicioEspecialiddsSegunEstableci {
    //busca las especialidades con las que cuenta el establecimiento, sin importar que no aya doctores registrados
    public void especialiddsSegunEstableci(final Context context, int codEstablecimiento, final IEspecialidadesMedicas x) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://guiamedica.xyz/servicios/datosEspecialidadesSegunEstablecimiento.php";
        Map<String,Integer> parametros=new HashMap<>();
        parametros.put("codEstablecimiento",codEstablecimiento);
        JsonObjectRequest post=new JsonObjectRequest(
                Request.Method.POST, url, new JSONObject(parametros), response -> {
                    especialidadMedica objetEspecialidad;
                    JSONArray arrax = response.optJSONArray("especialidadPorEstablecimiento");
                    try {
                        if (arrax!=null){
                            ArrayList<especialidadMedica> listaEspecialidades = new ArrayList<>();
                            for (int i = 0; i < arrax.length(); i++) {
                                objetEspecialidad = new especialidadMedica();
                                JSONObject jSOB;
                                jSOB = arrax.getJSONObject(i);
                                objetEspecialidad.setCodigoEspecialidad(jSOB.optInt("codigoEspecialidad"));
                                objetEspecialidad.setNombreEspecialidad(jSOB.optString("nombreEspecialidad"));
                                objetEspecialidad.setTipoServicioESE(jSOB.getString("tipoServicioESEspecialidad"));
                                listaEspecialidades.add(objetEspecialidad);
                            }
                            x.exitoTraerEspecialidades(listaEspecialidades);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(context, R.string.toas_problem_o_sinRegistro_especialidades, Toast.LENGTH_LONG).show()
        );
        queue.add(post);
    }
}
