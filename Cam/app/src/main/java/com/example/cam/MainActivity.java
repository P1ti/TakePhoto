package com.example.cam;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {

    private final int START_CAMERA_PLEASE = 0;
    private ImageView img_view;
    private String imgFileLocation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img_view = findViewById(R.id.imgView);
    }

    public void takePhotoPlease(View view) {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File PH = null;
        try {
            PH = createImgFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String authorities = getApplicationContext().getPackageName() + ".fileprovider";
        Uri photoUri = FileProvider.getUriForFile(this, authorities, PH);
        i.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(i, START_CAMERA_PLEASE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == START_CAMERA_PLEASE && resultCode == RESULT_OK) {
            Bitmap img = BitmapFactory.decodeFile(imgFileLocation);
            img_view.setImageBitmap(img);
        }
    }

    public File createImgFile() throws IOException {
        String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "JPEG_" + time + "_";
        File storage_dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File img = File.createTempFile(fileName, ".jpg", storage_dir);
        imgFileLocation = img.getAbsolutePath();
        return img;
    }
}
