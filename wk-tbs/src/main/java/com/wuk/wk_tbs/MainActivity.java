package com.wuk.wk_tbs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String path = getApplication().getFilesDir() + "/test/";
        String name = "1.pdf";
        AssetsUtil.copyFileFromAssets(this, name , path , name);

        File file = new File(path + name);
        Log.e("TAG", "onCreate: " +file.exists());


        tv1 = findViewById(R.id.tv1);
        TextView tv2 = findViewById(R.id.tv2);
        RelativeLayout relativeLayout = findViewById(R.id.rl);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTBS();
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TbsReaderView mTbsReaderView = new TbsReaderView(MainActivity.this, new TbsReaderView.ReaderCallback(){

                    @Override
                    public void onCallBackAction(Integer integer, Object o, Object o1) {
                        Log.e("TAG", "onCallBackAction: " + integer );
                    }
                });

                relativeLayout.addView(mTbsReaderView, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    //不使用黑暗模式
                    mTbsReaderView.setForceDarkAllowed(false);
                }
                String extensionName = FileUtils.getFileType(file.getPath());
                Bundle bundle = new Bundle();
                bundle.putString(TbsReaderView.KEY_FILE_PATH, file.getPath());
                bundle.putString(TbsReaderView.KEY_TEMP_PATH, FileUtils.createCachePath(MainActivity.this));
                boolean result = mTbsReaderView.preOpen(extensionName, false);
                if (result) {
                    mTbsReaderView.openFile(bundle);
                }

            }
        });


    }

    public void initTBS() {
        HashMap map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);

        QbSdk.setDownloadWithoutWifi(true);
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv1.setText("下载X5内核完成");
                    }
                });

                //下载结束时的状态，下载成功时errorCode为100,其他均为失败，外部不需要关注具体的失败原因
                Log.d("QbSdk", "onDownloadFinish -->下载X5内核完成：" + i);
            }

            @Override
            public void onInstallFinish(int i) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv1.setText("安装X5内核进度" + i);
                    }
                });

                //安装结束时的状态，安装成功时errorCode为200,其他均为失败，外部不需要关注具体的失败原因
                Log.d("QbSdk", "onInstallFinish -->安装X5内核进度：" + i);
            }

            @Override
            public void onDownloadProgress(int i) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv1.setText("下载X5内核进度:"+ i);
                    }
                });

                //下载过程的通知，提供当前下载进度[0-100]
                Log.d("QbSdk", "onDownloadProgress -->下载X5内核进度：" + i);
            }
        });

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                // x5內核初始化完成的回调，true表x5内核加载成功，否则表加载失败，会自动切换到系统内核。
                Log.d("QbSdk", " 内核加载 " + arg0);
                Toast.makeText(MainActivity.this, " 内核加载 " + arg0, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCoreInitFinished() {
                tv1.setText("内核初始化完毕:");
                //内核初始化完毕
                Log.d("QbSdk", "内核初始化完毕");
            }
        };

        // x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
        //Log.i("QbSdk", "是否可以加载X5内核: " + QbSdk.canLoadX5(this));
        Log.i("QbSdk", "app是否主动禁用了X5内核: " + QbSdk.getIsSysWebViewForcedByOuter());

    }






}