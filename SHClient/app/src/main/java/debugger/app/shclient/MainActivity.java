package debugger.app.shclient;

import android.content.Intent;
import android.os.Build;
//import android.support.annotation.RequiresApi;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import org.eclipse.paho.client.mqttv3.MqttException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Sender sender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main_activity);

        ImageButton bSettings = (ImageButton) findViewById(R.id.bSettings);
        Button bLamp1 = (Button) findViewById(R.id.bPopular);

        try {
            connectMqtt();
        }
        catch (MqttException e) {
            e.printStackTrace();
        }




        bSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                Intent intent = new Intent(MainActivity.this, SceneActivity.class);
                startActivity(intent);
            }
        });

        bLamp1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                        Thread sendMessageThread = new Thread(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void run() {
                                try {
                                    sender.SendMessage(sender.getClient(), "lamp1");
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        sendMessageThread.run();
                        System.out.println("lamp1");
                }
                return false;
            }
        });


    }

    @Override
    public void onClick(View view) {

    }

    private void connectMqtt() throws MqttException {
        sender = new Sender(getApplicationContext());
        sender.setConnect("tcp://192.168.1.114:1883");
//        Thread sendMessageThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    sender.setConnect("tcp://192.168.1.114:1883");
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        sendMessageThread.run();
    }
}