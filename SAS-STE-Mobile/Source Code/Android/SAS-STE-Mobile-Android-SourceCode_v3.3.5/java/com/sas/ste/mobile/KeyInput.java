package com.sas.ste.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Scanner;

public class KeyInput extends AppCompatActivity {

    Button button_scan_key, button_select_key_file, button_paste_key, button_back, button_next;
    EditText edittext_key_input_text, edittext_key_input_file;
    private static String key = "";
    private static final int FILE_SELECT_CODE = 100;

    protected static EditText static_edittext_key_input_file;
    protected static EditText static_edittext_key_input_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.key_input_layout);

        static_edittext_key_input_file = (EditText) findViewById(R.id.edittext_key_input_file);
        static_edittext_key_input_text = (EditText) findViewById(R.id.edittext_key_input_text);
    }

    public void buttonManagerKeyInput(View view) {
        boolean checked = ((Button) view).isActivated();

        switch (view.getId()) {
            case R.id.button_scan_key:
                scanKey();
                break;
            case R.id.button_select_key_file:
                selectKeyFile();
                break;
            case R.id.button_paste_key:
                pasteClipboard();
                break;
            case R.id.button_help_KeyInput:
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

    private void scanKey() {
        Intent TextRecognizerOCRIntent = new Intent(this, TextRecognizerOCR.class);
        TextRecognizerOCRIntent.putExtra("prevAct","KeyInput");
        startActivity(TextRecognizerOCRIntent);
    }

    private void selectKeyFile() {
        Intent FileExplorerIntent = new Intent(this, FileExplorer.class);
        FileExplorerIntent.putExtra("filePath","");
        FileExplorerIntent.putExtra("prevAct","KeyInput");
        startActivity(FileExplorerIntent);
    }

    private void pasteClipboard() {
        try {
            edittext_key_input_text = (EditText) findViewById(R.id.edittext_key_input_text);
            ClipboardManager clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            String clipboardText;
            clipboardText = clipBoard.getText().toString();
            edittext_key_input_text.setText(clipboardText);
        }catch (Exception e) {}
    }

    private void gotoHelp() {
        Intent HelpIntent = new Intent(this, Help.class);
        startActivity(HelpIntent);
    }

    private void next() {
        edittext_key_input_text = (EditText) findViewById(R.id.edittext_key_input_text);
        edittext_key_input_file = (EditText) findViewById(R.id.edittext_key_input_file);

        if (edittext_key_input_text.getText().toString().equals("")&& edittext_key_input_file.getText().toString().equals("") ) { // both are empty
            Toast.makeText(KeyInput.this, "Input Key First", Toast.LENGTH_SHORT).show();

        } else if (!edittext_key_input_text.getText().toString().equals("") && !edittext_key_input_file.getText().toString().equals("")) { // both are full
            Toast.makeText(KeyInput.this, "Input Key As Either Text OR File. Not Both", Toast.LENGTH_SHORT).show();

        } else if (!edittext_key_input_text.getText().toString().equals("") && edittext_key_input_file.getText().toString().equals("")) { // text is full
            String textKey = edittext_key_input_text.getText().toString();
            if (textKey.length() == 112) {
                key = textKey;
                MainActivity.key = key;

                Intent InputIntent = new Intent(this, Input.class);
                startActivity(InputIntent);
            } else {
                Toast.makeText(KeyInput.this, "Error!! Incorrect Key Format", Toast.LENGTH_SHORT).show();
            }

        } else if (!edittext_key_input_file.getText().toString().equals("") && edittext_key_input_text.getText().toString().equals("")) { // file is full
            String keyFilePath = edittext_key_input_file.getText().toString();

            try {
                // File type check for .skey format

                int filePathCharLength = keyFilePath.length() - 1;
                char[] keyFilePathChar = keyFilePath.toCharArray();

                if (keyFilePathChar[filePathCharLength -4] == '.'&& keyFilePathChar[filePathCharLength -3] == 's' && keyFilePathChar[filePathCharLength -2] == 'k' && keyFilePathChar[filePathCharLength -1] == 'e' && keyFilePathChar[filePathCharLength] == 'y') {

                    File file = new File(keyFilePath);

                    Scanner scannedKeyFile = new Scanner(file);

                    // Error handling

                    if (!scannedKeyFile.hasNextLine()) {
                        Toast.makeText(KeyInput.this, "Error!! File is empty", Toast.LENGTH_SHORT).show();
                    }else {

                        // key format (length) check

                        String fileKey = scannedKeyFile.nextLine();

                        if (fileKey.length() == 112) {
                            key = fileKey;
                            MainActivity.key = key;

                            Intent InputIntent = new Intent(this, Input.class);
                            startActivity(InputIntent);
                        } else {
                            Toast.makeText(KeyInput.this, "Error!! Incorrect Key Format", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(KeyInput.this, "Error!! Incorrect Key File Format (.skey Files Are Only Supported)", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(KeyInput.this, "Error!! File Not Found", Toast.LENGTH_SHORT).show();
            }
        }



    }

    private void back() {
        KeyInput.this.finish();
    }

}