package com.wuk.mycapture;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

/**
 * bitmap形式调用相机
 * 避免: 调用系统相机, 不拍照直接返回, 相册中出现空百图片
 */
public class MainActivity2 extends AppCompatActivity {
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
//                uri = CaptureUtils.openCamera(MainActivity2.this, System.currentTimeMillis() + ".jpg");

                 CaptureUtils.openCameraBitmap(MainActivity2.this, System.currentTimeMillis() + ".jpg");
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CaptureUtils.openPic(MainActivity2.this);
            }
        });
    }

    File fileCropUri;
    Uri cropImageUri;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case CaptureUtils.PHOTO_REQUEST_TAKEPHOTO:
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                iv.setImageBitmap(bitmap);
                break;

            case CaptureUtils.REQUESTCODE_PICK:
                if (data == null || data.getData() == null) {
                    return;
                }
                Uri uri = data.getData();
                Bitmap bitmapFromUri = CaptureUtils.getBitmapFromUri(uri, MainActivity2.this);
                iv.setImageBitmap(bitmapFromUri);

                break;

            case CaptureUtils.REQUESTCODE_CUTTING:

//                Bitmap bitmap = CaptureUtils.getBitmapFromUri(cropUri, this);
//                iv.setImageBitmap(bitmap);

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