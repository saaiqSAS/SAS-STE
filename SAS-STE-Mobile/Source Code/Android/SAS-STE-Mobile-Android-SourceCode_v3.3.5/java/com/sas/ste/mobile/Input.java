package com.sas.ste.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Input extends AppCompatActivity {


    Button button_scan_input_text, button_select_input_file, button_paste_input_text, button_back, button_next;
    EditText edittext_input_text, edittext_input_file;
    protected static String processString = "";
    protected static String tempFilePath = "";
    protected static String inputFilePath = "";
    protected static String staticInputType = "";
    private static final int FILE_SELECT_CODE = 100;

    protected static EditText static_edittext_input_file;
    protected static EditText static_edittext_input_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_layout);

        static_edittext_input_file = (EditText) findViewById(R.id.edittext_input_file);
        static_edittext_input_text = (EditText) findViewById(R.id.edittext_input_text);

    }

    public void buttonManagerInput(View view) {
        boolean checked = ((Button) view).isActivated();

        switch (view.getId()) {
            case R.id.button_scan_input_text:
                scanText();
                break;
            case R.id.button_paste_input_text:
                pasteClipboard();
                break;
            case R.id.button_select_input_file:
                selectFile();
                break;
            case R.id.button_help_Input:
                gotoHelp();
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

    private void scanText() {
        Intent TextRecognizerOCRIntent = new Intent(this, TextRecognizerOCR.class);
        TextRecognizerOCRIntent.putExtra("prevAct","Input");
        startActivity(TextRecognizerOCRIntent);
    }

    private void selectFile() {
        Intent FileExplorerIntent = new Intent(this, FileExplorer.class);
        FileExplorerIntent.putExtra("filePath","");
        FileExplorerIntent.putExtra("prevAct","Input");
        startActivity(FileExplorerIntent);
    }

    private void pasteClipboard() {
        try {
            edittext_input_text = (EditText) findViewById(R.id.edittext_input_text);
            ClipboardManager clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            String clipboardText;
            clipboardText = clipBoard.getText().toString();
            edittext_input_text.setText(clipboardText);
        }catch (Exception e) {}
    }

    private void gotoHelp() {
        Intent HelpIntent = new Intent(this, Help.class);
        startActivity(HelpIntent);
    }

    private void next() {
        edittext_input_text = (EditText) findViewById(R.id.edittext_input_text);
        edittext_input_file = (EditText) findViewById(R.id.edittext_input_file);

        if (edittext_input_text.getText().toString().equals("")&& edittext_input_file.getText().toString().equals("") ) { // both are empty
            Toast.makeText(Input.this, "Input Data To Process First", Toast.LENGTH_SHORT).show();

        } else if (!edittext_input_text.getText().toString().equals("") && !edittext_input_file.getText().toString().equals("")) { // both are full
            Toast.makeText(Input.this, "Input Data To Process As Either Text OR File. Not Both", Toast.LENGTH_SHORT).show();

        } else if (!edittext_input_text.getText().toString().equals("") && edittext_input_file.getText().toString().equals("")) { // text is full
            staticInputType = "1";
            processString = edittext_input_text.getText().toString();
            tempFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/SAS-STE/.ProgramFiles/.Temp.stemp";

            try {
                FileWriter wfile = new FileWriter(tempFilePath, false);

                wfile.write(processString);
                wfile.close();
            } catch (Exception e) {
            }

            Output.callProcess = true;
            Intent OutputIntent = new Intent(this, Output.class);
            startActivity(OutputIntent);

        } else if (!edittext_input_file.getText().toString().equals("") && edittext_input_text.getText().toString().equals("")) { // file is full
            String tempFilePath = edittext_input_file.getText().toString();

            File file = new File(tempFilePath);

            try {
                Scanner scannedKeyFile = new Scanner(file);

                // Error handling

                if (!scannedKeyFile.hasNextLine()) {
                    Toast.makeText(Input.this, "Error!! File is empty", Toast.LENGTH_SHORT).show();
                } else {
                    staticInputType = "2";
                    inputFilePath = tempFilePath;

                    Output.callProcess = true;
                    Intent OutputIntent = new Intent(this, Output.class);
                    startActivity(OutputIntent);
                }
            } catch (Exception e) {
                Toast.makeText(Input.this, "Error!! File Not Found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void back() {
        Input.this.finish();
    }

}