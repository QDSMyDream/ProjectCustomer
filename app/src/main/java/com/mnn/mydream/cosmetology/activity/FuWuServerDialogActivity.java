package com.mnn.mydream.cosmetology.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.applandeo.materialcalendarview.view.NiceSpinner;
import com.applandeo.materialcalendarview.view.SpinnerTextFormatter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.CustomerAndProject;
import com.mnn.mydream.cosmetology.bean.fuwuBean.FuWuSaleBean;
import com.mnn.mydream.cosmetology.dialog.CommonDialog;
import com.mnn.mydream.cosmetology.utils.Constons;
import com.mnn.mydream.cosmetology.utils.ImageLoader;
import com.mnn.mydream.cosmetology.utils.StringUtils;
import com.mnn.mydream.cosmetology.utils.ToastUtils;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;


public class FuWuServerDialogActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.server_img_photo)
    ImageView serverImgPhoto;
    @BindView(R.id.layout_photo)
    PercentRelativeLayout layoutPhoto;
    @BindView(R.id.server_name)
    AppCompatEditText serverName;
    @BindView(R.id.server_type)
    NiceSpinner serverType;
    @BindView(R.id.server_money)
    AppCompatEditText serverMoney;
    @BindView(R.id.server_md)
    NiceSpinner serverMd;
    @BindView(R.id.btn_yes)
    AppCompatButton btnYes;
    @BindView(R.id.btn_cancel)
    AppCompatButton btnCancel;
    @BindView(R.id.myScrollView)
    ScrollView myScrollView;
    private String TAG = "FuWuServerDialogActivity";
    private Integer flagInt;

    private String picPath = "";

    private List<String> serverTypeStrings = new ArrayList<>();

    private List<String> serverMdStrings = new ArrayList<>();

    private FuWuSaleBean fuWuSaleBean;

    private int FLAG_POSTION;

    private int FLAG_INDEX;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fu_wu_server_dialog);

        ButterKnife.bind(this);
        Intent intent = getIntent();


        fuWuSaleBean = (FuWuSaleBean) getIntent().getSerializableExtra(Constons.RESULT_FUWU_SERVER_STR_UPDATE_REQUEST);


        Log.e(TAG, "onCreate: " + flagInt);

        //测试数据
        serverTypeStrings.clear();
        serverTypeStrings.add("1类的撒");
        serverTypeStrings.add("2类2答案是");
        serverTypeStrings.add("3类当时的市场秩序");
        serverTypeStrings.add("4类啊实打实 ");
        serverTypeStrings.add("5大打是类");
        serverTypeStrings.add("6类大箱子");
        serverTypeStrings.add("7类11231");
        serverTypeStrings.add("8类434324");
        //测试数据2
        serverMdStrings.clear();
        serverMdStrings.add("苏州园区曼哈顿店");

        serverMd.attachDataSource(serverMdStrings);
        serverType.attachDataSource(serverTypeStrings);


        if (fuWuSaleBean != null) {
            FLAG_INDEX = 1;
            AppCompatEditText serverName = findViewById(R.id.server_name);
            NiceSpinner serverType = findViewById(R.id.server_type);
            AppCompatEditText serverMoney = findViewById(R.id.server_money);
            NiceSpinner serverMd = findViewById(R.id.server_md);

            serverName.setText(fuWuSaleBean.getServerName());
            serverType.setSelectedIndex(serverTypeStrings.indexOf(fuWuSaleBean.getServerType()));
            serverMd.setSelectedIndex(serverMdStrings.indexOf(fuWuSaleBean.getApplyMd()));
            serverMoney.setText(fuWuSaleBean.getServerMoney() + "");

            //加载网络图片
            ImageLoader.displayImageView(this, fuWuSaleBean.getServerUrl(), serverImgPhoto);

            title.setText("修改客户服务项目");
        } else {
            FLAG_INDEX = 0;
            flagInt = Integer.parseInt(intent.getStringExtra("flagInt"));

            title.setText("添加客户服务项目");
        }

    }


    /**
     * 拍照
     */
    private void takePictrue() {
        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())
                .theme(R.style.PictureStyle)
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(false)// 是否可预览图片 true or false
                .isCamera(false)// 是否显示拍照按钮 true or false
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .enableCrop(true)// 是否裁剪 true or false
                .withAspectRatio(500, 500)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .cropWH(500, 500)// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .isDragFrame(true)// 是否可拖动裁剪框(固定)
                .forResult(Constons.RESULT_FUWU_SERVER_CODE_REQUEST);//结果回调onActivityResult code
    }

    /**
     * 相册
     */
    private void pickPictrue() {

        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.PictureStyle)
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(false)// 是否可预览图片 true or false
                .isCamera(false)// 是否显示拍照按钮 true or false
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .enableCrop(true)// 是否裁剪 true or false
                .withAspectRatio(500, 500)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .cropWH(500, 500)// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .isDragFrame(true)// 是否可拖动裁剪框(固定)
                .forResult(Constons.RESULT_FUWU_SERVER_CODE_REQUEST);//结果回调onActivityResult code

    }

    /**
     * 显示对话框
     */
    private void showPickerDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_picker_type4picture, null);

        CommonDialog.Builder builder = new CommonDialog.Builder(this);
        final CommonDialog dialog = builder
                .setStyle(R.style.DialogBottomStyle)
                .setDialogMode(CommonDialog.DialogMode.MODE_BOTTOM)
                .setWidthPro(1.0f)
                .cancelTouchout(false)
                .setView(view)
                .build();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.tv_album:
                        dialog.dismiss();
                        pickPictrue();
                        break;
                    case R.id.tv_camera:
                        dialog.dismiss();
                        takePictrue();
                        break;
                    case R.id.tv_cancel:
                        dialog.dismiss();
                        break;
                }
            }
        };

        view.findViewById(R.id.tv_album).setOnClickListener(listener);
        view.findViewById(R.id.tv_camera).setOnClickListener(listener);
        view.findViewById(R.id.tv_cancel).setOnClickListener(listener);
        dialog.show();

    }


    //上传图片到服务器
    private void uoloadImg(String f) {

        BmobFile bmobFile = new BmobFile(new File(f));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    picPath = bmobFile.getFileUrl();
                    ToastUtils.showToast(getBaseContext(), "上传图片成功", true);
                } else {
                    Log.e(TAG, "上传文件失败：" + e.getMessage());
                }
            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
                Log.e(TAG, "onProgress: " + value);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: requestCode" + requestCode + "----resultCode" + resultCode);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constons.RESULT_FUWU_SERVER_CODE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    try {
                        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                        picPath = selectList.get(0).getCutPath();

                        Log.e(TAG, "onActivityResult: " + picPath);

                        if (picPath == null || picPath.equals("")) {
                            ToastUtils.showToast(this, "图片路径获取失败", false);
                            return;
                        }

                        //加载本地图片
                        ImageLoader.displayLocalImageViewCircle(this, serverImgPhoto, picPath);
                        uoloadImg(picPath);

                    } catch (Exception e) {
                        ToastUtils.showToast(getBaseContext(), "图片路径获取失败", false);
                    }

                    break;
            }
        }

    }

    @OnClick({R.id.layout_photo, R.id.btn_yes, R.id.btn_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.layout_photo:
                showPickerDialog();
                break;
            case R.id.btn_yes:
                saveServer();
                break;
            case R.id.btn_cancel:

                finish();
                break;
        }
    }


    private void saveServer() {

        if (FLAG_INDEX == 1) {


            if (StringUtils.isEmpty(serverName.getText().toString())) {
                ToastUtils.showToast(this, "请输入服务名称", false);
                return;
            }

            if (StringUtils.isEmpty(serverMoney.getText().toString())) {
                ToastUtils.showToast(this, "请输入服务价格", false);
                return;
            }
            String type = serverType.getSelectedItem().toString();

            fuWuSaleBean.setServerName(serverName.getText().toString());
            String md = serverMd.getSelectedItem().toString();
            fuWuSaleBean.setApplyMd(md);

            fuWuSaleBean.setServerMoney(Float.parseFloat(serverMoney.getText().toString()));

            fuWuSaleBean.setServerType(type);

            if (!picPath.equals("")) {
                fuWuSaleBean.setServerUrl(picPath);
            }

            setUpdateFuWuServer(fuWuSaleBean);

        } else {

            FuWuSaleBean fuWuSaleBean = new FuWuSaleBean();

            if (StringUtils.isEmpty(serverName.getText().toString())) {
                ToastUtils.showToast(this, "请输入服务名称", false);
                return;
            }

            if (StringUtils.isEmpty(serverMoney.getText().toString())) {
                ToastUtils.showToast(this, "请输入服务价格", false);
                return;
            }
            String type = serverType.getSelectedItem().toString();

            fuWuSaleBean.setServerName(serverName.getText().toString());
            String md = serverMd.getSelectedItem().toString();
            fuWuSaleBean.setApplyMd(md);

            fuWuSaleBean.setServerMoney(Float.parseFloat(serverMoney.getText().toString()));
            fuWuSaleBean.setServerSaleFlag(true);

            fuWuSaleBean.setServerType(type);

            fuWuSaleBean.setServerUrl(picPath);


            setSaveFuWuServer(fuWuSaleBean);


        }

    }

    private void setSaveFuWuServer(FuWuSaleBean fuWuSaleBean) {
        fuWuSaleBean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    ToastUtils.showToast(getBaseContext(), "添加(" + fuWuSaleBean.getServerName() + ")服务项目成功!", true);
                    refreshHandler.sendEmptyMessage(1);
                    Log.e("bmob", "成功");
                } else {
                    ToastUtils.showToast(getBaseContext(), "添加(" + fuWuSaleBean.getServerName() + ")服务项目失败" + e.toString(), false);
                }

            }
        });

    }

    private void setUpdateFuWuServer(FuWuSaleBean fuWuSaleBean) {
        fuWuSaleBean.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtils.showToast(getBaseContext(), "修改(" + fuWuSaleBean.getServerName() + ")服务项目成功!", true);
                    refreshHandler.sendEmptyMessage(2);
                    Log.e("bmob", "成功");
                } else {
                    ToastUtils.showToast(getBaseContext(), "修改(" + fuWuSaleBean.getServerName() + ")服务项目失败" + e.toString(), false);
                }
            }

        });

    }

    ///刷新Handler
    @SuppressLint("HandlerLeak")
    private Handler refreshHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0://1s刷新

                    break;
                case 1://1s刷新
                    setResult(Constons.RESULT_FUWU_SERVER_CODE_SCUESS_REQUEST);

                    finish();
                    break;
                case 2://1s刷新

                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constons.RESULT_FUWU_SERVER_STR_UPDATE_REQUEST, fuWuSaleBean);
                    intent.putExtras(bundle);
                    setResult(Constons.RESULT_FUWU_SERVER_CODE_UPDATE_REQUEST, intent);

                    finish();
                    break;


                default:
                    break;
            }
        }
    };

}
