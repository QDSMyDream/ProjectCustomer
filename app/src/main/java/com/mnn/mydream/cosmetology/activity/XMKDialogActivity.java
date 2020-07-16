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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.applandeo.materialcalendarview.view.NiceSpinner;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.fuwuBean.FuWuSaleBean;
import com.mnn.mydream.cosmetology.bean.fuwuBean.ServerTypeBean;
import com.mnn.mydream.cosmetology.dialog.BeautyAddServerTypeDialog;
import com.mnn.mydream.cosmetology.dialog.CommonDialog;
import com.mnn.mydream.cosmetology.utils.Constons;
import com.mnn.mydream.cosmetology.utils.ImageLoader;
import com.mnn.mydream.cosmetology.utils.StringUtils;
import com.mnn.mydream.cosmetology.utils.ToastUtils;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：添加项目卡
 */
public class XMKDialogActivity extends AppCompatActivity {

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
    @BindView(R.id.add_img)
    ImageView addImg;
    @BindView(R.id.add_type_layout)
    PercentRelativeLayout addTypeLayout;
    @BindView(R.id.server_time)
    AppCompatEditText serverTime;
    @BindView(R.id.server_num)
    AppCompatEditText serverNum;
    @BindView(R.id.server_td)
    AppCompatEditText serverTd;
    @BindView(R.id.server_td_num)
    TextView serverTdNum;
    private String TAG = "XMKDialogActivity";
    private Integer flagInt;

    private String picPath = "https://bmob-cdn-28614.bmobpay.com/2020/07/12/49e9500440be379380eff778e5dff13a.png";

//    private List<ServerTypeBean> serverTypeBeans = new ArrayList<>();

    private FuWuSaleBean fuWuSaleBean;

    private int FLAG_INDEX;

    private BeautyAddServerTypeDialog beautyAddServerTypeDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_xmk_dialog);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {

        Intent intent = getIntent();

        fuWuSaleBean = (FuWuSaleBean) getIntent().getSerializableExtra(Constons.RESULT_UPDATE_REQUEST);

        Log.e(TAG, "onCreate: " + flagInt);

        serverMd.attachDataSource(Constons.OPERATION_MD);
        serverType.attachDataSource(Constons.ServerTypeString);

        if (fuWuSaleBean != null) {

            FLAG_INDEX = 1;
            serverName.setText(fuWuSaleBean.getServerName());
//            serverType.setSelectedIndex(Constons.ServerTypeString.indexOf(fuWuSaleBean.getServerType()));

            int j = serverType.getAdapter().getCount();
            Log.e(TAG, "initView: " + j);
            for (int i = 0; i < j; i++) {
                Log.e(TAG, "initView: " + serverType.getAdapter().getItem(i).toString());
                if (fuWuSaleBean.getServerType().equals(serverType.getAdapter().getItem(i).toString())) {
                    serverType.setSelectedIndex(i + 1);// 默认选中项
                    break;
                }
            }

            serverMd.setSelectedIndex(0);
            serverMoney.setText(fuWuSaleBean.getServerMoney() + "");
            serverTime.setText(fuWuSaleBean.getServerTime() + "");
            serverNum.setText(fuWuSaleBean.getServerNum() + "");
            serverTd.setText(fuWuSaleBean.getServerCharacteristic());
            serverTdNum.setText(fuWuSaleBean.getServerCharacteristic().length() + "");

            //加载网络图片
            ImageLoader.displayImageView(this, fuWuSaleBean.getServerUrl(), serverImgPhoto, R.mipmap.ic_launcher_round);

            title.setText("修改客户服务项目");
        } else {
            FLAG_INDEX = 0;
            flagInt = Integer.parseInt(intent.getStringExtra("flagInt"));

            title.setText("添加客户服务项目");
        }

        serverTd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                serverTdNum.setText((s.length()) + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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

    @OnClick({R.id.layout_photo, R.id.btn_yes, R.id.btn_cancel, R.id.add_type_layout})
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

            case R.id.add_type_layout:
                addTypeDialog();
                break;

        }
    }

    private void addTypeDialog() {
        BeautyAddServerTypeDialog.Builder beautyAddServerTypeBuilder = new BeautyAddServerTypeDialog.Builder(this)
                .setTitleString("添加服务类型弹窗")
                .setYesOnClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppCompatEditText editText = beautyAddServerTypeDialog.findViewById(R.id.ed_servertype);
                        if (editText.getText() != null && !editText.getText().toString().equals("")) {
                            ServerTypeBean serverTypeBean = new ServerTypeBean();
                            serverTypeBean.setServerTypeString(editText.getText().toString());
                            serverTypeBean.setBmobUser(BmobUser.getCurrentUser(BmobUser.class));
                            addServerType(serverTypeBean);

                        } else {
                            ToastUtils.showToast(getBaseContext(), "请输入服务类型", false);

                        }


                    }
                });
        beautyAddServerTypeDialog = beautyAddServerTypeBuilder.createDialog();
        // 设置点击屏幕Dialog不消失
        beautyAddServerTypeDialog.show();

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

            if (Constons.ServerTypeString.size() == 0) {
                ToastUtils.showToast(this, "请选择服务类型", false);
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

            fuWuSaleBean.setServerTime(Integer.parseInt(serverTime.getText().toString()));

            setUpdateFuWuServer(fuWuSaleBean);

        } else {

            FuWuSaleBean fuWuSaleBean = new FuWuSaleBean();

            if (StringUtils.isEmpty(serverName.getText().toString())) {
                ToastUtils.showToast(this, "请输入服务名称", false);
                return;
            }

            if (Constons.ServerTypeString.size() == 0) {
                ToastUtils.showToast(this, "请选择服务类型", false);
                return;
            }
            if (StringUtils.isEmpty(serverMoney.getText().toString())) {
                ToastUtils.showToast(this, "请输入服务价格", false);
                return;
            }

            if (StringUtils.isEmpty(serverTime.getText().toString())) {
                ToastUtils.showToast(this, "请输入服务时长", false);
                return;
            }


            fuWuSaleBean.setServerNum(StringUtils.isEmpty(serverNum.getText().toString()) ? 0 : Integer.parseInt(serverNum.getText().toString()));
            fuWuSaleBean.setServerCharacteristic(serverTd.getText().toString());

            fuWuSaleBean.setServerTime(Integer.parseInt(serverTime.getText().toString()));

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
                case 0://刷新类型
                    serverType.attachDataSource(Constons.ServerTypeString);


                    if (fuWuSaleBean != null) {
                        int k = serverType.getAdapter().getCount();
                        Log.e(TAG, "handleMessage: " + k);
                        for (int i = 0; i < k; i++) {
                            Log.e(TAG, "handleMessage: " + serverType.getAdapter().getItem(i).toString());
                            if (fuWuSaleBean.getServerType().equals(serverType.getAdapter().getItem(i).toString())) {
                                serverType.setSelectedIndex(i + 1);// 默认选中项
                                break;
                            }
                        }
                    }


                    break;
                case 1://刷新
                    setResult(Constons.RESULT_FUWU_SERVER_CODE_SCUESS_REQUEST);

                    finish();
                    break;
                case 2://刷新

                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constons.RESULT_UPDATE_REQUEST, fuWuSaleBean);
                    intent.putExtras(bundle);
                    setResult(Constons.RESULT_FUWU_SERVER_CODE_UPDATE_REQUEST, intent);

                    finish();
                    break;
                case 3://刷新

                    serverType.attachDataSource(Constons.ServerTypeString);

                    int j = serverType.getAdapter().getCount();
                    Log.e(TAG, "handleMessage: " + j);
                    for (int i = 0; i < j; i++) {
                        Log.e(TAG, "handleMessage: " + serverType.getAdapter().getItem(i).toString());
                        if (fuWuSaleBean.getServerType().equals(serverType.getAdapter().getItem(i).toString())) {
                            serverType.setSelectedIndex(i + 1);// 默认选中项
                            break;
                        }
                    }


                    beautyAddServerTypeDialog.dismiss();

                    break;

                default:
                    break;
            }
        }
    };

