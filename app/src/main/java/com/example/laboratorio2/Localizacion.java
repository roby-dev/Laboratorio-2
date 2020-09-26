package com.example.laboratorio2;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class Localizacion implements LocationListener {
    FragmentMap mainActivity;
    TextView txtDireccion;

    public FragmentMap getMainActivity(){
        return mainActivity;
    }

    public void setMainActivity(FragmentMap mainActivity,TextView txtDireccion){
        this.mainActivity=mainActivity;

        this.txtDireccion=txtDireccion;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        String texto = "Mi ubicación es: \n"
                +"Latitud = " +location.getLatitude() +"\n"
                +"Longitud = "+location.getLongitude();


        mapa(location.getLatitude(),location.getLongitude());

        MainActivity.latitudGPS=String.valueOf(location.getLatitude());
        MainActivity.lontitudGPS=String.valueOf(location.getLongitude());

        Geocoder geocoder = new Geocoder(getMainActivity(), Locale.getDefault());
        try {
            List<Address> direccion = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            MainActivity.direccion=direccion.get(0).getAddressLine(0);
            txtDireccion.setText(MainActivity.direccion);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mapa(double lat,double lon){
        FragmentMaps fragment = new FragmentMaps();

        Bundle bundle = new Bundle();
        bundle.putDouble("lat",new Double(lat));
        bundle.putDouble("lon",new Double(lon));
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getMainActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment,fragment,null);
        fragmentTransaction.commit();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status){
            case LocationProvider
                    .AVAILABLE:
                Log.d("debug","LocationProvider.AVAILABLE");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                Log.d("debug","LocationProvider.OUT_OF_SERVICE");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.d("debug","LocationProvider.TEMPORARILY_UNAVAILABLE");
                break;
        }
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        txtDireccion.setText("GPS Activado");
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        txtDireccion.setText("GPS Desactivado");
    }
}
