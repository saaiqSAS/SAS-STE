package com.sas.ste.mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Arrays;

public class FileExplorer extends AppCompatActivity {

    protected static File[] fileList;
    protected static String filePath = "";
    private static String[] filePathHistory = new String[100];
    private static int filePathHistoryPlaceValue = 0 ;
    private EditText edittext_file_explorer_input;
    protected static EditText static_edittext_file_explorer_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_explorer_layout);

        static_edittext_file_explorer_input = (EditText) findViewById(R.id.edittext_file_explorer_input);
        filePathHistoryPlaceValue = 0;

        TextView file_explorer_pressToSelectFile = (TextView) findViewById(R.id.file_explorer_pressToSelectFile);
        file_explorer_pressToSelectFile.setVisibility(View.GONE);

        Bundle fileExplorerPutExtra = getIntent().getExtras();
        filePath = fileExplorerPutExtra.getString("filePath");

        getDirectory();
    }

    public void buttonManagerFileExplorer(View view) {
        switch (view.getId()) {
            case R.id.button_search_file_explorer:
                search();
                break;
            case R.id.button_select_file_path_file_explorer:
                select();
                break;
            case R.id.button_previous_file_explorer:
                previousDir();
                break;
        }
    }

    private void search() {
        filePath = edittext_file_explorer_input.getText().toString();
        getDirectory();
    }

    private void select() {
        Bundle fileExplorerPutExtra = getIntent().getExtras();
        String prevAct = fileExplorerPutExtra.getString("prevAct");

        switch (prevAct) {
            case "Input":
                Input.static_edittext_input_file.setText(filePath);
                break;
            case "KeyInput":
                KeyInput.static_edittext_key_input_file.setText(filePath);
                break;
            case "StaticKeyGen":
                StaticKeyGen.static_edittext_input_statickeys_file.setText(filePath);
                break;
        }
        FileExplorer.this.finish();
    }

    private void previousDir() {
        if (filePathHistoryPlaceValue > 1) {
            filePathHistoryPlaceValue--;
            filePath = filePathHistory[filePathHistoryPlaceValue];
            getDirectory();
        } else if (filePathHistoryPlaceValue == 1) {
            filePath = "";
            getDirectory();
        }

    }

    protected void getDirectory() {
        try {
            edittext_file_explorer_input = (EditText) findViewById(R.id.edittext_file_explorer_input);
            RecyclerView recyclerview_file_explorer = (RecyclerView) findViewById(R.id.recyclerview_file_explorer);
            TextView file_explorer_pressToSelectFile = (TextView) findViewById(R.id.file_explorer_pressToSelectFile);

            if (filePath.equals("")) {
                filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                filePathHistory[filePathHistoryPlaceValue] = filePath;
            }

            File file = new File(filePath);
            if (file.exists()) {
                if (file.isDirectory()) {

                    recyclerview_file_explorer.setVisibility(View.VISIBLE);
                    file_explorer_pressToSelectFile.setVisibility(View.GONE);

                    fileList = file.listFiles();
                    Arrays.sort(fileList);
                    setRecyclerView();

                } else {
                    recyclerview_file_explorer.setVisibility(View.GONE);
                    file_explorer_pressToSelectFile.setVisibility(View.VISIBLE);
                }
            } else {
                Toast.makeText(FileExplorer.this, "Error!! Path Does Not Exist", Toast.LENGTH_SHORT).show();
            }


            edittext_file_explorer_input.setText(filePath);

        }catch (Exception e) {
            Toast.makeText(FileExplorer.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setRecyclerView() {
        RecyclerView recView = (RecyclerView) findViewById(R.id.recyclerview_file_explorer);
        LinearLayoutManager linLay = new LinearLayoutManager(this);
        recView.setLayoutManager(linLay);

        FileExplorerAdapter recAdapter = new FileExplorerAdapter(fileList, getApplicationContext());
        recView.setAdapter(recAdapter);
    }

    protected static void setPath() {
        static_edittext_file_explorer_input.setText(filePath);

        if (filePathHistoryPlaceValue < 99) {
            filePathHistoryPlaceValue++;
            filePathHistory[filePathHistoryPlaceValue] = filePath;
        }

    }

}