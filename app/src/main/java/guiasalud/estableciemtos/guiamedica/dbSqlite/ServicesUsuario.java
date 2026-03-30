package guiasalud.estableciemtos.guiamedica.dbSqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import guiasalud.estableciemtos.guiamedica.R;

public class ServicesUsuario extends DBHelper{
    final private Context context;
    DBHelper dbHelper;

    public ServicesUsuario(@Nullable @org.jetbrains.annotations.Nullable Context context) {
        super(context);
        this.context=context;
    }
    //solo inserta un registro
    public long insertarUsuario(String alias){
        long id=0;
        try{
            dbHelper=new DBHelper(context);
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            //funcion para insertar registro
            ContentValues contValues=new ContentValues();
            contValues.put("alias",alias);
            //la funcion insert() nos debuelve el 'id' al q stamos insertando
            id=db.insert(TABLE_USER,null,contValues);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return id;
    }
    //modifica el unico registro existente
    public boolean ActualizarUsuario(int id,String alias){
        boolean correcto;
        dbHelper=new DBHelper(context);
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            //consulta de actualizacion
            db.execSQL("UPDATE " + TABLE_USER + " SET alias = '" + alias + "' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            Toast.makeText(context, R.string.actualizar_no, Toast.LENGTH_SHORT).show();
            ex.toString();
            correcto = false;
        }
        return correcto;
    }
    //muestra siempre que aya un registro
    public Usuario mostrarUsuario(){
        dbHelper=new DBHelper(context);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Usuario user=null;
        Cursor cursorUsuario;
        //necesario para el selec
        cursorUsuario=db.rawQuery("SELECT * FROM " + TABLE_USER,null);
        if (cursorUsuario.getCount()==1){
            //pasamos el cursor al primer resultado de la consulta
            if(cursorUsuario.moveToFirst()){
                user=new Usuario();
                user.setId(cursorUsuario.getInt(0));
                user.setAlias(cursorUsuario.getString(1));
            }
        }
        cursorUsuario.close();
        return user;
    }
}
