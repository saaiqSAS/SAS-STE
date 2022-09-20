package com.sas.ste.mobile;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.Arrays;

public class FileExplorerAdapter extends RecyclerView.Adapter<FileExplorerAdapter.ViewHolder> {

    private static File[] fileList;
    private Context context;


    //ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button button_row_file_explorer;

        public ViewHolder(View view) {
            super(view);

            button_row_file_explorer = (Button) view.findViewById(R.id.button_row_file_explorer);

        }

        public Button getButton_row_file_explorer() {
            return button_row_file_explorer;
        }
    }

    // Dataset
    public FileExplorerAdapter(File[] files, Context cntext) {
        fileList = files;
        context = cntext;
    }

    //Create new Views/Rows
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGrup, int viewTipe) {
        View row = LayoutInflater.from(viewGrup.getContext()).inflate(R.layout.file_explorer_row_layout, viewGrup , false);

        return new ViewHolder(row);
    }

    //write to the new View/Row
    @Override
    public void onBindViewHolder(ViewHolder viewhoder,final int placeValue) {
        //set filename
        viewhoder.getButton_row_file_explorer().setText(fileList[placeValue].getName());

        //set file icon
        if (fileList[placeValue].isDirectory()) {
            viewhoder.getButton_row_file_explorer().setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_directory,0,0,0);
        } else if (!fileList[placeValue].isDirectory()) {
            viewhoder.getButton_row_file_explorer().setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_file,0,0,0);
        }

        //click listener
        viewhoder.getButton_row_file_explorer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File onClickFile = fileList[viewhoder.getAdapterPosition()];
                File dir = new File( onClickFile.getAbsolutePath());

                if (dir.isDirectory()) {
                    fileList = dir.listFiles();
                    Arrays.sort(fileList);
                    notifyDataSetChanged();
                }

                FileExplorer.filePath = onClickFile.getAbsolutePath();
                FileExplorer.setPath();
            }
        });


    }

    //size of localDataSet
    @Override
    public  int getItemCount() {
        return fileList.length;
    }

}
