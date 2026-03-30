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
import guiasalud.estableciemtos.guiamedica.modelsEntities.doctor;
import guiasalud.estableciemtos.guiamedica.modelsEntities.especialidadMedica;

public class ServicioDocSegunEstableci {
    //traer todo los doctores activos que pertenescan al establecimient
    public void traerTodosLosDoctoresDelEstablecimiento(final Context context,int codEstablecimiento, final IDoctor x){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://guiamedica.xyz/servicios/datosDocTodosPorEstablecimiento.php";
        Map<String,Integer> parametros=new HashMap<>();
        parametros.put("codEstablecimiento",codEstablecimiento);
        JsonObjectRequest post=new JsonObjectRequest(
                Request.Method.POST,url,new JSONObject(parametros),
                response -> {
                    doctor objeDoctor;
                    JSONArray arrax = response.optJSONArray("doctoresPorEstablecimient");
                    try {
                        ArrayList<doctor> listaDoctores = new ArrayList<>();
                        if (arrax != null){
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
                                //objeDoctor.setCodEspecialidd(jSOB.optString("nombreEspecialidad"));
                                listaDoctores.add(objeDoctor);
                            }
                        x.exitoTraerDoctores(listaDoctores);
                    }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                },error -> Toast.makeText(context,R.string.toas_problem_todoLosDoc, Toast.LENGTH_LONG).show()
        );
        queue.add(post);
    }
    //busca doctores segun el establecimiento y segun la especialidad seleccionada
    public void traerDoctorsSegunEspecialiddEstableci(final Context context,int codEstablecimiento,int codEspecialidad, final IDoctor x){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://guiamedica.xyz/servicios/datosDocPorEspecialiddYEstablecimient.php";
        Map<String,Integer> parametros=new HashMap<>();
        parametros.put("codEstablecimiento",codEstablecimiento);
        parametros.put("codEspecialidad",codEspecialidad);
        JsonObjectRequest post=new JsonObjectRequest(
                Request.Method.POST,url,new JSONObject(parametros),
                response -> {
                    doctor objeDoctor;
                    JSONArray arrax = response.optJSONArray("doctoresPorEspecialiddYEstablecimient");
                    try {
                        if (arrax != null){
                            ArrayList<doctor> listaDoctores = new ArrayList<>();
                        for (int i = 0; i < arrax.length(); i++) {
                            objeDoctor = new doctor();
                            JSONObject jSOB;
                            jSOB = arrax.getJSONObject(i);
                            objeDoctor.setCodDoctor(jSOB.optInt("codigoDoctor"));
                            objeDoctor.setNombre(jSOB.optString("nombreDoctor"));
                            objeDoctor.setApellido(jSOB.optString("apellidoDoctor"));
                            objeDoctor.setRne(jSOB.optString("rneDoctor"));
                            objeDoctor.setNumColegiatura(jSOB.optString("numColegiaturaDoctor"));
                            objeDoctor.setDescripTipoAtencioDoc(jSOB.optString("descripcionTipoAtencionDoctor"));
                            objeDoctor.setPrecio(jSOB.optDouble("precioAtencionDoctor"));
                            objeDoctor.setDirFacebook(jSOB.optString("facebookDoctor"));
                            objeDoctor.setDirInstagram(jSOB.optString("instagramDoctor"));
                            objeDoctor.setNuContacto(jSOB.optString("telefonoDoctor"));
                            listaDoctores.add(objeDoctor);
                        }
                        x.exitoTraerDoctores(listaDoctores);
                    }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                } ,error -> Toast.makeText(context, R.string.toas_problem_DocXespecialidd, Toast.LENGTH_LONG).show()
        );
        queue.add(post);
    }

    //traer especialidades que esten registradas en el establecimiento y q tengan 1 doctor minimo registrado
    public void traerEspecialidadesConMiniUnDocSegunEstableci(final Context context,int codEstablecimiento, final IEspecialidadesMedicas x){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://guiamedica.xyz/servicios/datosEspecialiddsConMinimoUnDocSegunEstableci.php";
        Map<String,Integer> parametros=new HashMap<>();
        parametros.put("codEstablecimiento",codEstablecimiento);
        JsonObjectRequest post=new JsonObjectRequest(
                Request.Method.POST,url,new JSONObject(parametros),
                response -> {
                    especialidadMedica objeEspecialidd;
                    JSONArray arrax = response.optJSONArray("especialiddsConMinimoUnDoc");
                    if (arrax != null) {
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
                }//else x.errortraesespecialidd();
                } ,error -> Toast.makeText(context,R.string.toas_problem_o_sinRegistro_especialidadesconDoc, Toast.LENGTH_SHORT).show()
        );
        queue.add(post);
    }
}