//    /**
//     * serverTypeStrings 服务类型
//     */
//    private void getSelectServerTypeAll() {
//
//        LoadingDialog.Builder addSignDialogBuild = new LoadingDialog.Builder(this);
//        loadingDialog = addSignDialogBuild.createDialog();
//        loadingDialog.setCanceledOnTouchOutside(false);
//        // 设置点击屏幕Dialog不消失
//        loadingDialog.show();
//
////        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////
////        Date createdAtDate = null;
////        try {
////            createdAtDate = sdf.parse(Tools.getSameDay());
////        } catch (ParseException e) {
////            e.printStackTrace();
////        }
////        BmobDate bmobCreatedAtDate = new BmobDate(createdAtDate);
////
//        BmobQuery<ServerTypeBean> categoryBmobQuery = new BmobQuery<>();
////        categoryBmobQuery.addWhereLessThanOrEqualTo("createdAt", bmobCreatedAtDate);
//        categoryBmobQuery.findObjects(new FindListener<ServerTypeBean>() {
//            @Override
//            public void done(List<ServerTypeBean> object, BmobException e) {
//                if (e == null) {
//                    if (object.size() == 0) {
//                        Constons.ServerTypeString.add("无");
//                    } else {
//                        Constons.ServerTypeString.clear();
//                        serverTypeBeans = object;
//                        for (ServerTypeBean serverTypeBean : serverTypeBeans) {
//                            Constons.ServerTypeString.add(serverTypeBean.getServerTypeString());
//                        }
//                    }
//                    loadingDialog.dismiss();
//                    refreshHandler.sendEmptyMessage(0);
//
//                } else {
//                    loadingDialog.dismiss();
//                    Log.e("BMOB", e.toString());
//                    ToastUtils.showToast(getBaseContext(), "查询失败", false);
//                }
//            }
//        });
//    }


    //添加服务类型
    private void addServerType(ServerTypeBean serverTypeBean) {

        serverTypeBean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {

                    ToastUtils.showToast(getBaseContext(), "添加服务类型成功", true);
                    Log.e("bmob", "成功");

                    Constons.ServerTypeString.add(serverTypeBean.getServerTypeString());
                    refreshHandler.sendEmptyMessage(3);
                    beautyAddServerTypeDialog.dismiss();

                } else {
                    ToastUtils.showToast(getBaseContext(), "添加服务类型失败：" + e.getMessage(), false);
                    Log.e("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }


            }
        });
    }


}
