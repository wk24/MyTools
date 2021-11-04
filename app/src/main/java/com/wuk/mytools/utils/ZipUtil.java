package com.wuk.mytools.utils;


import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;



public class ZipUtil {


    /**
     * 压缩文件或者文件夹
     *
     * @param srcFileStr  待压缩文件或文件夹路径
     * @param destFileStr 目标文件路径
     * @throws IOException
     */
    public static void unZip(String destFileStr, String srcFileStr) throws Exception {
        unZip(srcFileStr, destFileStr, false);
    }
    public static void unZip(String outDirStr, String zipFilePath, final boolean isDelete )throws Exception{
        unZip(outDirStr,zipFilePath,isDelete,null);
    }
    public static void unZip(String outDirStr, String zipFilePath, final boolean isDelete ,InnerCallback callBack) throws Exception {
        final List<File> files = new ArrayList<>();
        File zipFile = new File(zipFilePath);
        long totalLen = 0;
        long unzipedLen = 0;
        if(callBack!=null)
            totalLen= getZipSize(zipFile);
        File targetDirectory = new File(outDirStr);
        ZipFile archive = new ZipFile(zipFile);
        Enumeration<? extends ZipEntry> e = archive.entries();
        long start = System.currentTimeMillis();

        while (e.hasMoreElements()) {
            ZipEntry entry = e.nextElement();
            File file = new File(targetDirectory, entry.getName().replace("\\", "/"));
            if (entry.isDirectory()) {
                file.mkdirs();
            } else {

                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                InputStream in = null;
                BufferedOutputStream out = null;

                try {
                    in = archive.getInputStream(entry);

                    out = new BufferedOutputStream(new FileOutputStream(file));
                    byte[] buffer = new byte[4 * 1024];
                    int count;
                    int times = 0;
                    while ((count = in.read(buffer)) > 0) {
                        out.write(buffer, 0, count);
                        unzipedLen += count;
                        times++;
                        if(times%512==0&& callBack!=null&&totalLen>0) {
                            int progress = (int) ((unzipedLen * 100) / totalLen);
                            callBack.onProgress(progress);
                        }
                    }
                    //计算zip进度

                } catch (Exception ex) {
                    ex.printStackTrace();
                    if(callBack!=null)
                        callBack.onError(ex.getMessage());
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    if (out != null) {
                        try {
                            out.close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                //判断是不是包含
                if (entry.getName().endsWith(".zip") || entry.getName().endsWith(".ppub") || entry.getName().endsWith(".images")) {
                    files.add(file);
                }
            }
        }
        archive.close();
        //学科工具用到解压完成状态
        if (callBack!=null){
            callBack.onProgress(100);
        }
        Log.i("fhl","NoPb zipUseTime" + (System.currentTimeMillis()-start));
        if (isDelete) {
            zipFile.delete();
        }

        if (files != null && files.size() > 0) {
            new Thread(new SecondZipTask(files, isDelete)).start();
        }

    }



    /**
     * 获取压缩包字节大小
     * @param filePath
     * @return
     */
    public static long getZipSize(File filePath) {
        long size = 0;
        ZipFile f;
        try {
            f = new ZipFile(filePath);
            Enumeration<? extends ZipEntry> en = f.entries();
            while (en.hasMoreElements()) {
                size += en.nextElement().getSize();
            }
        } catch (IOException e) {
            size = 0;
        }
        return size;
    }







    private static class SecondZipTask implements Runnable {
        List<File> files;
        private boolean isDelete;

        public SecondZipTask(List<File> files, boolean isDel) {
            this.files = files;
            isDelete = isDel;
        }

        @Override
        public void run() {
            for (int i = 0; i < files.size(); i++) {
                File file = files.get(i);
                try {
                    if (file.getName().endsWith(".ppub")) {
//                        File outFile = new File(file.getParentFile().getAbsolutePath() + "/" + file.getName().replace(".ppub", ".zip"));
//                        if (DecodeFile.getInstance().decodeFile(file, outFile)) ;
//                        {
//                            if (isDelete) {
//                                file.delete();
//                            }
//                            unZip(outFile.getParentFile().getAbsolutePath(), outFile.getAbsolutePath(), isDelete);
//                        }
                    } else if (file.getName().endsWith(".images")) {
                        unZip(file.getParentFile().getAbsolutePath(), file.getAbsolutePath(), isDelete);
                    } else {
                        unZip(file.getParentFile().getAbsolutePath(), file.getAbsolutePath(), isDelete);
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }

            }
        }
    }

    public interface InnerCallback {
        void onProgress(int pb);
        void onError(String msg);
    }
}
