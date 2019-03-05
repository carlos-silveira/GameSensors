package com.example.gamesensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    TextView data;
    private TextView txt;
    private TextView result;
    private Button toMorseBtn;
    private Button toAlphaBtn;
    private Button btnClean;
    private Button btnSpace;
    private char output;
    SensorManager mySensorManager;
    Sensor myProximitySensor;
    boolean dot=false,dash=false,near=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = findViewById(R.id.txt);
        result = findViewById(R.id.result);
        toMorseBtn = findViewById(R.id.toMorseBtn);
        toAlphaBtn = findViewById(R.id.toAlphaBtn);
        btnClean = findViewById(R.id.btnClean);
        btnSpace = findViewById(R.id.btnSpace);
        data = findViewById(R.id.data);
        mySensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        myProximitySensor = mySensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (myProximitySensor == null) {
            data.setText("No Proximity Sensor!");
        } else {
            mySensorManager.registerListener(proximitySensorEventListener, myProximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        toMorseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtToConvert = txt.getText().toString();
                String convertedTxt = MorseCode.alphaToMorse(txtToConvert);
                result.setText(convertedTxt);
            }
        });

        toAlphaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtToConvert = txt.getText().toString();
                String convertedTxt = MorseCode.morseToAlpha(txtToConvert);
                result.setText(convertedTxt);
            }
        });
        btnSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt.setText(txt.getText() + " ");
            }
        });

        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt.setText("");
            }
        });
//        data.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start,
//                                      int before, int count) {
//                if (data.getText().equals("Near")) {
//                    txt.setText("Cambio texto");
//                    long lastCall = 0;
//                    while (near) {
//                        if (System.currentTimeMillis() - lastCall > 5000) {
//                            lastCall = System.currentTimeMillis();
//                            dot = true;
//
//                        }
//                        if (System.currentTimeMillis() - lastCall > 1000) {
//                            lastCall = System.currentTimeMillis();
//                            dot = false;
//                            dash = true;
//
//                        }
//
//                    }
//                }
//
//            }
//
//
//        });
    }
    SensorEventListener proximitySensorEventListener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            // TODO Auto-generated method stub
            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                if (event.values[0] == 0) {
                    data.setText("Near");

                    near=true;


                    new java.util.Timer().schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {

                                        output='.';


                                }
                            },
                            100
                    );
                    new java.util.Timer().schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {

                                    output='-';

                                }
                            },
                            2000
                    );
//                    new java.util.Timer().schedule(
//                            new java.util.TimerTask() {
//                                @Override
//                                public void run() {
//
//                                    output=' ';
//
//                                }
//                            },
//                            3000
//                    );


                } else {
                    data.setText("Away");
                    txt.setText(txt.getText()+""+output);
//                    if(dash){
//                        txt.setText(txt.getText()+"-");
//                        dash=false;
//                    }
//                    if(dot){
//                        txt.setText(txt.getText()+".");
//                        dot=false;
//                    }
                }


            }
        }
    };
}