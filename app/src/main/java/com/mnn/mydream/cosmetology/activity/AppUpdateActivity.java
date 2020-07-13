package com.mnn.mydream.cosmetology.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.AppUpdateBean;
import com.mnn.mydream.cosmetology.dialog.APPUpdateDialog;
import com.mnn.mydream.cosmetology.utils.APKVersionCodeUtils;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class AppUpdateActivity extends AppCompatActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.content1)
    AppCompatTextView content1;
    @BindView(R.id.content2)
    AppCompatTextView content2;
    @BindView(R.id.content3)
    AppCompatTextView content3;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.progressBar_layout)
    PercentLinearLayout progressBarLayout;
    @BindView(R.id.btn_cancel)
    AppCompatButton btnCancel;
    @BindView(R.id.btn_commit)
    AppCompatButton btnCommit;
    private String TAG = "AppUpdateActivity";
    /**
     * APP更新参数
     */
    private int NEW_VERSION_CODE;
    private int VERSION_CODE;
    private String VERSION_NAME = "";


    private String APPUPDATE_URL = "";
    private int mProgressInt = 0;
    private boolean IS_UPDATE_CANCEL;
    private APPUpdateDialog appUpdateDialog;

    private String newUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_update);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initUpdate();
    }


    //初始化登陆
    private void initUpdate() {


        BmobQuery<AppUpdateBean> bmobQuery = new BmobQuery<AppUpdateBean>();
        bmobQuery.getObject("Hj7pCCCx", new QueryListener<AppUpdateBean>() {
            @Override
            public void done(AppUpdateBean object, BmobException e) {
                if (e == null) {
                    Log.e(TAG, "done: " + object.toString());

                    isUpdateCheck(object);
                }
            }
        });


    }


    //检测是否更新
    private void isUpdateCheck(AppUpdateBean object) {
        VERSION_CODE = APKVersionCodeUtils.getVersionCode(this);
        VERSION_NAME = APKVersionCodeUtils.getVerName(this);
        APPUPDATE_URL = object.getPath().getFileUrl();
        NEW_VERSION_CODE = object.getVersion_i();
        Log.e(TAG, "isUpdateCheck: versionCode" + VERSION_CODE + "---" + "versionName" + VERSION_NAME);

        Log.e(TAG, "isUpdateCheck: " + object.getVersion_i());

        newUrl = "BNWY_" + NEW_VERSION_CODE + "_" + VERSION_NAME + ".apk";

        if (object.getVersion_i() > VERSION_CODE) {
//            showAppDownloadDialog(APPUPDATE_URL, object);
            title.setText("检测到有新版本");
            content1.setText("有新版本更新:" + object.getVersion_i() + "." + object.getVersion());
            content2.setText("更新版本大小:" + object.getTarget_size());
            content3.setText(object.getUpdate_log());

            btnCancel.setVisibility(View.VISIBLE);
            btnCommit.setVisibility(View.VISIBLE);

        } else {
            title.setText("已是最新版本");
            content2.setText("已是最新版本:" + "BNWY_" + NEW_VERSION_CODE + "_" + VERSION_NAME);

            btnCancel.setVisibility(View.GONE);
            btnCommit.setVisibility(View.GONE);

        }


    }


    /*
     * 开启新线程下载apk文件
     */
    private void downloadAPK(String url) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        String sdPath = Environment.getExternalStorageDirectory() + "/";
//

                        if (!getExternalFilesDir(null).exists()) {
                            getExternalFilesDir(null).mkdir();
                        }
                        Log.e(TAG, "run: " + getExternalFilesDir(null));
                        // 下载文件
                        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        int length = conn.getContentLength();

                        File apkFile = new File(getExternalFilesDir(null), newUrl);
                        FileOutputStream fos = new FileOutputStream(apkFile);

                        int count = 0;
                        byte[] buffer = new byte[1024];
                        while (!IS_UPDATE_CANCEL) {
                            int numread = is.read(buffer);
                            count += numread;
                            // 计算进度条的当前位置
                            mProgressInt = (int) (((float) count / length) * 100);
                            // 更新进度条
                            mUpdateProgressHandler.sendEmptyMessage(1);

                            // 下载完成
                            if (numread < 0) {
                                mUpdateProgressHandler.sendEmptyMessage(2);
                                break;
                            }
                            fos.write(buffer, 0, numread);
                        }
                        fos.close();
                        is.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 接收消息
     */
    private Handler mUpdateProgressHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    // 设置进度条
                    progressBar.setProgress(mProgressInt);
                    break;
                case 2:
                    // 隐藏当前下载对话框
//                    appUpdateDialog.dismiss();
                    // 安装 APK 文件
                    installAPK();
            }
        }

        ;
    };

    /*
     * 下载到本地后执行安装
     */
    protected void installAPK() {
        File apkFile = new File(getExternalFilesDir(null), newUrl);
        if (!apkFile.exists()) {
            return;
        }

        Log.e(TAG, "installAPK: " + getExternalFilesDir(null));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Log.e(TAG, "installAPK: " + apkFile.toString());
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(this, "com.mnn.mydream.cosmetology.fileProvider", apkFile);
        } else {
            uri = Uri.fromFile(apkFile);
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        startActivity(intent);


    }

    @OnClick({R.id.btn_cancel, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                IS_UPDATE_CANCEL = true;

                finish();
                break;
            case R.id.btn_commit:
                IS_UPDATE_CANCEL = false;
                title.setText("正在下载新版本");
                btnCommit.setVisibility(View.GONE);
                content1.setVisibility(View.GONE);
                content2.setVisibility(View.GONE);
                content3.setVisibility(View.GONE);
                progressBarLayout.setVisibility(View.VISIBLE);
                // 下载文件
                downloadAPK(APPUPDATE_URL);
                break;
        }
    }
}
