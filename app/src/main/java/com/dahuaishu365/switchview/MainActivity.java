package com.dahuaishu365.switchview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SwitchView mSwitchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwitchView = (SwitchView) findViewById(R.id.switch_view);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                mSwitchView.turnOn();
                break;
            case R.id.button2:
                mSwitchView.turnOffWithAnimation();
                break;
            case R.id.button3:
                mSwitchView.turnOnWithAnimation();
                break;
            case R.id.button4:
                mSwitchView.turnOff();
                break;
        }

    }
}
