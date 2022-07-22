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
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Output extends AppCompatActivity {

    Button button_share_output, button_copy_output, button_save_output_to_file, button_back, button_restart;
    TextView output_text_area;
    EditText output_save_file_name;

    protected static String staticFunctionType = "";
    protected static String staticInputType = "";
    protected static String processString = "";
    protected static ArrayList<String> processStringArray = new ArrayList<String>();
    protected static String tempFilePath = "";
    protected static String inputFilePath = "";
    protected static char[] charSet;
    protected static String key = "";
    private static String finalResult = "";
    protected static String fullResult = "";
    private static boolean finishedProcessing = false;
    private static String saveFilePath = "";
    protected static boolean callProcess = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.output_layout);
        if (callProcess) {
            callProcess();
        }

        button_save_output_to_file = (Button) findViewById(R.id.button_save_output_to_file);
        button_save_output_to_file.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                saveOutputToInputFile();
                return true;
            }
        });
    }

    private void callProcess() {
        resetVariables();
        getVariables();
        process();
    }

    private void resetVariables() {
        fullResult = "";
        finalResult = "";
        processString = "";
        processStringArray.clear();
    }

    private void getVariables() {
        staticFunctionType = MainActivity.staticFunctionType;
        staticInputType = Input.staticInputType;
        charSet = KeyGen.charSet;
        key = MainActivity.key;

    }

    private void process() {
        try {
            if (staticFunctionType == "1") {
                if (staticInputType == "1") {
                    tempFilePath = Input.tempFilePath;

                    File file = new File(tempFilePath);
                    Scanner scannedFile = new Scanner(file);
                    Boolean fileHasNextLine;
                    fileHasNextLine = scannedFile.hasNextLine();

                    int prcssArrNum = 0;

                    while (fileHasNextLine == true) {
                        processStringArray.add(scannedFile.nextLine());
                        fileHasNextLine = scannedFile.hasNextLine();
                        if (fileHasNextLine) {
                            prcssArrNum++;
                        }
                    }

                    int crrArrNum = prcssArrNum;

                    while (crrArrNum >= 0) {
                        processString = processStringArray.get(crrArrNum);
                        process.encrypt();
                        finalResult = process.result;
                        fullResult = finalResult + "\n" + fullResult;

                        output_text_area = (TextView) findViewById(R.id.output_text_area);
                        output_text_area.setText(fullResult);
                        crrArrNum--;
                    }

                    callProcess = false;
                    finishedProcessing = true;
                    Toast.makeText(Output.this, "Finished Processing", Toast.LENGTH_SHORT).show();

                    FileWriter shredTempFile = new FileWriter(tempFilePath,false);
                    int shred = 0;
                    while (shred <= 10) {
                        shredTempFile.write("SAS-STE Temp File");
                        shred++;
                    }
                    shredTempFile.close();
                    file.delete();

                } else if (staticInputType == "2") {
                    inputFilePath = Input.inputFilePath;

                    File file = new File(inputFilePath);
                    Scanner scannedFile = new Scanner(file);
                    Boolean fileHasNextLine;
                    fileHasNextLine = scannedFile.hasNextLine();

                    int prcssArrNum = 0;

                    while (fileHasNextLine == true) {
                        processStringArray.add(scannedFile.nextLine());
                        fileHasNextLine = scannedFile.hasNextLine();
                        if (fileHasNextLine) {
                            prcssArrNum++;
                        }
                    }

                    int crrArrNum = prcssArrNum;

                    while (crrArrNum >= 0) {
                        processString = processStringArray.get(crrArrNum);
                        process.encrypt();
                        finalResult = process.result;
                        fullResult = finalResult + "\n" + fullResult;

                        output_text_area = (TextView) findViewById(R.id.output_text_area);
                        output_text_area.setText(fullResult);
                        crrArrNum--;
                    }

                    callProcess = false;
                    finishedProcessing = true;
                    Toast.makeText(Output.this, "Finished Processing", Toast.LENGTH_SHORT).show();
                }

            } else if (staticFunctionType == "2") {
                if (staticInputType == "1") {
                    tempFilePath = Input.tempFilePath;

                    File file = new File(tempFilePath);
                    Scanner scannedFile = new Scanner(file);
                    Boolean fileHasNextLine;
                    fileHasNextLine = scannedFile.hasNextLine();

                    int prcssArrNum = 0;

                    while (fileHasNextLine == true) {
                        processStringArray.add(scannedFile.nextLine());
                        fileHasNextLine = scannedFile.hasNextLine();
                        if (fileHasNextLine) {
                            prcssArrNum++;
                        }
                    }

                    int crrArrNum = prcssArrNum;

                    while (crrArrNum >= 0) {
                        processString = processStringArray.get(crrArrNum);
                        process.decrypt();
                        finalResult = process.result;

                        fullResult = finalResult + "\n" + fullResult;

                        output_text_area = (TextView) findViewById(R.id.output_text_area);
                        output_text_area.setText(fullResult);
                        crrArrNum--;
                    }

                    callProcess = false;
                    finishedProcessing = true;
                    Toast.makeText(Output.this, "Finished Processing", Toast.LENGTH_SHORT).show();
                    file.delete();

                } else if (staticInputType == "2") {
                    inputFilePath = Input.inputFilePath;

                    File file = new File(inputFilePath);
                    Scanner scannedFile = new Scanner(file);
                    Boolean fileHasNextLine;
                    fileHasNextLine = scannedFile.hasNextLine();

                    int prcssArrNum = 0;

                    while (fileHasNextLine == true) {
                        processStringArray.add(scannedFile.nextLine());
                        fileHasNextLine = scannedFile.hasNextLine();
                        if (fileHasNextLine) {
                            prcssArrNum++;
                        }
                    }

                    int crrArrNum = prcssArrNum;

                    while (crrArrNum >= 0) {
                        processString = processStringArray.get(crrArrNum);
                        process.decrypt();
                        finalResult = process.result;
                        fullResult = finalResult + "\n" + fullResult;

                        output_text_area = (TextView) findViewById(R.id.output_text_area);
                        output_text_area.setText(fullResult);
                        crrArrNum--;
                    }

                    callProcess = false;
                    finishedProcessing = true;
                    Toast.makeText(Output.this, "Finished Processing", Toast.LENGTH_SHORT).show();

                }
            }
        } catch (Exception e) {}
    }

    public void buttonManagerOutput(View view) {
        boolean checked = ((Button) view).isActivated();

        switch (view.getId()) {
            case R.id.button_copy_output:
                copyOutput();
                break;
            case R.id.button_share_output:
                shareOutput();
                break;
            case R.id.button_save_output_to_file:
                saveOutputToFile();
                break;
            //-----Navigation Buttons-----
            case R.id.button_restart:
                restart();
                break;
            case R.id.button_back:
                back();
                break;
            //-----Others-----
            case R.id.button_zoom:
                zoom();
                break;
        }
    }

    private void zoom() {
        if (!finishedProcessing) {
            Toast.makeText(Output.this, "Processing Not Finished Yet", Toast.LENGTH_SHORT).show();
        } else {
            Intent OutputZoomIntent = new Intent(this, OutputZoom.class);
            startActivity(OutputZoomIntent);
        }
    }

    private void copyOutput() {
        if (!finishedProcessing) {
            Toast.makeText(Output.this, "Processing Not Finished Yet", Toast.LENGTH_SHORT).show();
        } else {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("SAS-STE Output", fullResult);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(Output.this, "Output Copied", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareOutput() {
        if (!finishedProcessing) {
            Toast.makeText(Output.this, "Processing Not Finished Yet", Toast.LENGTH_SHORT).show();
        } else {
            String sharebody = fullResult;

            Intent intentt = new Intent(Intent.ACTION_SEND);

            intentt.setType("text/plain");
            //intentt.putExtra(Intent.EXTRA_SUBJECT, "SAS-STE Output: ");

            intentt.putExtra(Intent.EXTRA_TEXT, sharebody);
            startActivity(Intent.createChooser(intentt, "Share Via"));
        }
    }

    private void saveOutputToFile() {
        if (!finishedProcessing) {
            Toast.makeText(Output.this, "Processing Not Finished Yet", Toast.LENGTH_SHORT).show();
        } else {
            output_save_file_name = (EditText) findViewById(R.id.output_save_file_name);
            String checkForFileName = output_save_file_name.getText().toString();

            if (checkForFileName.matches("")) {
                Toast.makeText(Output.this, "Enter Save File Name", Toast.LENGTH_SHORT).show();
            } else {
                saveFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/SAS-STE/Output/" + output_save_file_name.getText().toString();

                try {
                    FileWriter wfile = new FileWriter(saveFilePath, true);

                    wfile.write(fullResult);
                    wfile.close();
                } catch (Exception e) {
                }

                File savedFile = new File(saveFilePath);
                if (savedFile.exists()) {
                    Toast.makeText(Output.this, "Output Saved To "+saveFilePath, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Output.this, "Error!! Couldn't Save Output To File", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void saveOutputToInputFile() {
        try {
            inputFilePath = Input.inputFilePath;

            if (inputFilePath.equals("")) {
                Toast.makeText(Output.this, "Input Was Not Given As A File", Toast.LENGTH_SHORT).show();
            } else {
                File inputFile = new File(inputFilePath);
                if (!inputFile.exists()) {
                    Toast.makeText(Output.this, "Error!! Input File Not Found", Toast.LENGTH_SHORT).show();
                } else {
                    FileWriter shredInputFile = new FileWriter(inputFilePath);
                    int shred = 0;
                    while (shred <= 10) {
                        shredInputFile.write("SAS-STE \n SAS-STE");
                        shred++;
                    }
                    shredInputFile.close();
                    inputFile.delete();
                    FileWriter overrideInputFile = new FileWriter(inputFilePath);
                    overrideInputFile.write(fullResult);
                    overrideInputFile.close();
                    Toast.makeText(Output.this, "Output Saved To Input File", Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e) {}
    }

    private void restart() {
        Intent MainIntent = new Intent(this, MainActivity.class);
        startActivity(MainIntent);
    }

    private void back() {
        Output.this.finish();
    }
}