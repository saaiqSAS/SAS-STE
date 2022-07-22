package com.sas.ste.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class KeyGen extends AppCompatActivity {

    Button button_generate_key, button_save_key_to_file, button_share_key, button_back, button_next;
    EditText key_save_file_name;
    private static String saveFilePath = "";
    private static boolean keySaved = false;
    private static int multiEncryptAmount = 1;
    protected static String key = "$STE::";
    protected static final char[] charSet = {' ','!','\"','#','$','%','&','\'','(',')','*','+',',','-','.','/','0','1','2','3','4','5','6','7','8','9',':',';','<','=','>','?','@','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','[','\\',']','^','_','`','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','{','|','}','~'}; //95chars


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.key_gen_layout);
        resetVariables();
    }

    private void resetVariables() {
        key = "$STE::";
        keySaved = false;
    }

    public void encryptAmountManager(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.ea1:
                if (checked)
                    multiEncryptAmount = 1;
                    break;
            case R.id.ea2:
                if (checked)
                    multiEncryptAmount = 2;
                    break;
            case R.id.ea3:
                if (checked)
                    multiEncryptAmount = 3;
                    break;
            case R.id.ea4:
                if (checked)
                    multiEncryptAmount = 4;
                    break;
            case R.id.ea5:
                if (checked)
                    multiEncryptAmount = 5;
                    break;
        }
    }

    public void buttonManagerKeyGen(View view) {
        boolean checked = ((Button) view).isActivated();

        switch (view.getId()) {
            case R.id.button_generate_key:
                genKey();
                TextView keyDisplay = (TextView) findViewById(R.id.key_print_area);
                keyDisplay.setText(key);
                break;
            case R.id.button_copy_key:
                copyKey();
                break;
            case R.id.button_share_key:
                shareKey();
                break;
            case R.id.button_save_key_to_file:
                saveKeyToFile();
                break;
            //-----Navigation Buttons-----
            case R.id.button_next:
                next();
                break;
            case R.id.button_back:
                back();
                break;
        }
    }

    private void copyKey() {
        if (key.equals("$STE::")) {
            Toast.makeText(KeyGen.this, "Generate Key First", Toast.LENGTH_SHORT).show();
        } else {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("SAS-STE Key", key);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(KeyGen.this, "Key Copied", Toast.LENGTH_SHORT).show();

            keySaved = true;
        }
    }

    private void shareKey() {
        if (key.equals("$STE::")) {
            Toast.makeText(KeyGen.this, "Generate Key First", Toast.LENGTH_SHORT).show();
        } else {
            String sharebody = key;

            Intent intentt = new Intent(Intent.ACTION_SEND);

            intentt.setType("text/plain");
            //intentt.putExtra(Intent.EXTRA_SUBJECT, "SAS-STE Key: ");

            intentt.putExtra(Intent.EXTRA_TEXT, sharebody);
            startActivity(Intent.createChooser(intentt, "Share Via"));

            keySaved = true;
        }
    }

    private void saveKeyToFile() {
        if (key.equals("$STE::")) {
            Toast.makeText(KeyGen.this, "Generate Key First", Toast.LENGTH_SHORT).show();
        } else {
            key_save_file_name = (EditText) findViewById(R.id.key_save_file_name);
            String checkForFileName = key_save_file_name.getText().toString();

            if (checkForFileName.matches("")) {
                Toast.makeText(KeyGen.this, "Enter Save File Name", Toast.LENGTH_SHORT).show();
            } else {
                saveFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/SAS-STE/Key/" + key_save_file_name.getText().toString() + ".skey";

                try {
                    FileWriter wfile = new FileWriter(saveFilePath, false);

                    wfile.write(key + "\n");
                    wfile.close();

                    keySaved = true;
                } catch (Exception e) {
                }

                File savedFile = new File(saveFilePath);
                if (savedFile.exists()) {
                    Toast.makeText(KeyGen.this, "Key Saved To "+saveFilePath, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(KeyGen.this, "Error!! Couldn't Save Key To File", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    protected static void noKeyGen() {
        key += "0";
        key += "0";
        for (char echar : charSet){
            key += echar;
        }
        key += "0";
        key += "0";
        key += "0";

        key += "::KEY$";
        MainActivity.key = key;
    }

    private void genKey() {

        key = "$STE::"; //reset Key

        Random rand = new Random();
        Integer[] charSetKey = new Integer[95];

        for (int i = 0; i < charSetKey.length; i++) {
            charSetKey[i] = i;
        }
        Collections.shuffle(Arrays.asList(charSetKey));

        key += rand.nextInt(2); // L0Type
        key += rand.nextInt(5); //L2pAsign

        for (int charKey : charSetKey) {
            key += charSet[charKey];
        }

        key += rand.nextInt(9); //L1Rep
        key += rand.nextInt(9); //L2Rep
        key += multiEncryptAmount-1; // fullRep
        //key += rand.nextInt(9); // fullRep

        key += "::KEY$";
        MainActivity.key = key;

        keySaved = false;

    }

    private void next() {
        if (key.equals("$STE::")) {
            Toast.makeText(KeyGen.this, "Generate Key First", Toast.LENGTH_SHORT).show();
        } else {
            if (keySaved) {
                Intent InputIntent = new Intent(this, Input.class);
                startActivity(InputIntent);
            } else {
                Toast.makeText(KeyGen.this, "Save/Share/Copy Key First", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void back() {
        KeyGen.this.finish();
    }

}