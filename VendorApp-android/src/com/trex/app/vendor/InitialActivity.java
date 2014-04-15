package com.trex.app.vendor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjuyal on 11/4/14.
 */
public class InitialActivity extends Activity {

    Spinner sp1,sp2,sp3;
    ArrayAdapter<String> adp1,adp2,adp3;
    List<String> l1,l2,l3;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial);

        l1=new ArrayList<String>();
        l3=new ArrayList<String>();
        char ch='A';
        for (int i=0;i<26;i++){
            l1.add(ch+"");
            ch++;
        }
        l1.add("AA");
        l1.add("AB");

        List<String> random = new ArrayList<String>();
        ch='H';
        for (int i=0;i<14;i++){
            random.add(ch + "");
            ch++;
        }

        sp1= (Spinner) findViewById(R.id.spinner1);
        sp2= (Spinner) findViewById(R.id.spinner2);
        sp3= (Spinner) findViewById(R.id.spinner3);


        l3.add("Dominoes");
        l3.add("PizzaHut");
        l3.add("Water Counter1");
        l3.add("WashRooms");
        l3.add("Water Counter2");
        l3.add("Subway");
        l3.add("KFC");
        l3.add("McD");

        adp3=new ArrayAdapter<String> (this,android.R.layout.simple_dropdown_item_1line,l3);
        adp3.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sp3.setAdapter(adp3);


        adp1=new ArrayAdapter<String> (this,android.R.layout.simple_dropdown_item_1line,l1);
        adp1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sp1.setAdapter(adp1);

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                pos=arg2;
                add();

            }

            private void add() {
                // TODO Auto-generated method stub
                //Toast.makeText(getBaseContext(), ""+pos, Toast.LENGTH_SHORT).show();
                l2= new ArrayList<String>();
                if(pos>=7 && pos <=21){
                    for(int i=1;i<=18;i++){
                        l2.add(i+"");
                    }
                }else{
                    for(int i=1;i<=61;i++){
                        l2.add(i+"");
                    }
                }
                adp2=new ArrayAdapter<String>(InitialActivity.this,
                        android.R.layout.simple_dropdown_item_1line,l2);
                adp2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                sp2.setAdapter(adp2);

                select();
            }

            private void select() {
                // TODO Auto-generated method stub

                sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {
                        // TODO Auto-generated method stub
                        //Toast.makeText(getBaseContext(), "Test "+arg2, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

       // final EditText et = (EditText)findViewById(R.id.eTSeatNo) ;
        final Spinner spin1 = (Spinner) findViewById(R.id.spinner1);
        final Spinner spin2 = (Spinner) findViewById(R.id.spinner2);
        final Spinner spin3 = (Spinner) findViewById(R.id.spinner3);
        Button bt = (Button)findViewById(R.id.button);

        /*Spinner dropdown = (Spinner)findViewById(R.id.spinner1);*/


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitialActivity.this, MainActivity.class);
                String message = String.valueOf(spin1.getSelectedItem())+"_"+String.valueOf(spin2.getSelectedItem())+":"+String.valueOf(spin3.getSelectedItem());
                Log.d("Soccer" , "Sea number before initnt : " + message);
                intent.putExtra("message", message);
                startActivity(intent);
            }
        });
    }
}
