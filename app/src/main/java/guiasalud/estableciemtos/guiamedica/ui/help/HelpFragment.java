package guiasalud.estableciemtos.guiamedica.ui.help;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.google.android.material.textfield.TextInputLayout;
import org.jetbrains.annotations.NotNull;
import guiasalud.estableciemtos.guiamedica.R;
import guiasalud.estableciemtos.guiamedica.dbSqlite.DBHelper;
import guiasalud.estableciemtos.guiamedica.dbSqlite.ServicesUsuario;
import guiasalud.estableciemtos.guiamedica.dbSqlite.Usuario;

public class HelpFragment extends Fragment implements View.OnClickListener {
    private EditText editAlias;
    private TextInputLayout textLayaAlias;
    private Button guardar,modificar,actualizar;
    private Usuario usuario;
    //private Context contex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        crearDB();
        View rootView = inflater.inflate(R.layout.fragment_help, container, false);
        Animation animation= AnimationUtils.loadAnimation(getContext(), R.anim.animationcovid2);
        editAlias=rootView.findViewById(R.id.tietAlias);
        textLayaAlias=rootView.findViewById(R.id.outlinedTextFieldAlias);
        guardar=rootView.findViewById(R.id.btnGuardarAlias);
        modificar=rootView.findViewById(R.id.btnModificarAlias);
        actualizar=rootView.findViewById(R.id.btnActualizarAlias);
        guardar.setOnClickListener(this);
        modificar.setOnClickListener(this);
        actualizar.setOnClickListener(this);
        hayRegistro();
        textLayaAlias.setAnimation(animation);
        return rootView;
    }
    private void crearDB(){
        DBHelper DBHelper =new DBHelper(getContext());
        SQLiteDatabase db = DBHelper.getWritableDatabase();
    }
    private void hayRegistro(){//en el onCreateView
        ServicesUsuario servicesUsuario =new ServicesUsuario(getContext());
        usuario=servicesUsuario.mostrarUsuario();
        //condicion para verificar si existen registros y no si existe la BD
       if (usuario!=null){
            //si hay registro
            editAlias.setEnabled(false);
           editAlias.setFocusableInTouchMode(false);
            textLayaAlias.setEndIconMode(TextInputLayout.END_ICON_NONE);
            editAlias.setText(usuario.getAlias());
            guardar.setVisibility(View.GONE);
            modificar.setVisibility(View.VISIBLE);
        }else {
            //no hay registro
            guardar.setVisibility(View.VISIBLE);
            modificar.setVisibility(View.GONE);
        }
        actualizar.setVisibility(View.GONE);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btnGuardarAlias){
            ServicesUsuario servicesUsuario =new ServicesUsuario(getContext());
            if (editAlias.getText().toString().trim().length()>0){
                //esto debuelve un 'id' de tipo 'long'
                long id = servicesUsuario.insertarUsuario(editAlias.getText().toString().trim());
                if (id>0){
                    Toast.makeText(getContext(),R.string.sobrenombre_guardado,Toast.LENGTH_SHORT).show();
                    hayRegistro();
                }else Toast.makeText(getContext(),R.string.aviso_guardar_nameMarker,Toast.LENGTH_SHORT).show();
            } else Toast.makeText(getContext(),R.string.aviso_ingrese_nameMarker,Toast.LENGTH_SHORT).show();
        }else if (v.getId()==R.id.btnModificarAlias){
            editAlias.setEnabled(true);
            editAlias.setFocusableInTouchMode(true);
            textLayaAlias.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);
            modificar.setVisibility(View.GONE);
            actualizar.setVisibility(View.VISIBLE);
        }else if (v.getId()==R.id.btnActualizarAlias){
            //actualizo el mismo registro de la id alias mostrada
            ServicesUsuario servicesUsuario =new ServicesUsuario(getContext());
            if (editAlias.getText().toString().trim().length()>0){
                boolean respuesta=servicesUsuario.ActualizarUsuario(usuario.getId(),editAlias.getText().toString().trim());
                if (respuesta){
                    Toast.makeText(getContext(),R.string.aviso_actualizar_nameMarker,Toast.LENGTH_LONG).show();
                    hayRegistro();
                }
                //else toas.no actualizo
            }else Toast.makeText(getContext(),R.string.aviso_ingrese_nameMarker,Toast.LENGTH_SHORT).show();

        }
    }
}
