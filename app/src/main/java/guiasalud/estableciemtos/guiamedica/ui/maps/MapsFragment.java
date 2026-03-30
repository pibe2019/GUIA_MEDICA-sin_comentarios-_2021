package guiasalud.estableciemtos.guiamedica.ui.maps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import guiasalud.estableciemtos.guiamedica.LoadingDialog;
import guiasalud.estableciemtos.guiamedica.R;
import guiasalud.estableciemtos.guiamedica.dbSqlite.ServicesUsuario;
import guiasalud.estableciemtos.guiamedica.dbSqlite.Usuario;
import guiasalud.estableciemtos.guiamedica.fragment.establecimientoFragment;
import guiasalud.estableciemtos.guiamedica.modelsEntities.especialidadMedica;
import guiasalud.estableciemtos.guiamedica.modelsEntities.establecimientoMapa;
import guiasalud.estableciemtos.guiamedica.services.IEspecialidadesMedicas;
import guiasalud.estableciemtos.guiamedica.services.IRecepcionEstablecimientoCcordenads;
import guiasalud.estableciemtos.guiamedica.services.ServicioEspecialidadesMedicas;
import guiasalud.estableciemtos.guiamedica.services.ServicioEstablecimientoCcordenads;
import guiasalud.estableciemtos.guiamedica.verificacionInternet;

