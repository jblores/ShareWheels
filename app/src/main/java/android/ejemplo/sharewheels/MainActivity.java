package android.ejemplo.sharewheels;

import static android.ejemplo.sharewheels.util.Constants.ERROR_DIALOG_REQUEST;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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