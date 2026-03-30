package guiasalud.estableciemtos.guiamedica;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import guiasalud.estableciemtos.guiamedica.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    verificacionInternet aInternt=new verificacionInternet(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            setSupportActionBar(binding.appBarMain.toolbar);
            DrawerLayout drawer = binding.drawerLayout;
            NavigationView navigationView = binding.navView;
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_adminEstablecimiento/*,R.id.establecimientoFragment2*/)
                    .setOpenableLayout(drawer)
                    .build();
            //forma para corregir el problema de fragmentContainerView
            NavHostFragment navHostFragment=(NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main) ;
            assert navHostFragment != null;
            NavController navController= navHostFragment.getNavController();

            //este solo funciona con el <fragment>
            //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
            //se implementa para poder tener el control de los botones del menu lateral
            //y se podra tener acceso a su metodo onNavigationItemSelected()
           //NavigationView navigationVieww = findViewById(R.id.nav_view);
            //navigationVieww.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    //se tiene acceso a controlar las opciones del tollbar, el icono de amburgueza del menu lateral
        //y los 3 puntos q es el boton de la derecha
        if (item.getItemId() == R.id.action_settings) {
            this.logout();
        }
        return super.onOptionsItemSelected(item);
    }
    //metodo cerrar cesion
    private void logout() {
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        //para tener acceso a este metodo, se tiene que implementar arriba en el MainActivity
        //implements NavigationView.OnNavigationItemSelectedListener
        return false;
    }
}