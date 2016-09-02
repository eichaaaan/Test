package com.example.k14022kk.test;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private LocationManager mLocationManager;

    private TextView mTextView1;
    private TextView mTextView2;
    private TextView result;

    private Button mButton;
    private Button mButton2;

    private ArrayList<GPSData> data_list;
    private ArrayList<String> latitude=null;
    private ArrayList<String> longitude=null;
    private int i=0;
    private final String FILE_NAME="MapData.dat";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mTextView1=(TextView)findViewById(R.id.textView2);
        mTextView2=(TextView)findViewById(R.id.textView3);

        mButton=(Button)findViewById(R.id.button);
        mButton2=(Button)findViewById(R.id.button2);

        latitude=new ArrayList<>();
        longitude=new ArrayList<>();

        data_list=new ArrayList<GPSData>();

        result=(TextView)findViewById(R.id.textView4);
    }

    @Override
    protected void onResume() {
        System.out.println("0");
        if (mLocationManager != null) {
            System.out.println("0.1");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
                System.out.println("0.2");
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                System.out.println("1");
                mLocationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        //LocationManager.NETWORK_PROVIDER,
                        100,
                        3,
                        this);
            }
            System.out.println("2");
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    //LocationManager.NETWORK_PROVIDER,
                    0,
                    0,
                    this);
            mLocationManager.removeUpdates(this);
        }
        super.onResume();

    }

    @Override
    protected void onPause() {
        if (mLocationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                mLocationManager.removeUpdates(this);
            }

        }
        super.onPause();
    }

    @Override
    public void onLocationChanged(Location location){
        System.out.println("2");
        Log.v("----------", "----------");
        Log.v("Latitude", String.valueOf(location.getLatitude()));
        Log.v("Longitude", String.valueOf(location.getLongitude()));
        Log.v("Accuracy", String.valueOf(location.getAccuracy()));
        Log.v("Altitude", String.valueOf(location.getAltitude()));
        Log.v("Time", String.valueOf(location.getTime()));
        Log.v("Speed", String.valueOf(location.getSpeed()));
        Log.v("Bearing", String.valueOf(location.getBearing()));

        mTextView1.setText("Latitude:"+location.getLatitude());
        mTextView2.setText("Latitude:"+location.getLongitude());


        GPSData data=new GPSData(location.getLatitude(),location.getLongitude());
        data_list.add(data);

    }

    @Override
    public void onProviderDisabled(String provider){

    }

    @Override
    public void onProviderEnabled(String provider){

    }


    @Override
    public void onStatusChanged(String provider,int status,Bundle extras){
        switch(status){
            case LocationProvider.AVAILABLE:
                Log.v("Status", "AVAILABLE");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                Log.v("Status", "OUT_OF_SERVICE");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.v("Status", "TEMPORARILY_UNAVAILABLE");
                break;
        }
    }
    //Startボタンを押したときの処理（GPS計測開始）
    public void onStart(View view){
        //確認ダイアログの実装
        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("GPS");
        alertDialog.setMessage("GPS計測を始めますか?");

        if (mLocationManager != null) {
            System.out.println("0.1");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            }
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    //LocationManager.NETWORK_PROVIDER,
                    0,
                    0,
                    this);
        }
    }
    //Stopボタンを押したときの処理（GPS計測終了）
    public void onStop(View view){
        if (mLocationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

            }
            mLocationManager.removeUpdates(this);
        }
        i=0;

        //ファイルに書き込む処理
        if(data_list.size()!=0){
            try {

                FileOutputStream fos=openFileOutput(FILE_NAME,MODE_PRIVATE);
                System.out.println("保存");
                ObjectOutputStream oos=new ObjectOutputStream(fos);
                oos.writeObject(data_list);
                oos.close();
                fos.close();
                System.out.println(data_list.get(i));
                i++;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void onRead(View view){
        try{

            FileInputStream fis=openFileInput(FILE_NAME);
            ObjectInputStream ois=new ObjectInputStream(fis);
            ArrayList<GPSData> save=(ArrayList<GPSData>)ois.readObject();
            for(int l=0;l<save.size();l++){

                System.out.println("latitude:"+save.get(l).getLatitude());
                System.out.println("longitude:"+save.get(l).getLongitude());
                result.append(l+".latitude:"+save.get(l).getLatitude()+" longitude:"+save.get(l).getLongitude()+"\n");
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
