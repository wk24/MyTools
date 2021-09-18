package com.wuk.mytools.utils;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @author wuk
 * @date 2019/11/25
 */
public class FileUtil {

    /**
     * File is exists boolean.
     *
     * @param path the path
     * @return the boolean
     */
    public static boolean fileIsExists(String path) {
        try {
            File f = new File(path);
            if (!f.exists() || f.length() == 0) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 创建文件
     * @param path
     * @return
     */
    public static boolean createFolder(String path) {
        if (path!=null && !path.isEmpty() && !"null".equals(path.substring(path.lastIndexOf("/") + 1))) {
            File file = new File(path);
            if (!file.exists()) {
                return file.mkdirs();
            }
        }
        return false;
    }

    /**
     * 保存文件
     * @param bm
     * @param path
     * @param name
     * @return
     */
    public static long saveBitmap(Bitmap bm, String path, String name) {
        File f = new File(path, name);
        long size = 0;
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            size = f.length();
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 资源大小,单位转换
     * @param str
     * @return
     */
    public static String getFileSize(String str){
        String size = "";
        try{
            Long file = Long.valueOf(str);
            DecimalFormat df = new DecimalFormat("#.0");
            if (file < 1024) {
                size = df.format((double) file) + "B";
            } else if (file < 1048576) {
                size = df.format((double) file / 1024) + "K";
            } else if (file < 1073741824) {
                size = df.format((double) file / 1048576) + "M";
            } else {
                size = df.format((double) file / 1073741824) +"G";
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return size;
    }


    /**
     *  将文件大小的字节数转换成String
     * @param fileSize  文件大小的字节数
     * @return String
     */
    public static String getFileSize(long fileSize){
        String size = "";
        DecimalFormat df = new DecimalFormat("0");
        DecimalFormat df1 = new DecimalFormat("#.00");
        df.setRoundingMode(RoundingMode.DOWN);
        df1.setRoundingMode(RoundingMode.DOWN);
            /*if (file < 1024) {
                size = df.format((double) file) + "B";
            } else*/ if (fileSize < 1048576) {
            size = df.format((double) fileSize / 1024) + "K";
        } else if (fileSize < 1073741824) {
            size = df.format((double) fileSize / 1048576) + "M";
        } else {
            size = df1.format((double) fileSize / 1073741824) +"G";
        }
        return size;
    }


    /**
     * 复制文件
     * @param fromFile
     * @param toFile
     * @return
     */
    public static boolean copyFile(File fromFile, File toFile) {
        try {
            InputStream is = new FileInputStream(fromFile);
            FileOutputStream fos = new FileOutputStream(toFile);
            byte[] buffer = new byte[7168];
            boolean var5 = false;

            int count;
            while((count = is.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }

            fos.close();
            is.close();
            return true;
        } catch (Exception var6) {
            var6.printStackTrace();
            return false;
        }
    }

    /**
     * 删除文件
     * @param file
     */
    public static void deleteFile( File file){
        if(file.exists()){
            if(file.isFile()){
                file.delete();
            }else if(file.isDirectory()){
                File[] listFiles = file.listFiles();
                if(listFiles!=null) {
                    for (File chile : listFiles) {
                        deleteFile(chile);
                    }
                }
                file.delete();
            }

        }
    }

    /*
     * Java文件操作 获取文件扩展名
     *
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot );
            }
        }
        return filename;
    }

    /*
     * Java文件操作 获取不带扩展名的文件名
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }


    /**
     * 保存bitmap到本地
     *
     * @param bitmap Bitmap
     */
    public static void saveBitmap(Bitmap bitmap,String path) {
        try {
            File filePic = new File(path);
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * bitmap转base64
     * */
    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
