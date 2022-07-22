package com.sas.ste.mobile;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity {

    Button encrypt_new_key, encrypt_existing_key, encrypt_no_key, decrypt_keyed, decrypt_no_key;
    protected static String key = "";
    protected static String staticFunctionType = "";
    private static final int STORAGE_PERMISSION_CODE = 100;
    private static int staticKeyGenKeyPress = 3;

    private static boolean keyExtracted = false;
    private static int checkForDefault = 0;

    protected static int[] StaticKeyL1 = new int[95];
    protected static int[] StaticKeyL2p1 = new int[95];
    protected static int[] StaticKeyL2p2 = new int[95];
    protected static int[] StaticKeyL2p3 = new int[95];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        checkForFilesystem();
        if (!keyExtracted) {
            getStaticKeys();
            keyExtracted = true;
        }
        key = ""; // reset key

        encrypt_new_key = (Button) findViewById(R.id.encrypt_new_key);
        encrypt_new_key.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                staticKeyGenKeyPress--;
                if (staticKeyGenKeyPress == 0) {
                    gotoStaticKeyGen();
                    staticKeyGenKeyPress = 3;
                } else {
                    Toast.makeText(MainActivity.this, staticKeyGenKeyPress+" More Time(s)", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });


    }

    private void checkForFilesystem() {
        //-----Check If Permission Are Given-----

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, STORAGE_PERMISSION_CODE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, STORAGE_PERMISSION_CODE);
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.MANAGE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE
            }, STORAGE_PERMISSION_CODE);
        }


        //-----Wait Until User Gives Permissions-----
        while (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {}
        }
        try {
            Thread.sleep(500);
        } catch(InterruptedException e) {}

        //-----Check If Filesystem For SAS-STE Is Ready-----
        try {
                File mainDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/SAS-STE/");
                if (!mainDir.exists()) {
                    boolean createFile = mainDir.mkdirs();
                    if (createFile) {
                        Toast.makeText(MainActivity.this, "Download/SAS-STE Created ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Error!! Download/SAS-STE Not Created ", Toast.LENGTH_SHORT).show();
                    }
                }
        } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Error!! Couldn't Check Filesystem", Toast.LENGTH_SHORT).show();
        }

        try {
                File keysDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/SAS-STE/Key/");
                if (!keysDir.exists()) {
                    boolean createFile = keysDir.mkdirs();
                    if (createFile) {
                        Toast.makeText(MainActivity.this, "Download/SAS-STE/Key Created ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Error!! Download/SAS-STE/Key Not Created ", Toast.LENGTH_SHORT).show();
                    }
                }
        } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Error!! Couldn't Check Filesystem", Toast.LENGTH_SHORT).show();
        }

        try {
                File outputDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/SAS-STE/Output/");
                if (!outputDir.exists()) {
                    boolean createFile = outputDir.mkdirs();
                    if (createFile) {
                        Toast.makeText(MainActivity.this, "Download/SAS-STE/Output Created ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Error!! Download/SAS-STE/Output Not Created ", Toast.LENGTH_SHORT).show();
                    }
                }
        } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Error!! Couldn't Check Filesystem", Toast.LENGTH_SHORT).show();
        }

        try {
            File programFilesDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/SAS-STE/.ProgramFiles/");
            if (!programFilesDir.exists()) {
                programFilesDir.mkdirs();
            }
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Error!! Couldn't Check Filesystem", Toast.LENGTH_SHORT).show();
        }
    }

    public void buttonManagerMain(View view) {

        boolean checked = ((Button) view).isActivated();

        switch (view.getId()) {
            case R.id.encrypt_new_key:
                staticFunctionType = "1";
                gotoKeyGen();
                break;
            case R.id.encrypt_existing_key:
                staticFunctionType = "1";
                gotoKeyInput();
                break;
            case R.id.encrypt_no_key:
                staticFunctionType = "1";
                KeyGen.noKeyGen();
                gotoInput();
                break;
            case R.id.decrypt_keyed:
                staticFunctionType = "2";
                gotoKeyInput();
                break;
            case R.id.decrypt_no_key:
                staticFunctionType = "2";
                KeyGen.noKeyGen();
                gotoInput();
                break;
        }
    }

    private void gotoKeyGen() {
        Intent keyGenIntent = new Intent(this, KeyGen.class);
        startActivity(keyGenIntent);

    }

    private void gotoKeyInput() {
        Intent keyInputIntent = new Intent(this, KeyInput.class);
        startActivity(keyInputIntent);
    }

    private void gotoInput() {
        KeyGen.noKeyGen();
        Intent InputIntent = new Intent(this, Input.class);
        startActivity(InputIntent);
    }

    private void gotoStaticKeyGen() {
        Intent StaticKeyGenIntent = new Intent(this, StaticKeyGen.class);
        startActivity(StaticKeyGenIntent);
    }

    protected void getStaticKeys() {
        try {
            String tempStaticKeysFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/SAS-STE/.ProgramFiles/StaticKeys.stkey";
            char[] tempStaticKeysFilePathArr = tempStaticKeysFilePath.toCharArray();
            String staticKeysFilePath = "";

            for (char echar: tempStaticKeysFilePathArr) {
                if (echar == '\\') {
                    staticKeysFilePath += "\\\\";
                }else {
                    staticKeysFilePath += echar;
                }
            }

            File staticKeys = new File(staticKeysFilePath);
            if (staticKeys.exists()) {
                Boolean staticKeysHasNextLine;
                Scanner staticKeysFile = new Scanner(staticKeys);
                char[] tempLineArr;
                String tempLine = "";
                String tempNumString = "";
                int lineNum = 1;
                int[] tempStaticKey = new int[95];
                int crrTempStaticKeyPlace = 0;

                staticKeysHasNextLine = staticKeysFile.hasNextLine();

                if (!staticKeysHasNextLine) {
                    Toast.makeText(MainActivity.this, "Error!! StaticKeys.stkey File Is Empty", Toast.LENGTH_SHORT).show();
                    
                } else {
                    while (staticKeysHasNextLine) {
                        tempLine = staticKeysFile.nextLine();
                        if (lineNum == 3) {
                            Toast.makeText(MainActivity.this, tempLine, Toast.LENGTH_SHORT).show();
                        }
                        if (lineNum == 6 ||lineNum == 8 || lineNum == 10 || lineNum == 12  ) {

                            if (tempLine.length() == 370) {
                                tempLineArr = tempLine.toCharArray();

                                for (char echar : tempLineArr) {
                                    if (echar != ',') {
                                        tempNumString += echar;
                                    } else if (echar == ',') {

                                        switch (tempNumString) {
                                            case " 0":
                                                tempStaticKey[crrTempStaticKeyPlace] = 0;
                                                break;
                                            case " 1":
                                                tempStaticKey[crrTempStaticKeyPlace] = 1;
                                                break;
                                            case " 2":
                                                tempStaticKey[crrTempStaticKeyPlace] = 2;
                                                break;
                                            case " 3":
                                                tempStaticKey[crrTempStaticKeyPlace] = 3;
                                                break;
                                            case " 4":
                                                tempStaticKey[crrTempStaticKeyPlace] = 4;
                                                break;
                                            case " 5":
                                                tempStaticKey[crrTempStaticKeyPlace] = 5;
                                                break;
                                            case " 6":
                                                tempStaticKey[crrTempStaticKeyPlace] = 6;
                                                break;
                                            case " 7":
                                                tempStaticKey[crrTempStaticKeyPlace] = 7;
                                                break;
                                            case " 8":
                                                tempStaticKey[crrTempStaticKeyPlace] = 8;
                                                break;
                                            case " 9":
                                                tempStaticKey[crrTempStaticKeyPlace] = 9;
                                                break;
                                            case " 10":
                                                tempStaticKey[crrTempStaticKeyPlace] = 10;
                                                break;
                                            case " 11":
                                                tempStaticKey[crrTempStaticKeyPlace] = 11;
                                                break;
                                            case " 12":
                                                tempStaticKey[crrTempStaticKeyPlace] = 12;
                                                break;
                                            case " 13":
                                                tempStaticKey[crrTempStaticKeyPlace] = 13;
                                                break;
                                            case " 14":
                                                tempStaticKey[crrTempStaticKeyPlace] = 14;
                                                break;
                                            case " 15":
                                                tempStaticKey[crrTempStaticKeyPlace] = 15;
                                                break;
                                            case " 16":
                                                tempStaticKey[crrTempStaticKeyPlace] = 16;
                                                break;
                                            case " 17":
                                                tempStaticKey[crrTempStaticKeyPlace] = 17;
                                                break;
                                            case " 18":
                                                tempStaticKey[crrTempStaticKeyPlace] = 18;
                                                break;
                                            case " 19":
                                                tempStaticKey[crrTempStaticKeyPlace] = 19;
                                                break;
                                            case " 20":
                                                tempStaticKey[crrTempStaticKeyPlace] = 20;
                                                break;
                                            case " 21":
                                                tempStaticKey[crrTempStaticKeyPlace] = 21;
                                                break;
                                            case " 22":
                                                tempStaticKey[crrTempStaticKeyPlace] = 22;
                                                break;
                                            case " 23":
                                                tempStaticKey[crrTempStaticKeyPlace] = 23;
                                                break;
                                            case " 24":
                                                tempStaticKey[crrTempStaticKeyPlace] = 24;
                                                break;
                                            case " 25":
                                                tempStaticKey[crrTempStaticKeyPlace] = 25;
                                                break;
                                            case " 26":
                                                tempStaticKey[crrTempStaticKeyPlace] = 26;
                                                break;
                                            case " 27":
                                                tempStaticKey[crrTempStaticKeyPlace] = 27;
                                                break;
                                            case " 28":
                                                tempStaticKey[crrTempStaticKeyPlace] = 28;
                                                break;
                                            case " 29":
                                                tempStaticKey[crrTempStaticKeyPlace] = 29;
                                                break;
                                            case " 30":
                                                tempStaticKey[crrTempStaticKeyPlace] = 30;
                                                break;
                                            case " 31":
                                                tempStaticKey[crrTempStaticKeyPlace] = 31;
                                                break;
                                            case " 32":
                                                tempStaticKey[crrTempStaticKeyPlace] = 32;
                                                break;
                                            case " 33":
                                                tempStaticKey[crrTempStaticKeyPlace] = 33;
                                                break;
                                            case " 34":
                                                tempStaticKey[crrTempStaticKeyPlace] = 34;
                                                break;
                                            case " 35":
                                                tempStaticKey[crrTempStaticKeyPlace] = 35;
                                                break;
                                            case " 36":
                                                tempStaticKey[crrTempStaticKeyPlace] = 36;
                                                break;
                                            case " 37":
                                                tempStaticKey[crrTempStaticKeyPlace] = 37;
                                                break;
                                            case " 38":
                                                tempStaticKey[crrTempStaticKeyPlace] = 38;
                                                break;
                                            case " 39":
                                                tempStaticKey[crrTempStaticKeyPlace] = 39;
                                                break;
                                            case " 40":
                                                tempStaticKey[crrTempStaticKeyPlace] = 40;
                                                break;
                                            case " 41":
                                                tempStaticKey[crrTempStaticKeyPlace] = 41;
                                                break;
                                            case " 42":
                                                tempStaticKey[crrTempStaticKeyPlace] = 42;
                                                break;
                                            case " 43":
                                                tempStaticKey[crrTempStaticKeyPlace] = 43;
                                                break;
                                            case " 44":
                                                tempStaticKey[crrTempStaticKeyPlace] = 44;
                                                break;
                                            case " 45":
                                                tempStaticKey[crrTempStaticKeyPlace] = 45;
                                                break;
                                            case " 46":
                                                tempStaticKey[crrTempStaticKeyPlace] = 46;
                                                break;
                                            case " 47":
                                                tempStaticKey[crrTempStaticKeyPlace] = 47;
                                                break;
                                            case " 48":
                                                tempStaticKey[crrTempStaticKeyPlace] = 48;
                                                break;
                                            case " 49":
                                                tempStaticKey[crrTempStaticKeyPlace] = 49;
                                                break;
                                            case " 50":
                                                tempStaticKey[crrTempStaticKeyPlace] = 50;
                                                break;
                                            case " 51":
                                                tempStaticKey[crrTempStaticKeyPlace] = 51;
                                                break;
                                            case " 52":
                                                tempStaticKey[crrTempStaticKeyPlace] = 52;
                                                break;
                                            case " 53":
                                                tempStaticKey[crrTempStaticKeyPlace] = 53;
                                                break;
                                            case " 54":
                                                tempStaticKey[crrTempStaticKeyPlace] = 54;
                                                break;
                                            case " 55":
                                                tempStaticKey[crrTempStaticKeyPlace] = 55;
                                                break;
                                            case " 56":
                                                tempStaticKey[crrTempStaticKeyPlace] = 56;
                                                break;
                                            case " 57":
                                                tempStaticKey[crrTempStaticKeyPlace] = 57;
                                                break;
                                            case " 58":
                                                tempStaticKey[crrTempStaticKeyPlace] = 58;
                                                break;
                                            case " 59":
                                                tempStaticKey[crrTempStaticKeyPlace] = 59;
                                                break;
                                            case " 60":
                                                tempStaticKey[crrTempStaticKeyPlace] = 60;
                                                break;
                                            case " 61":
                                                tempStaticKey[crrTempStaticKeyPlace] = 61;
                                                break;
                                            case " 62":
                                                tempStaticKey[crrTempStaticKeyPlace] = 62;
                                                break;
                                            case " 63":
                                                tempStaticKey[crrTempStaticKeyPlace] = 63;
                                                break;
                                            case " 64":
                                                tempStaticKey[crrTempStaticKeyPlace] = 64;
                                                break;
                                            case " 65":
                                                tempStaticKey[crrTempStaticKeyPlace] = 65;
                                                break;
                                            case " 66":
                                                tempStaticKey[crrTempStaticKeyPlace] = 66;
                                                break;
                                            case " 67":
                                                tempStaticKey[crrTempStaticKeyPlace] = 67;
                                                break;
                                            case " 68":
                                                tempStaticKey[crrTempStaticKeyPlace] = 68;
                                                break;
                                            case " 69":
                                                tempStaticKey[crrTempStaticKeyPlace] = 69;
                                                break;
                                            case " 70":
                                                tempStaticKey[crrTempStaticKeyPlace] = 70;
                                                break;
                                            case " 71":
                                                tempStaticKey[crrTempStaticKeyPlace] = 71;
                                                break;
                                            case " 72":
                                                tempStaticKey[crrTempStaticKeyPlace] = 72;
                                                break;
                                            case " 73":
                                                tempStaticKey[crrTempStaticKeyPlace] = 73;
                                                break;
                                            case " 74":
                                                tempStaticKey[crrTempStaticKeyPlace] = 74;
                                                break;
                                            case " 75":
                                                tempStaticKey[crrTempStaticKeyPlace] = 75;
                                                break;
                                            case " 76":
                                                tempStaticKey[crrTempStaticKeyPlace] = 76;
                                                break;
                                            case " 77":
                                                tempStaticKey[crrTempStaticKeyPlace] = 77;
                                                break;
                                            case " 78":
                                                tempStaticKey[crrTempStaticKeyPlace] = 78;
                                                break;
                                            case " 79":
                                                tempStaticKey[crrTempStaticKeyPlace] = 79;
                                                break;
                                            case " 80":
                                                tempStaticKey[crrTempStaticKeyPlace] = 80;
                                                break;
                                            case " 81":
                                                tempStaticKey[crrTempStaticKeyPlace] = 81;
                                                break;
                                            case " 82":
                                                tempStaticKey[crrTempStaticKeyPlace] = 82;
                                                break;
                                            case " 83":
                                                tempStaticKey[crrTempStaticKeyPlace] = 83;
                                                break;
                                            case " 84":
                                                tempStaticKey[crrTempStaticKeyPlace] = 84;
                                                break;
                                            case " 85":
                                                tempStaticKey[crrTempStaticKeyPlace] = 85;
                                                break;
                                            case " 86":
                                                tempStaticKey[crrTempStaticKeyPlace] = 86;
                                                break;
                                            case " 87":
                                                tempStaticKey[crrTempStaticKeyPlace] = 87;
                                                break;
                                            case " 88":
                                                tempStaticKey[crrTempStaticKeyPlace] = 88;
                                                break;
                                            case " 89":
                                                tempStaticKey[crrTempStaticKeyPlace] = 89;
                                                break;
                                            case " 90":
                                                tempStaticKey[crrTempStaticKeyPlace] = 90;
                                                break;
                                            case " 91":
                                                tempStaticKey[crrTempStaticKeyPlace] = 91;
                                                break;
                                            case " 92":
                                                tempStaticKey[crrTempStaticKeyPlace] = 92;
                                                break;
                                            case " 93":
                                                tempStaticKey[crrTempStaticKeyPlace] = 93;
                                                break;
                                            case " 94":
                                                tempStaticKey[crrTempStaticKeyPlace] = 94;
                                                break;
                                            default:
                                                Toast.makeText(MainActivity.this, "Error!! Unknown Character/Number In StaticKeys.stkey File", Toast.LENGTH_SHORT).show();
                                                
                                        }
                                        crrTempStaticKeyPlace++;
                                        tempNumString = "";
                                    }
                                }
                                crrTempStaticKeyPlace = 0;
                                int tempStaticKeyCrrValue = 0;
                                switch (lineNum) {
                                    case 6:
                                        tempStaticKeyCrrValue = 0;

                                        for (int eint : tempStaticKey) {
                                            StaticKeyL1[tempStaticKeyCrrValue] = eint;
                                            tempStaticKeyCrrValue++;
                                        }
                                        if (Arrays.toString(StaticKeyL1).equals("[9, 29, 91, 67, 75, 30, 59, 38, 68, 55, 80, 52, 26, 37, 56, 40, 66, 10, 65, 11, 81, 49, 18, 85, 88, 70, 51, 6, 43, 82, 41, 42, 74, 87, 12, 86, 45, 39, 58, 35, 47, 21, 3, 31, 23, 33, 16, 57, 32, 17, 84, 60, 93, 19, 20, 69, 22, 24, 5, 46, 8, 90, 83, 78, 54, 4, 48, 13, 53, 94, 77, 76, 89, 63, 34, 0, 62, 28, 79, 36, 1, 14, 2, 50, 61, 64, 72, 73, 25, 7, 15, 27, 71, 44, 92]")){
                                            checkForDefault++;
                                        }
                                        break;
                                    case 8:
                                        tempStaticKeyCrrValue = 0;

                                        for (int eint : tempStaticKey) {
                                            StaticKeyL2p1[tempStaticKeyCrrValue] = eint;
                                            tempStaticKeyCrrValue++;
                                        }
                                        if (Arrays.toString(StaticKeyL2p1).equals("[59, 89, 15, 43, 34, 78, 5, 72, 68, 82, 86, 7, 85, 65, 69, 8, 16, 33, 94, 93, 42, 18, 53, 26, 32, 87, 41, 17, 2, 55, 79, 83, 31, 46, 76, 6, 12, 54, 36, 11, 23, 20, 9, 21, 74, 92, 62, 58, 3, 57, 35, 1, 66, 13, 67, 61, 10, 47, 52, 14, 22, 48, 60, 39, 75, 25, 88, 64, 73, 38, 19, 81, 56, 71, 0, 90, 30, 50, 77, 37, 24, 44, 40, 80, 63, 70, 45, 84, 91, 27, 4, 29, 49, 51, 28]")){
                                            checkForDefault++;
                                        }
                                        break;
                                    case 10:
                                        tempStaticKeyCrrValue = 0;

                                        for (int eint : tempStaticKey) {
                                            StaticKeyL2p2[tempStaticKeyCrrValue] = eint;
                                            tempStaticKeyCrrValue++;
                                        }
                                        if (Arrays.toString(StaticKeyL2p2).equals("[47, 40, 29, 35, 11, 8, 86, 1, 63, 80, 19, 24, 79, 85, 60, 56, 3, 62, 59, 64, 42, 83, 44, 32, 21, 65, 78, 82, 53, 75, 15, 6, 76, 50, 66, 25, 31, 55, 58, 84, 36, 0, 69, 61, 87, 34, 74, 54, 18, 22, 37, 48, 5, 71, 7, 12, 51, 73, 67, 93, 10, 43, 94, 49, 9, 89, 13, 41, 28, 91, 38, 57, 20, 4, 92, 30, 39, 27, 70, 23, 46, 14, 33, 72, 68, 2, 26, 52, 16, 81, 17, 45, 90, 77, 88]")){
                                            checkForDefault++;
                                        }
                                        break;
                                    case 12:
                                        tempStaticKeyCrrValue = 0;

                                        for (int eint : tempStaticKey) {
                                            StaticKeyL2p3[tempStaticKeyCrrValue] = eint;
                                            tempStaticKeyCrrValue++;
                                        }
                                        if (Arrays.toString(StaticKeyL2p3).equals("[10, 73, 51, 34, 87, 53, 76, 41, 74, 31, 23, 39, 63, 57, 32, 43, 36, 20, 92, 9, 64, 33, 46, 88, 26, 13, 55, 1, 3, 60, 14, 67, 82, 59, 94, 49, 22, 84, 28, 2, 62, 5, 65, 25, 6, 78, 37, 21, 29, 30, 85, 91, 16, 71, 79, 52, 61, 7, 19, 8, 12, 44, 0, 45, 89, 77, 80, 27, 68, 50, 42, 24, 15, 72, 69, 35, 56, 47, 83, 66, 17, 18, 48, 38, 81, 86, 93, 54, 4, 75, 58, 70, 90, 40, 11]")){
                                            checkForDefault++;
                                        }
                                        break;
                                }

                            }else {
                                Toast.makeText(MainActivity.this, "Error!! StaticKeys.stkey File Is In An Incorrect Format", Toast.LENGTH_SHORT).show();
                                
                            }
                        }
                        staticKeysHasNextLine = staticKeysFile.hasNextLine();
                        lineNum++;
                    }
                    if (checkForDefault >= 4) {
                        Toast.makeText(MainActivity.this, "You Are Using The Default Static Keys.\nPlease Change The Static Keys By Holding And Releasing 'New Key' 3 Times.\nFor More Information Refer To The SAS-STE Documentation At (https://saaiqsas.github.io/tools/sas-ste.html)", Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                Toast.makeText(MainActivity.this, "StaticKeys.stkey File Not Found", Toast.LENGTH_SHORT).show();
                StaticKeyGen.defaultStaticKeysFileGen();
                Toast.makeText(MainActivity.this, "New Default StaticKeys.stkey File Generated", Toast.LENGTH_SHORT).show();
                getStaticKeys();
            }
        }catch (Exception e) {}
    }

}