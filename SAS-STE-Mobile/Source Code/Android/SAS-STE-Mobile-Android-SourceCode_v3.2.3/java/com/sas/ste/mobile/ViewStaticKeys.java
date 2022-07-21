package com.sas.ste.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;

public class ViewStaticKeys extends AppCompatActivity {

    TextView statickeys_print_area;
    Button button_back, button_apply;

    private static String staticKeysFilePath = "";
    private static int applyPress = 3;
    private static String StaticKeys = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_static_keys_layout);

        StaticKeys = "";
        displayStaticKeysFile();

        button_apply = (Button) findViewById(R.id.button_apply);
        button_apply.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                applyPress--;
                if (applyPress == 0) {
                    applyStaticKeys();
                    applyPress = 3;
                } else {
                    Toast.makeText(ViewStaticKeys.this, applyPress+" More Time(s)", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    public void buttonManagerViewStaticKey(View view) {
        switch (view.getId()) {
            case R.id.button_back:
                back();
                break;
            case R.id.button_apply:
                Toast.makeText(ViewStaticKeys.this, "Please Press, Hold And Release The Button 3 Times", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void displayStaticKeysFile() {
        StaticKeys = StaticKeyGen.StaticKeys;
        statickeys_print_area = (TextView) findViewById(R.id.statickeys_print_area);
        statickeys_print_area.setText(StaticKeys);
    }

    private void applyStaticKeys() {
        try {
            staticKeysFilePath = Environment.getExternalStorageDirectory() + "/SAS-STE/.ProgramFiles/StaticKeys.stkey";
            File staticKeysFile = new File(staticKeysFilePath);
            if (staticKeysFile.exists()) {
                staticKeysFile.delete();
            }
            FileWriter newStaticKeysFile = new FileWriter(staticKeysFilePath);
            newStaticKeysFile.write(StaticKeys);
            newStaticKeysFile.close();
            Toast.makeText(ViewStaticKeys.this, "Static Keys Applied \nPlease Re-Open SAS-STE-Mobile", Toast.LENGTH_SHORT).show();

            back();
        }catch (Exception e) {}
    }

    private void back() {
        ViewStaticKeys.this.finish();
    }
}