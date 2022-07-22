package com.sas.ste.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class StaticKeyGen extends AppCompatActivity {

    Button button_select_statickeys_file, button_generate_statickeys, button_confirm_static_keys_file, button_restore_default_static_keys, button_view_crr_static_keys, button_back, button_apply;
    EditText edittext_input_statickeys_file, edittext_input_statickeys_file_identifier;

    private static String staticKeyFileIdentifier = "";
    private static Integer[] arr = new Integer[95];
    private static String staticKeysFilePath = "";
    private static String tempArrToString = "";
    private static char[] tempArrToStringArr = new char[95];
    private static int restoreDefaultsPress = 3;
    private static boolean correctFileFormat;
    protected static String StaticKeys = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.static_key_gen_layout);

        button_restore_default_static_keys = (Button) findViewById(R.id.button_restore_default_static_keys);
        button_restore_default_static_keys.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                restoreDefaultsPress--;
                if (restoreDefaultsPress == 0) {
                    restoreDefaultStaticKeysFile();
                    restoreDefaultsPress = 3;
                } else {
                    Toast.makeText(StaticKeyGen.this, restoreDefaultsPress+" More Time(s)", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    public void buttonManagerStaticKeyGen(View view) {
        switch (view.getId()) {
            case R.id.button_select_statickeys_file:
                selectStaticKeysFile();
                break;
            case R.id.button_generate_statickeys:
                generateStaticKeysFile();
                break;
            case R.id.button_confirm_static_keys_file:
                getExistingStaticKeyFile();
                break;
            case R.id.button_restore_default_static_keys:
                Toast.makeText(StaticKeyGen.this, "Please Press, Hold And Release The Button 3 Times", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_view_crr_static_keys:
                getCrrStaticKeys();
                break;
            case R.id.button_back:
                back();
                break;
        }

    }

    private void selectStaticKeysFile() {
        Toast.makeText(StaticKeyGen.this, "This Feature Is Only Available In The Pro Version. Please Enter Existing StaticKeys.stkey File Path Manually", Toast.LENGTH_SHORT).show();
    }

    private void restoreDefaultStaticKeysFile() {
        defaultStaticKeysFileGen();
        Toast.makeText(StaticKeyGen.this, "Default StaticKeys.stkey File Restored \nPlease Re-Open SAS-STE-Mobile", Toast.LENGTH_SHORT).show();
    }

    private void generateStaticKeysFile() {
        try {
            edittext_input_statickeys_file_identifier = (EditText) findViewById(R.id.edittext_input_statickeys_file_identifier);
            staticKeyFileIdentifier = edittext_input_statickeys_file_identifier.getText().toString();

            if (staticKeyFileIdentifier.equals("")) {
                Toast.makeText(StaticKeyGen.this, "Enter A StaticKeys.stkey File Identifier First", Toast.LENGTH_SHORT).show();
            } else {
                StaticKeys = "";

                for (int i = 0; i < arr.length; i++) {
                    arr[i] = i;
                }
                StaticKeys += "SAS-STE-StaticKeys\n";
                StaticKeys += "==================\n";
                StaticKeys += "StaticKeys: " + staticKeyFileIdentifier + "\n";
                StaticKeys += "==================\n";

                StaticKeys += "Layer1:\n";
                Collections.shuffle(Arrays.asList(arr));
                tempArrToStringArr = Arrays.toString(arr).toCharArray();
                tempArrToString = "";
                for (char echarL1 : tempArrToStringArr) {
                    if (echarL1 != '[' && echarL1 != ']') {
                        tempArrToString += echarL1;
                    } else {

                    }
                }
                StaticKeys += " " + tempArrToString + "," + "\n";

                StaticKeys += "Layer2p1:\n";
                Collections.shuffle(Arrays.asList(arr));
                tempArrToStringArr = Arrays.toString(arr).toCharArray();
                tempArrToString = "";
                for (char echarL2p1 : tempArrToStringArr) {
                    if (echarL2p1 != '[' && echarL2p1 != ']') {
                        tempArrToString += echarL2p1;
                    }
                }
                StaticKeys += " " + tempArrToString + "," + "\n";

                StaticKeys += "Layer2p2:\n";
                Collections.shuffle(Arrays.asList(arr));
                tempArrToStringArr = Arrays.toString(arr).toCharArray();
                tempArrToString = "";
                for (char echarL2p2 : tempArrToStringArr) {
                    if (echarL2p2 != '[' && echarL2p2 != ']') {
                        tempArrToString += echarL2p2;
                    }
                }
                StaticKeys += " " + tempArrToString + "," + "\n";

                StaticKeys += "Layer2p3:\n";
                Collections.shuffle(Arrays.asList(arr));
                tempArrToStringArr = Arrays.toString(arr).toCharArray();
                tempArrToString = "";
                for (char echarL2p3 : tempArrToStringArr) {
                    if (echarL2p3 != '[' && echarL2p3 != ']') {
                        tempArrToString += echarL2p3;
                    }
                }
                StaticKeys += " " + tempArrToString + "," + "\n";

                StaticKeys += "==================\n";

                viewStaticKeys();
            }
        }catch (Exception e) {
            System.out.println("generateStaticKeysFile() "+e);
        }

    }

    protected static void defaultStaticKeysFileGen() {
        try {
            File staticKeysFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/SAS-STE/.ProgramFiles/StaticKeys.stkey");
            if (staticKeysFile.exists()) {
                staticKeysFile.delete();
            }

            FileWriter defaultStaticKeysFile = new FileWriter(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/SAS-STE/.ProgramFiles/StaticKeys.stkey", true);
            defaultStaticKeysFile.write("SAS-STE-StaticKeys\n" +
                    "==================\n" +
                    "StaticKeys: Default\n" +
                    "==================\n" +
                    "Layer1:\n" +
                    " 9, 29, 91, 67, 75, 30, 59, 38, 68, 55, 80, 52, 26, 37, 56, 40, 66, 10, 65, 11, 81, 49, 18, 85, 88, 70, 51, 6, 43, 82, 41, 42, 74, 87, 12, 86, 45, 39, 58, 35, 47, 21, 3, 31, 23, 33, 16, 57, 32, 17, 84, 60, 93, 19, 20, 69, 22, 24, 5, 46, 8, 90, 83, 78, 54, 4, 48, 13, 53, 94, 77, 76, 89, 63, 34, 0, 62, 28, 79, 36, 1, 14, 2, 50, 61, 64, 72, 73, 25, 7, 15, 27, 71, 44, 92,\n" +
                    "Layer2p1:\n" +
                    " 59, 89, 15, 43, 34, 78, 5, 72, 68, 82, 86, 7, 85, 65, 69, 8, 16, 33, 94, 93, 42, 18, 53, 26, 32, 87, 41, 17, 2, 55, 79, 83, 31, 46, 76, 6, 12, 54, 36, 11, 23, 20, 9, 21, 74, 92, 62, 58, 3, 57, 35, 1, 66, 13, 67, 61, 10, 47, 52, 14, 22, 48, 60, 39, 75, 25, 88, 64, 73, 38, 19, 81, 56, 71, 0, 90, 30, 50, 77, 37, 24, 44, 40, 80, 63, 70, 45, 84, 91, 27, 4, 29, 49, 51, 28,\n" +
                    "Layer2p2:\n" +
                    " 47, 40, 29, 35, 11, 8, 86, 1, 63, 80, 19, 24, 79, 85, 60, 56, 3, 62, 59, 64, 42, 83, 44, 32, 21, 65, 78, 82, 53, 75, 15, 6, 76, 50, 66, 25, 31, 55, 58, 84, 36, 0, 69, 61, 87, 34, 74, 54, 18, 22, 37, 48, 5, 71, 7, 12, 51, 73, 67, 93, 10, 43, 94, 49, 9, 89, 13, 41, 28, 91, 38, 57, 20, 4, 92, 30, 39, 27, 70, 23, 46, 14, 33, 72, 68, 2, 26, 52, 16, 81, 17, 45, 90, 77, 88,\n" +
                    "Layer2p3:\n" +
                    " 10, 73, 51, 34, 87, 53, 76, 41, 74, 31, 23, 39, 63, 57, 32, 43, 36, 20, 92, 9, 64, 33, 46, 88, 26, 13, 55, 1, 3, 60, 14, 67, 82, 59, 94, 49, 22, 84, 28, 2, 62, 5, 65, 25, 6, 78, 37, 21, 29, 30, 85, 91, 16, 71, 79, 52, 61, 7, 19, 8, 12, 44, 0, 45, 89, 77, 80, 27, 68, 50, 42, 24, 15, 72, 69, 35, 56, 47, 83, 66, 17, 18, 48, 38, 81, 86, 93, 54, 4, 75, 58, 70, 90, 40, 11,\n" +
                    "==================");
            defaultStaticKeysFile.close();
        }catch (Exception e){}

    }

    private void getExistingStaticKeyFile() {
        try {
            StaticKeys = "";

            edittext_input_statickeys_file = (EditText) findViewById(R.id.edittext_input_statickeys_file);
            staticKeysFilePath = edittext_input_statickeys_file.getText().toString();

            if (staticKeysFilePath.equals("")) {
                Toast.makeText(StaticKeyGen.this, "Enter Existing StaticKeys.stkey File Path First", Toast.LENGTH_SHORT).show();
            } else {
                    File exitStaticKeysFile = new File(staticKeysFilePath);
                    if (!exitStaticKeysFile.exists()) {
                        Toast.makeText(StaticKeyGen.this, "Error!! File Not Found", Toast.LENGTH_SHORT).show();
                    } else {
                        char[] staticKeysFilePathArray = staticKeysFilePath.toCharArray();
                        int staticKeysFilePathLength = staticKeysFilePath.length() - 1;

                        if (staticKeysFilePathArray[staticKeysFilePathLength] == 'y' && staticKeysFilePathArray[staticKeysFilePathLength - 1] == 'e' && staticKeysFilePathArray[staticKeysFilePathLength - 2] == 'k' && staticKeysFilePathArray[staticKeysFilePathLength - 3] == 't' && staticKeysFilePathArray[staticKeysFilePathLength - 4] == 's' && staticKeysFilePathArray[staticKeysFilePathLength - 5] == '.') {

                            Scanner scannedExistStaticKeysFile = new Scanner(exitStaticKeysFile);
                            Boolean fileHasNextLine;
                            fileHasNextLine = scannedExistStaticKeysFile.hasNextLine();

                            int lineNum;
                            String tempLine = "";

                            lineNum = 1;
                            correctFileFormat = true;

                            while (fileHasNextLine) {
                                tempLine = scannedExistStaticKeysFile.nextLine();
                                if (lineNum == 1) {
                                    if (tempLine.length() == 18) {
                                        StaticKeys += tempLine + "\n";
                                    } else {
                                        Toast.makeText(StaticKeyGen.this, "Error!! StaticKeys.stkey File In The Path Given Is In An Incorrect Format", Toast.LENGTH_SHORT).show();
                                        correctFileFormat = false;
                                    }
                                } else if (lineNum == 6 || lineNum == 8 || lineNum == 10 || lineNum == 12) {
                                    if (tempLine.length() == 370) {
                                        StaticKeys += tempLine + "\n";
                                    } else {
                                        Toast.makeText(StaticKeyGen.this, "Error!! StaticKeys.stkey File In The Path Given Is In An Incorrect Format", Toast.LENGTH_SHORT).show();
                                        correctFileFormat = false;
                                    }
                                } else {
                                    StaticKeys += tempLine + "\n";
                                }
                                fileHasNextLine = scannedExistStaticKeysFile.hasNextLine();
                                lineNum++;
                            }
                            if (correctFileFormat) {
                                viewStaticKeys();
                            }
                        } else {
                            Toast.makeText(StaticKeyGen.this, "Error!! Incorrect StaticKeys File Format (.stkey Files Are Only Supported)", Toast.LENGTH_SHORT).show();
                            correctFileFormat = false;
                        }
                    }
            }
        } catch (Exception e) {}
    }

    private void getCrrStaticKeys() {
        try {
            StaticKeys = "";

            File staticKeysFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/SAS-STE/.ProgramFiles/StaticKeys.stkey");
            Scanner scannedStaticKeysFile = new Scanner(staticKeysFile);
            Boolean scannedFileHasNextLine;

            scannedFileHasNextLine = scannedStaticKeysFile.hasNextLine();
            while (scannedFileHasNextLine) {
                StaticKeys += scannedStaticKeysFile.nextLine() + "\n";
                scannedFileHasNextLine = scannedStaticKeysFile.hasNextLine();
            }
            viewStaticKeys();
        } catch (Exception e) {}
    }

    private void viewStaticKeys() {
        Intent ViewStaticKeysIntent = new Intent(this, ViewStaticKeys.class);
        startActivity(ViewStaticKeysIntent);
    }

    private void back() {
        StaticKeyGen.this.finish();
    }

}