public class MapsFragment extends Fragment implements IRecepcionEstablecimientoCcordenads,
        IEspecialidadesMedicas, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnInfoWindowCloseListener, GoogleMap.OnMyLocationButtonClickListener/*,
        AdapterView.OnItemSelectedListener*/ {
    NavController navController;
    Fragment estabecimientoFragmenttt;
    private verificacionInternet aInternt;
    private Usuario usuario = new Usuario();
    private boolean pressBotonMiHubication = false;
    private WeakReference<Context> contex;
    ServicioEstablecimientoCcordenads sec = new ServicioEstablecimientoCcordenads();
    ServicioEspecialidadesMedicas seem = new ServicioEspecialidadesMedicas();
    //
    private Spinner especialiddmedica;
    private static ArrayList<especialidadMedica> listaEspecialdades = new ArrayList<>();
    ArrayList<String> listaStringEspecialidades;
    //
    static ArrayList<establecimientoMapa> li;
    private GoogleMap mMap;
    ArrayList<Marker> lista_marker;//almaceno los marcadores resultantes detodo los establecimientos traidos de la BD
    Marker markerGlobal = null;
    private double longitudeEstable = 0, latitudeEstable = 0;
    //////////////////////
    String mensaje1;
    double lat = 0.0;
    double lng = 0.0;
    private Marker marcador;
    protected View mView = null;
    LocationManager locationManager;
    LoadingDialog loadingDialog;
    private int contador=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.contex = new WeakReference<>(getContext());
        estabecimientoFragmenttt = new establecimientoFragment();
        aInternt = new verificacionInternet(this.getActivity());
    }

    private final OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(@NotNull GoogleMap googleMap) {
            mMap = googleMap;
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            LatLng puntoZonn = new LatLng(-8.105843, -79.035873);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(puntoZonn, 12));
            mMap.setMaxZoomPreference(19.3f);
            mMap.getUiSettings().setZoomControlsEnabled(false);//abilitamos u  deshabilitala opcion de boton en el mapa de zonn
            mMap.getUiSettings().setMapToolbarEnabled(false);//barra de herramientas del mapa
            //googleMap.setOnMyLocationButtonClickListener(MapsFragment.this::onMyLocationButtonClick);
            enableMyLocation2();
            selectSpinner(googleMap);
            /*
Toast.makeText(getContext(),"entro mapa ready",Toast.LENGTH_SHORT).show();
            LatLng inicio = new LatLng(-8.077431, -79.117848);
            mMap.addMarker(new MarkerOptions().position(inicio).title("Marker inicio"));
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            LatLng finn01 = new LatLng(-8.094278, -79.115816);
            mMap.addMarker(new MarkerOptions().position(finn01).title("Marker fin huanhcaco"));
            ///////
            LatLng fin = new LatLng(-9.060347, -78.589397);
            mMap.addMarker(new MarkerOptions().position(fin).title("Marker fin chimbote"));
            sec.ruta(getContext(),mMap,-8.077431,-79.117848,-9.060347,-78.589397);*/
        }
    };

    private void enableMyLocation2() {
        if (ContextCompat.checkSelfPermission(contex.get(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(contex.get(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);//q se vea el boton de mi ubicacion
            mMap.setOnMyLocationButtonClickListener(MapsFragment.this);//tocas el boton de mi ubicacion
        }
    }

    public void selectSpinner(GoogleMap googleMap) {
        especialiddmedica.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int posicioReal = position - 1;
                mMap.clear();/*eliminatodo los marcadores y limpia el mapa*/
                sec.borrarPolilyne();
                markerGlobal = null;
                latitudeEstable = 0.0;
                longitudeEstable = 0.0;
                if (aInternt.HayInternet()) {
                    if (position != 0) {
                        loadingDialog.startLoadingDialog();
                        int codEspecialidad;
                        codEspecialidad = listaEspecialdades.get(posicioReal).getCodigoEspecialidad();
                        new Thread(() -> {
                            //Aquí ejecutamos nuestras tareas costosas
                            sec.establecimientosPorEspecialidad(getContext(), codEspecialidad, MapsFragment.this, googleMap);
                        }).start();

                    } else {
                        if (contador==0) loadingDialog.startLoadingDialog();
                        else contador=0;
                        new Thread(() -> {
                            //Aquí ejecutamos nuestras tareas costosas
                            sec.coordenadas(contex.get(), MapsFragment.this, googleMap);
                        }).start();
                    }
                } else {
                    mostrarDialogPersonalizado(contex.get());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Toast.makeText(getContext(),"onNothuSelected:"+parent,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);
        this.mView = rootView;
        //miUbicacion(false);
        especialiddmedica = rootView.findViewById(R.id.spseleccionar);
        verificacionInternet aInternt = new verificacionInternet(getActivity());
        contador=1;
        if (aInternt.HayInternet()) {
            loadingDialog = new LoadingDialog(contex.get());
            loadingDialog.startLoadingDialog();
            //hilos
            //new Handler().postDelayed(() -> progressDialogGlobal.dismiss(),5000);
            seem.traerEspecialidadesMedicas(contex.get(), this);
        } else {
            mostrarDialogPersonalizado(contex.get());
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map1);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
        navController = Navigation.findNavController(view);
        ServicesUsuario servicesUsuario = new ServicesUsuario(contex.get());
        usuario = servicesUsuario.mostrarUsuario();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        miUbicacion(false);
        if (getActivity() != null)
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            if (ActivityCompat.checkSelfPermission(contex.get(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(contex.get(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                detenerServiceLocation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //si el fragment es removido o reemplazado
    }

    @Override
    public void exitoCorde(ArrayList<establecimientoMapa> x, GoogleMap googleMap) {
        if (x != null) {
            li = x;
            if (li.get(0).getCodigoEst() > 0) addMarkersToMap(googleMap);
        } else Toast.makeText(contex.get(), R.string.error_alcargar_establecimientos, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void exitoImagen(ArrayList<establecimientoMapa> x) {

    }

    @Override
    public void errorCorde(String x) {
        if (x.equals("0"))
            Toast.makeText(contex.get(), R.string.null_establecimientos, Toast.LENGTH_SHORT).show();

    }

    @SuppressLint("PotentialBehaviorOverride")
    private void addMarkersToMap(GoogleMap googleMap) {
        lista_marker = new ArrayList<>();
        //runOnUiThread -->es para activity
        //Thread es para fragment tambien pero no permite modificar la interfaz ni pasar parametros con el hilo principal
        //Handler()--> perfecto para fragmento y permite interactuar con variables del hilo principal mediante el" .pos() "
        /*getActivity().runOnUiThread(() -> {
        });
        new Thread(() -> {
        }).start();*/
        new Handler(Looper.getMainLooper()).post(() -> {
            for (int i = 0; i < li.size(); i++) {
                LatLng estabSald = new LatLng(li.get(i).getLatitudEst(), li.get(i).getLongitudEst());
                Marker actual;
                if (li.get(i).getTipoEst().equals("publico")) {
                    actual = googleMap.addMarker(new MarkerOptions().position(estabSald).title(li.get(i).getNombreEst()).snippet(li.get(i).getTipoEst()).icon(BitmapDescriptorFactory.fromResource(R.drawable.azulmarcador52)).rotation(6));
                } else {
                    actual = googleMap.addMarker(new MarkerOptions().position(estabSald).title(li.get(i).getNombreEst()).snippet(li.get(i).getTipoEst()).icon(BitmapDescriptorFactory.fromResource(R.drawable.turquesamarcador52)).alpha(1));
                }
                lista_marker.add(i, actual);
            }
            loadingDialog.dismissDialog();
        });

        //FALTA VALIDAR QUE MI UBICACION STE ACTIVADA.<-- ya esta
        googleMap.setOnMarkerClickListener(marker -> {
            markerGlobal = marker;
            int posicion = lista_marker.indexOf(markerGlobal);
            try {
                if (posicion !=- 1){
                    latitudeEstable = li.get(posicion).getLatitudEst();//del establecimiento
                    longitudeEstable = li.get(posicion).getLongitudEst();//del establecimiento

                    if (lat != 0 && lng != 0) {
                        sec.ruta(contex.get(), googleMap, lat, lng, latitudeEstable, longitudeEstable);
                    } else{
                        if (ActivityCompat.checkSelfPermission(contex.get(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                                ActivityCompat.checkSelfPermission(contex.get(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(contex.get(), R.string.aviso_de_ubicación, Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    latitudeEstable=-0.1;
                    longitudeEstable=-0.1;
                }

            } catch (Exception e) {
               e.printStackTrace();
            }
            return false;
        });
        //evento cuando tocas el marker
        //googleMap.setOnMarkerClickListener(this);
        //evento cuando tocas la ventana de informacion
        googleMap.setOnInfoWindowClickListener(this);
        //googleMap.setOnInfoWindowClickListener(this);//cierra la ventana de informacion
    }

    @Override
    public void onInfoWindowClick(@NonNull @NotNull Marker marker) {
        int posicion = lista_marker.indexOf(marker);
        markerGlobal = null;
        try {
            Bundle bundle = new Bundle();
            bundle.putString("nombre", li.get(posicion).getNombreEst());
            bundle.putString("tipo", li.get(posicion).getTipoEst());
            bundle.putString("direccion", li.get(posicion).getDireccionEst());
            bundle.putDouble("longitud", li.get(posicion).getLongitudEst());
            bundle.putDouble("latitud", li.get(posicion).getLatitudEst());
            bundle.putString("paginaWeb", li.get(posicion).getAginawebEst());
            bundle.putString("facebook", li.get(posicion).getFacebookEst());
            bundle.putString("numeroAtencion", li.get(posicion).getNumeroAtencionEst());
            bundle.putString("whatssap", li.get(posicion).getWhatssapEst());
            bundle.putString("correo", li.get(posicion).getCorreoInstitucionalEst());
            bundle.putString("numeroEmergencia", li.get(posicion).getNumeroEmergenciaEst());
            bundle.putString("licencia", li.get(posicion).getLicenciaFuncionamientoEst());
            bundle.putString("descripcion", li.get(posicion).getDescripcionServiciosEst());
            bundle.putInt("codigoEstablecimiento", li.get(posicion).getCodigoEst());
            //bundle.putParcelable("imagen",li.get(posicion).getImagen());
            //bundle.putString("tipoImagen",li.get(posicion).getTipo());
            Navigation.findNavController(mView).navigate(R.id.establecimientoFragment2, bundle);
        } catch (Exception ignored) {
        }
    }


    @Override
    public boolean onMarkerClick(@NonNull @NotNull Marker marker) {
        return false;
    }

    @Override
    public void onInfoWindowClose(@NonNull @NotNull Marker marker) {
    }

    @Override
    public boolean onMyLocationButtonClick() {
        // " Se hizo clic en el botón Mi ubicación "
        pressBotonMiHubication = true;
        miUbicacion(true);
        return false;
    }

    private void miUbicacion(boolean pb) {
        boolean GPS_disponiblep, NETWORK_disponible;
        Location net_loc=null, gps_loc=null, finalLoc=null;
        if (ActivityCompat.checkSelfPermission(contex.get(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(contex.get(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) contex.get().getSystemService(Context.LOCATION_SERVICE);
            GPS_disponiblep = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            NETWORK_disponible = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (GPS_disponiblep) {
                if (NETWORK_disponible) net_loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                gps_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                //Log.i("network activado"," :gpsloc-> "+gps_loc);
                //Log.i("network activado"," :net_loc-> "+net_loc);
                if (gps_loc != null && net_loc != null && gps_loc.getAccuracy()!=0.0 && net_loc.getAccuracy()!=0.0){
                    //Log.i("gps_loc.getAccuracy() = "," "+gps_loc.getAccuracy());
                    //Log.i("net_loc.getAccuracy() = "," "+net_loc.getAccuracy());
                    if (gps_loc.getAccuracy() > net_loc.getAccuracy()){
                        finalLoc=net_loc;
                        new Handler(Looper.getMainLooper()).post(() -> {
                            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 0, locationListener);//tiempo q demora en actualizar la posicion
                        });
                        Toast.makeText(contex.get(),"Recuperando ubicación",Toast.LENGTH_SHORT).show();
                        //Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        //ActualizarUbicacion(location,pb);
                        //ActualizarUbicacion(gps_loc,pb);
                        /*new Handler(Looper.getMainLooper()).post(() -> {
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, locationListener);//tiempo q demora en actualizar la posicion
                        });*/
                    }else {
                        finalLoc=gps_loc;
                        new Handler(Looper.getMainLooper()).post(() -> {
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 3, locationListener);//tiempo q demora en actualizar la posicion
                        });
                        Toast.makeText(contex.get(),"Recuperando ubicación.",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if (gps_loc != null) {
                        finalLoc=gps_loc;
                        new Handler(Looper.getMainLooper()).post(() -> {
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 3, locationListener);//tiempo q demora en actualizar la posicion
                        });
                        Toast.makeText(contex.get(),"Recuperando ubicación.",Toast.LENGTH_SHORT).show();

                    } else if (net_loc != null) {
                        finalLoc=net_loc;
                        new Handler(Looper.getMainLooper()).post(() -> {
                            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 0, locationListener);//tiempo q demora en actualizar la posicion
                        });
                        Toast.makeText(contex.get(),"ubicación",Toast.LENGTH_SHORT).show();
                    }else{
                        new Handler(Looper.getMainLooper()).post(() -> {
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, locationListener);//tiempo q demora en actualizar la posicion
                        });
                    }
                }

                if (finalLoc != null) {
                    ActualizarUbicacion(finalLoc,pb);
                    //Log.i("resultado final  finalLoc= "," "+finalLoc.getAccuracy());
                } else {
                    //Log.i(" finalLoc= null"," "+finalLoc);
                    Toast.makeText(contex.get(),R.string.aviso_uvicacion_null,Toast.LENGTH_SHORT).show();
                }
            } else {
                //Log.i("netword Y GPS apagado","entro a else");
                new Handler(Looper.getMainLooper()).post(() -> {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, locationListener);//tiempo q demora en actualizar la posicion
                });
            }



        }
    }

    private void  detenerServiceLocation(){
        locationManager.removeUpdates(locationListener);
    }

    private void ActualizarUbicacion(Location location,boolean pb) {
        try {
            if (location != null) {
                if (location.getLatitude() !=0.0 && location.getLongitude() !=0.0) {
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                    agregarMarcador(lat, lng, pb);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    // agregar marcador de mi ubicacion en el mapa
    private void agregarMarcador(double lat, double lng, boolean pressButon) {
        //usuario;
        LatLng coordenadas = new LatLng(lat, lng);
        if (marcador != null) marcador.remove();
        if (usuario!=null){
            String alias=usuario.getAlias();
            if (mMap!=null)
            marcador = mMap.addMarker(new MarkerOptions()
                    .position(coordenadas)
                    .title(" "+alias+" ")//cuando tocas el icino aparecera
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.caminando30)));//icono x defecto
        }else {
            if (mMap!=null)
            marcador = mMap.addMarker(new MarkerOptions()
                    .position(coordenadas)
                    .title(contex.get().getString(R.string.dato_yo))//cuando tocas el icino aparecera
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.caminando30)));//icono x defecto
        }
        if (pressButon){
            pressBotonMiHubication=false;
            CameraUpdate MiUbicacion = CameraUpdateFactory.newLatLng(coordenadas);//MAYOR CANTIDAD MAS CERCA ZONN DEL MAPA
            mMap.animateCamera(MiUbicacion);
        }
    }
    //control del mapa
    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //entra cuando la ubicacion a cambiado
            //int posicion;
            ActualizarUbicacion(location,false);
            if(markerGlobal!=null){
                try {
                    if (latitudeEstable != -0.1) pintar(latitudeEstable,longitudeEstable, location);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        private void pintar(double latitudd, double longitudd, Location location){

            try {
                if (location.getLatitude()!=0 && location.getLongitude()!=0){
                    pressBotonMiHubication=false;
                    //if (tocoMarket){
                    //new Thread(() -> sec.ruta(contex.get(),mMap,location.getLatitude(),location.getLongitude(),latitud,longitud)).start();
                    sec.ruta(contex.get(),mMap,location.getLatitude(),location.getLongitude(),latitudd,longitudd);
                    //}
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void onProviderEnabled(String provider) {
            pressBotonMiHubication=false;
            mensaje1 = contex.get().getString(R.string.aviso_toas_activado);
            Mensaje();
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            //quedo obsoleto en el nivel de api 29
        }

        @Override
        public void onProviderDisabled(String provider) {
            mensaje1 = contex.get().getString(R.string.aviso_toas_desactivado);
            //se ejecuta cuando el gps sta desactivado
            if (pressBotonMiHubication) locationStart();
            Mensaje();
            lat=0.0;
            lng=0.0;
        }

    };
    public void Mensaje(){
        Toast toast = Toast.makeText(contex.get(),mensaje1,Toast.LENGTH_SHORT);
        toast.show();
    }

    private void locationStart() {//activar los servicion cuando stan apagados
        try {
            if (contex.get() !=null){
                LocationManager mlocManag = (LocationManager) contex.get().getSystemService(Context.LOCATION_SERVICE);
                final boolean gpsEnable = mlocManag.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (!gpsEnable) {
                    Intent settingInt = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(settingInt);// ACTION_APPLICATION_DETAILS_SETTINGS
                    pressBotonMiHubication=false;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void exitoTraerEspecialidades(ArrayList<especialidadMedica> x) {
        if (x==null) seem.traerEspecialidadesMedicas(contex.get(),this);
        else{
            listaEspecialdades=x;
            listaStringEspecialidades=new ArrayList<>();
            listaStringEspecialidades.add(getString(R.string.spiner_cabecera));
            for (int i=0;i < x.size();i++){
                listaStringEspecialidades.add(listaEspecialdades.get(i).getNombreEspecialidad()/*+"-"+listaEspecialdades.get(i).getCodigoEspecialidad()*/);
            }
            llenarSpiner(listaStringEspecialidades);
        }
    }

    public void llenarSpiner(ArrayList<String> lista)
    {
         ArrayAdapter<String> adapter=new ArrayAdapter<>(contex.get(), android.R.layout.simple_spinner_item,lista);
        especialiddmedica.setAdapter(adapter);
    }

    @Override
    public void errorTraerEspecialidades(String y) {}


    private void mostrarDialogPersonalizado(Context c){
        AlertDialog.Builder builder=new AlertDialog.Builder(c);
        LayoutInflater inflater=getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog,null);
        builder.setView(view);
        AlertDialog dialog=builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        TextView txt=view.findViewById(R.id.text_dialog);
        txt.setText(R.string.fallo_de_conexion);
        Button btn=view.findViewById(R.id.btn_dialogo_ok);
        btn.setOnClickListener(v -> {
            dialog.dismiss();
        });
    }

}