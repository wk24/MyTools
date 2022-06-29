package com.wuk.mytools.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Assets工具类
 * @author wuk
 * @date 2022/6/29
 */
public class AssetsUtil {


    /**
     * 将assets中的文件保存到本地目录
     * @param context
     * @param assetName
     * @param savePath
     * @param saveName
     */
    public static void copyFileFromAssets(Context context, String assetName, String savePath, String saveName) {
        // 若目标文件夹不存在，则创建
        File dir = new File(savePath);
        if (!dir.exists()) {
            if (!dir.mkdir()) {
                Log.d("FileUtils", "mkdir error: " + savePath);
                return;
            }
        }

        // 拷贝文件
        String filename = savePath + "/" + saveName;
        File file = new File(filename);
        if (!file.exists()) {
            try {
                InputStream inStream = context.getAssets().open(assetName);
                FileOutputStream fileOutputStream = new FileOutputStream(filename);

                int byteread;
                byte[] buffer = new byte[1024];
                while ((byteread = inStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, byteread);
                }
                fileOutputStream.flush();
                inStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("FileUtils", "[copyFileFromAssets] copy asset file: " + assetName + " to : " + filename);
        } else {
            Log.d("FileUtils", "[copyFileFromAssets] file is exist: " + filename);
        }
    }
}
