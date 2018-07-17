package com.example.schic.tp14;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

public class PreferenceActivity extends AppCompatActivity {
    SharedPreferences pref;
    RadioGroup rgLangue;
    Switch swAdmin;
    RadioButton rbf, rbe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        rbf = findViewById(R.id.radioButton);
        rbe = findViewById(R.id.radioButton2);
        rgLangue = findViewById(R.id.radioGroup);
        swAdmin = findViewById(R.id.switch1);

        pref = getSharedPreferences("tp14", MODE_PRIVATE);
        if(pref.getBoolean("admin", false) == false){
            swAdmin.setChecked(false);
        }
        else{
            swAdmin.setChecked(true);
        }

        swAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pref.edit().putBoolean("admin", isChecked).commit();
            }
        });

        if(pref.getInt("langue",0) == 0)
        {
            rbf.setChecked(true);
        }
        else {
            rbe.setChecked(true);
        }

        rgLangue.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioButton:
                        pref.edit().putInt("langue", 0).commit();
                        break;
                    case R.id.radioButton2:
                        pref.edit().putInt("langue", 1).commit();
                        break;
                }
            }
        });

    }
}
