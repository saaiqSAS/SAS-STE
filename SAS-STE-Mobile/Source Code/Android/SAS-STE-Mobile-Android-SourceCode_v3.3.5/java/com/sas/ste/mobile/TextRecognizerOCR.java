package com.sas.ste.mobile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

public class TextRecognizerOCR extends AppCompatActivity {

    private static int GET_CAMERA_PHOTO = 100;
    private static int GET_GALLERY_PHOTO = 200;
    private static Bitmap bitImage;
    private static Uri bitUri;
    protected static String recognizedText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_recogniser_layout);
        recognizedText = "";
    }

    public void buttonManagerTextRecogniser(View view) {
        switch (view.getId()) {
            case R.id.button_gallery:
                gallery();
                break;
            case R.id.button_camera:
                camera();
                break;
            case R.id.button_confirm_recognised_text:
                confirm();
                break;
        }
    }

    private void confirm() {
        Bundle TextRecognizerOCRPutExtra = getIntent().getExtras();
        String prevAct = TextRecognizerOCRPutExtra.getString("prevAct");

        switch (prevAct) {
            case "Input":
                Input.static_edittext_input_text.setText(recognizedText);
                break;
            case "KeyInput":
                KeyInput.static_edittext_key_input_text.setText(recognizedText);
                break;
        }
        TextRecognizerOCR.this.finish();
    }

    private void camera() {
        try {
            Intent getImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (getImageIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(getImageIntent, GET_CAMERA_PHOTO);
            }
        }catch (Exception e) {}
    }

    private void gallery() {
        try {
            Intent pickIntent = new Intent();
            pickIntent.setType("image/*");
            pickIntent.setAction(Intent.ACTION_GET_CONTENT);

            startActivityForResult(pickIntent, GET_GALLERY_PHOTO);
        }catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_CAMERA_PHOTO ) {
            try {
                Bundle extras = data.getExtras();
                bitImage = (Bitmap) extras.get("data");

                ImageView imageview_image_display = (ImageView) findViewById(R.id.imageview_image_display);
                imageview_image_display.setImageBitmap(bitImage);

                extractTextCamera();

                Toast.makeText(this, "Processing, Please Wait", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {}

        } else if (requestCode == GET_GALLERY_PHOTO) {
            try {
                bitUri = (Uri) data.getData();

                Bitmap tempBitImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), bitUri);
                ImageView imageview_image_display = (ImageView) findViewById(R.id.imageview_image_display);
                imageview_image_display.setImageBitmap(tempBitImage);

                extractTextGallery();

                Toast.makeText(this, "Processing, Please Wait", Toast.LENGTH_SHORT).show();

            }catch (Exception e) {}
        }
    }

    private void extractTextCamera() {
        try {
            InputImage image = InputImage.fromBitmap(bitImage, 0);
            TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

            Task<Text> result = recognizer.process(image).addOnSuccessListener(new OnSuccessListener<Text>() {
                @Override
                public void onSuccess(Text visionText) {
                    Toast.makeText(TextRecognizerOCR.this, "Text Recognition Finished", Toast.LENGTH_SHORT).show();
                    EditText edittext_recognised_text_display = (EditText) findViewById(R.id.edittext_recognised_text_display);
                    edittext_recognised_text_display.setText(visionText.getText());
                    recognizedText = visionText.getText();
                }
            }).addOnFailureListener(
                    new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(TextRecognizerOCR.this, "Failed To Extract Text", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {}
    }

    private void extractTextGallery() {
        try {
            InputImage image = InputImage.fromFilePath(TextRecognizerOCR.this, bitUri);
            TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

            Task<Text> result = recognizer.process(image).addOnSuccessListener(new OnSuccessListener<Text>() {
                @Override
                public void onSuccess(Text visionText) {
                    Toast.makeText(TextRecognizerOCR.this, "Text Recognition Finished", Toast.LENGTH_SHORT).show();
                    EditText edittext_recognised_text_display = (EditText) findViewById(R.id.edittext_recognised_text_display);
                    edittext_recognised_text_display.setText(visionText.getText());
                    recognizedText = visionText.getText();
                }
            }).addOnFailureListener(
                    new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(TextRecognizerOCR.this, "Failed To Extract Text", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {}
    }
}