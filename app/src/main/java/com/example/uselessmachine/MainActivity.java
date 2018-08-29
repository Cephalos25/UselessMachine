package com.example.uselessmachine;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    private Button buttonSelfDestruct;
    private Switch switchUseless;
    private ConstraintLayout bgelement;
    
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wireWidgets();
        setListeners();
    }

    private void setListeners() {
        buttonSelfDestruct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSelfDestructSequence();
            }
        });
        switchUseless.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    startSwitchOffTimer();
                }
            }
        });
    }

    private void startSelfDestructSequence() {
        buttonSelfDestruct.setEnabled(false);
        switchUseless.setEnabled(false);
        buttonSelfDestruct.setText(getString(R.string.main_destruct)+" "+10);
        Log.d(TAG, "startSelfDestructSequence: Self-destruct started");
        startSelfDestructTimer();
    }

    private void startSelfDestructTimer() {
        new CountDownTimer(10000,500){
            @Override
            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished%1000<500){
                    buttonSelfDestruct.setText(getString(R.string.main_destruct)+" "+Math.floor(millisUntilFinished/1000));
                    if(millisUntilFinished<3500){
                        bgelement.setBackgroundColor(Color.argb(255,255,0,0));
                    }
                    Log.d(TAG, "onTick: Timer updated");
                } else {
                    if(millisUntilFinished<3500){
                        bgelement.setBackgroundColor(Color.argb(255,255,255,255));
                    }
                }
            }
            @Override
            public void onFinish() {
                finish();
            }
        }.start();
    }

    private void startSwitchOffTimer() {
        new CountDownTimer(2000,100){
            @Override
            public void onTick(long millisUntilFinished) {
                if(!switchUseless.isChecked()){
                    Log.d(TAG, "onTick: Timer canceled");
                    cancel();
                }
            }
            @Override
            public void onFinish() {
                Log.d(TAG, "onFinish: Switch turned off");
                switchUseless.setChecked(false);
            }
        }.start();
    }

    private void wireWidgets() {
        buttonSelfDestruct=findViewById(R.id.button_main_selfdestruct);
        switchUseless=findViewById(R.id.switch_main_useless);
        bgelement=findViewById(R.id.layout);
    }

}
