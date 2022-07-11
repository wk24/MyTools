package com.wuk.mycapture;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;


import java.io.File;

/**
 * 兼容android11
 * @author wuk
 * @date 2022/7/5
 */
public class CaptureUtils {
    /**
     * 选择头像相册选取
     */
    public static final int REQUESTCODE_PICK = 30001;
    /**
     * 裁剪好头像-设置头像
     */
    public static final int REQUESTCODE_CUTTING = 30002;
    /**
     * 选择头像拍照选取
     */
    public static final int PHOTO_REQUEST_TAKEPHOTO = 30003;

    private static final String TAG = "CaptureUtils";
    private static final String AUTHORITY = ".myfileprovider.pep";

    private static final int output_X = 480;
    private static final int output_Y = 480;

    /**
     * 打开相册
     * @param activity
     */
    public static void openPic(Activity activity) {

        Intent intent;
        if (Build.VERSION.SDK_INT > 30) { // Android 11 (API level 30)
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            Intent.createChooser(intent, null);
        }
        activity.startActivityForResult(intent,REQUESTCODE_PICK);
    }


    /**
     * 打开相机
     * @param activity
     * @param name
     * @return
     */
    public static Uri openCamera(Activity activity, String name) {
        return openCamera(activity, name, "pep", PHOTO_REQUEST_TAKEPHOTO);
    }

