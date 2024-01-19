package android.ejemplo.sharewheels;

import static android.ejemplo.sharewheels.util.Constants.ERROR_DIALOG_REQUEST;
import static android.ejemplo.sharewheels.util.Constants.PERMISSIONS_REQUEST_ENABLE_GPS;


import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApi;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button login,register;
    private boolean mLocationPermisionGranted = false;

    private void buildAlertMessageNoGps(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Esta aplicaci'on requiere de GPS para funcionar.")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {

                    public void onClick(@SuppressWarnings("unused")final DialogInterface dialog,@SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        //revisar esto que no se si esta bien porque en el video utilizan un metodo diferente y el sustituto no obsoleto de ese metodo es una locura
                        startActivity(enableGpsIntent);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    public boolean isMapsEnabled(){
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }
    public boolean isServicesOK(){
        Log.d(TAG,"isServicesOK : checking google services version");
        int disponible = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
        if (disponible == ConnectionResult.SUCCESS){
            Log.d(TAG,"isServicesOK: Google Play Services funciona");
            return true;
        }else if (GoogleApiAvailability.getInstance().isUserResolvableError(disponible)){
            Log.d(TAG,"isServicesOK: ha ocurrido un error pero se puede arreglar");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this,disponible,ERROR_DIALOG_REQUEST);
            dialog.show();
        }else {
            Toast.makeText(this,"No puedes utilizar el mapa",Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.buttonLoginPage);
        register = findViewById(R.id.buttonRegisterPage);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}