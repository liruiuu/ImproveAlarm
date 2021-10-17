package com.ryze.improvealarm;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SetAppPackage extends Activity {
    EditText set_app1, set_app2, set_app3, set_app4;
    EditText set_package1, set_package2, set_package3, set_package4;
    Button button_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        set_app1 = findViewById(R.id.set_app1);
        set_app2 = findViewById(R.id.set_app2);
        set_app3 = findViewById(R.id.set_app3);
        set_app4 = findViewById(R.id.set_app4);
        set_package1 = findViewById(R.id.set_package1);
        set_package2 = findViewById(R.id.set_package2);
        set_package3 = findViewById(R.id.set_package3);
        set_package4 = findViewById(R.id.set_package4);
        button_set = findViewById(R.id.button_set);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        set_app1.setText(MainActivity.Appname1);
        set_app2.setText(MainActivity.Appname2);
        set_app3.setText(MainActivity.Appname3);
        set_app4.setText(MainActivity.Appname4);
        set_package1.setText(MainActivity.packagename1);
        set_package2.setText(MainActivity.packagename2);
        set_package3.setText(MainActivity.packagename3);
        set_package4.setText(MainActivity.packagename4);

        button_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.Appname1 = set_app1.getText().toString();
                MainActivity.packagename1 = set_package1.getText().toString();
                MainActivity.Appname2 = set_app2.getText().toString();
                MainActivity.packagename2 = set_package2.getText().toString();
                MainActivity.Appname3 = set_app3.getText().toString();
                MainActivity.packagename3 = set_package3.getText().toString();
                MainActivity.Appname4 = set_app4.getText().toString();
                MainActivity.packagename4 = set_package4.getText().toString();
                SharedPreferences sp = getSharedPreferences("AppNameAndPackage", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("Appname1", MainActivity.Appname1);
                editor.putString("Appname2", MainActivity.Appname2);
                editor.putString("Appname3", MainActivity.Appname3);
                editor.putString("Appname4", MainActivity.Appname4);
                editor.putString("packagename1",  MainActivity.packagename1);
                editor.putString("packagename2",  MainActivity.packagename2);
                editor.putString("packagename3",  MainActivity.packagename3);
                editor.putString("packagename4",  MainActivity.packagename4);
                editor.commit();

                finish();
                Log.i("qidong", "跳转MainActivity");

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