    /**
     * 打开相机
     * AndroidQ以上：图片保存进公共目录内(公共目录/picture/子文件夹)
     * AndroidQ以下：相片保存进沙盒目录内(沙盒目录/picture/子文件夹)
     * @param activity activity
     * @param name 相片名
     * @return 成功即为uri，失败为null，等到相机拍照后，该uri即为照片
     */
    public static void openCameraBitmap(Activity activity, String name) {
        String child = "pep";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(activity.getPackageManager()) == null) {
            //无相机
            Log.e(TAG, "无相机");
            return;
        }
        if (name == null || name.equals("")) {
            name = System.currentTimeMillis() + ".png";
        }
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.e(TAG, "不存在存储卡或没有读写权限");
            return ;
        }
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        activity.startActivityForResult(intent, CaptureUtils.PHOTO_REQUEST_TAKEPHOTO);
        return;
    }
    /**
     * 打开相机
     * AndroidQ以上：图片保存进公共目录内(公共目录/picture/子文件夹)
     * AndroidQ以下：相片保存进沙盒目录内(沙盒目录/picture/子文件夹)
     * @param activity activity
     * @param name 相片名
     * @param child 存放的子文件夹
     * @return 成功即为uri，失败为null，等到相机拍照后，该uri即为照片
     */
    public static Uri openCamera(Activity activity, String name, String child , int CAMERA_TAKE_PHOTO) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(activity.getPackageManager()) == null) {
            //无相机
            Log.e(TAG, "无相机");
            return null;
        }
        if (name == null || name.equals("")) {
            name = System.currentTimeMillis() + ".png";
        }
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.e(TAG, "不存在存储卡或没有读写权限");
            return null;
        }
        Uri uri = getUri(activity, name, child);
        if (uri == null) {
            Log.e(TAG, "用于存放照片的uri创建失败");
            return null;
        }
        Log.e(TAG, "cameraUri：" + uri);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        activity.startActivityForResult(intent, CAMERA_TAKE_PHOTO);
        return uri;
    }

    @Nullable
    private static Uri getUri(Activity activity, String name, String child) {
        Uri uri;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            uri = createImageUriAboveAndroidQ(activity, name, child);
        } else {
            File outFile = new File(activity.getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
            uri = getUriN(activity, outFile);
//            uri = createImageCameraUriBelowAndroidQ(activity, name, child);
        }
        return uri;
    }

    /**
     * AndroidQ以上创建用于保存相片的uri，(公有目录/pictures/child)
     * @param activity activity
     * @param name 文件名
     * @param child 子文件夹
     * @return uri
     */
    private static Uri createImageUriAboveAndroidQ(Activity activity, String name, String child) {
        ContentValues contentValues = new ContentValues();//内容
        ContentResolver resolver = activity.getContentResolver();//内容解析器
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, name);//文件名
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/*");//文件类型
        if (child != null && !child.equals("")) {
            //存放子文件夹
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/" + child);
        } else {
            //存放picture目录
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
        }
        return resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
    }

    /**
     * AndroidQ以下创建用于保存拍照的照片的uri，(沙盒目录/pictures/child)
     * 拍照传入的intent中
     * Android7以下：file类型的uri
     * Android7以上：content类型的uri
     * @param activity activity
     * @param name 文件名
     * @param child 子文件夹
     * @return content uri
     */
    private static Uri createImageCameraUriBelowAndroidQ(Activity activity, String name, String child) {
//        File pictureDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);//标准图片目录
        File pictureDir = activity.getExternalCacheDir();
        assert pictureDir != null;//获取沙盒内标准目录是不会为null的
        if (pictureDir.exists()) {
            if (child != null && !child.equals("")) {//存放子文件夹
                File childDir = new File(pictureDir + "/" + child);
                childDir.mkdirs();
                if (childDir.exists()) {
                    File picture = new File(pictureDir, name);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //适配Android7以上的path转uri
                        return FileProvider.getUriForFile(activity, activity.getPackageName()+AUTHORITY, picture);
                    } else {
                        //Android7以下
                        return Uri.fromFile(picture);
                    }
                } else {
                    return null;
                }
            } else {//存放当前目录
                File picture = new File(pictureDir, name);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //适配Android7以上的path转uri，该方法得到的uri为content类型的
                    return FileProvider.getUriForFile(activity, activity.getPackageName()+AUTHORITY, picture);
                } else {
                    //Android7以下，该方法得到的uri为file类型的
                    return Uri.fromFile(picture);
                }
            }
        } else {
            return null;
        }
    }


    /**
     * 调用系统剪裁
     * @param activity 上下文
     * @param orgUri 输入uri
     * @param name 输出名字
     */
    public static Uri cropImageUri(Activity activity , Uri orgUri, String name) {

        Uri desUri ;
//        fileCropUri = File(activity!!.getExternalCacheDir(), System.currentTimeMillis().toString() + ".jpg")
//        cropImageUri = Uri.fromFile(fileCropUri)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            desUri = getUri(activity, name, "pep");
        }else{
            File outFile = new File(activity.getExternalCacheDir(), name);
            desUri = Uri.fromFile( outFile);
        }
        cropImageUri(activity,orgUri,desUri,1,1,output_X, output_Y,REQUESTCODE_CUTTING);
        return desUri;
    }

    /**
     * 系统剪裁
     * @param activity
     * @param orgUri
     * @param desUri
     * @param aspectX
     * @param aspectY
     * @param width
     * @param height
     * @param requestCode
     */
    public static void cropImageUri(Activity activity , Uri orgUri, Uri desUri,
            int aspectX, int aspectY,int width, int height, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        Uri inUri = orgUri;
        // 兼容部分手机无法裁剪相册选择的图片
        if (Build.VERSION.SDK_INT >= 30) {
            String scheme = orgUri.getScheme();
            String path = orgUri.getPath();
            // 这个!.jpg是兼容小米有时候content://开头，.jpg结尾
            if (path == null || !path.endsWith(".jpg")) {
                if (scheme != null && scheme == ContentResolver.SCHEME_CONTENT) {
                    Cursor cursor = activity.getContentResolver().query(inUri, null, null, null, null);
                    if (cursor.moveToFirst()) {
                        @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                        inUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
                    }
                    cursor.close();
                }
            }
        }
        intent.setDataAndType(inUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("scale", true);
        //将剪切的图片保存到目标Uri中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, desUri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * Android7.0 Uri 问题(除了，getExternalCacheDir可以访问)
     * @return uri
     */
    public static Uri getUriN(Context context, File outFile){
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context, context.getPackageName()+".myfileprovider.pep", outFile);
        } else {
            uri = Uri.fromFile(outFile);
        }
        return uri;
    }


        /**
     * 读取uri所在的图片
     * @param uri
     *         图片对应的Uri
     * @param mContext
     *         上下文对象
     * @return 获取图像的Bitmap
     */
    public static Bitmap getBitmapFromUri(Uri uri, Context mContext) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.
                    getBitmap(mContext.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
