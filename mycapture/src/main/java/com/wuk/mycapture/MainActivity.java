package com.wuk.mycapture;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView iv;
    private ImageView iv1;
    private Uri uri;
    private Uri cropUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean granted = PermissionUtils.isGranted(this, "android.permission.MANAGE_EXTERNAL_STORAGE","android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA");
        if (!granted) {
            requestPermissions();
        }



        TextView tv1 = findViewById(R.id.tv1);
        TextView tv2 = findViewById(R.id.tv2);
        iv = findViewById(R.id.iv);
        iv1 = findViewById(R.id.iv1);

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uri = CaptureUtils.openCamera(MainActivity.this, System.currentTimeMillis() + ".jpg");
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CaptureUtils.openPic(MainActivity.this);
            }
        });
    }


    File fileCropUri;
    Uri cropImageUri;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case CaptureUtils.PHOTO_REQUEST_TAKEPHOTO:
                cropUri = CaptureUtils.cropImageUri(this, uri, System.currentTimeMillis() + ".jpg");
                break;

            case CaptureUtils.REQUESTCODE_PICK:
                if (data == null || data.getData() == null) {
                    return;
                }
                cropUri = CaptureUtils.cropImageUri(MainActivity.this, data.getData(), System.currentTimeMillis() + ".jpg");

                break;

            case CaptureUtils.REQUESTCODE_CUTTING:

                Bitmap bitmap = CaptureUtils.getBitmapFromUri(cropUri, this);
                iv.setImageBitmap(bitmap);

                break;

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void requestPermissions() {

        PermissionUtils.permission(getApplicationContext(), "android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE")
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        if (permissionsGranted.size() == 2) {

                        }

                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                    }
                }).request();
    }
}