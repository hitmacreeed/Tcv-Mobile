package com.rtc.user.tcvmobile;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import co.mobiwise.library.radio.RadioListener;
import co.mobiwise.library.radio.RadioManager;

public class Rcv extends Activity implements RadioListener{

    private final String[] RADIO_URL = {"http://radios.vpn.sapo.pt/CV/radio7.mp3"};

    Button mButtonControlStart;
    TextView mTextViewControl;
    RadioManager mRadioManager = RadioManager.with(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_radio);

        mRadioManager.registerListener(this);
        mRadioManager.setLogging(true);

        initializeUI();
    }

    public void initializeUI() {
        mButtonControlStart = (Button) findViewById(R.id.buttonControlStart);
        mTextViewControl = (TextView) findViewById(R.id.textviewControl);

        mButtonControlStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mRadioManager.isPlaying())
                    mRadioManager.startRadio(RADIO_URL[0]);
                else
                    mRadioManager.stopRadio();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mRadioManager.connect();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRadioManager.disconnect();
    }

    @Override
    public void onRadioLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //TODO Do UI works here.
                mTextViewControl.setText("RADIO : A CARREGAR...");

            }
        });
    }

    @Override
    public void onRadioConnected() {

    }

    @Override
    public void onRadioStarted() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //TODO Do UI works here.
                mTextViewControl.setText("RADIO: A TOCAR...");
            }
        });
    }

    @Override
    public void onRadioStopped() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //TODO Do UI works here
                mTextViewControl.setText("RADIO: EM PAUSA.");
            }
        });
    }

    @Override
    public void onMetaDataReceived(String s, String s1) {
        //TODO Check metadata values. Singer name, song name or whatever you have.
    }

    @Override
    public void onError() {

    }
}
