package com.sas.ste.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OutputZoom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.output_zoom_layout);

        TextView output_text_area_zoom = (TextView) findViewById(R.id.output_text_area_zoom);
        output_text_area_zoom.setText(Output.fullResult);
    }

    public void buttonManagerOutputZoom(View view) {
        boolean checked = ((Button) view).isActivated();

        switch (view.getId()) {
            case R.id.button_back_from_zoom:
                back();
                break;
        }
    }

    private void back() {
        OutputZoom.this.finish();
    }

}