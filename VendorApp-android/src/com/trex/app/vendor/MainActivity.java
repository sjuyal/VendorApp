package com.trex.app.vendor;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.Iterator;
import java.util.List;


public class MainActivity extends AndroidApplication {

    private String DATA_URL="http://1-dot-avian-outrider-546.appspot.com/resource/getSeatCords/";
    @Override
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("Soccer","LO");
        Intent intent = getIntent();
        String message = intent.getStringExtra("message");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

//        List<SensorData> data = generate.test();
//
//        int Max = 1145, Min = 0;
//        int rand =Min + (int)(Math.random() * ((Max - Min) + 1));
//        String []pair = data.get(rand).values.get(0).split("_");

//        String x = pair[0];
//        String y = pair[1];
//
//        Log.d("Soccer",DATA_URL + message+"/"+x+"/"+y);
        String data[] = message.split(":");
        Log.d("vendor",DATA_URL + data[0] + "/35.12512346/14.9874652");
        String output = new RequestTask().doInBackground(DATA_URL + data[0] + "/35.12512346/14.9874652");
        JsonValue jsonValue = new JsonReader().parse(output);
        Iterator<JsonValue> iter = jsonValue.iterator();
        int i=0;
        int x = 0;
        int y = 0;
        while(iter.hasNext()){
            if(i==0){
                JsonValue jV = iter.next();
            }else{
                JsonValue jV = iter.next();
                x = jV.getInt("xCoordinate");
                y = jV.getInt("yCoordinate");
            }
            i++;
        }
//        Log.d("Soccer","$$"+output+"$$");
//        Toast.makeText(getApplicationContext(),output,Toast.LENGTH_LONG).show();

        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        StringBuilder sb = new StringBuilder();
        sb.append(x+":"+y+",");
        sb.append(data[1]);
        Log.d("vendor",sb.toString());
        cfg.useAccelerometer = false;
        cfg.useCompass = false;

        initialize(new VendorApp(sb.toString()), cfg);
    }
}