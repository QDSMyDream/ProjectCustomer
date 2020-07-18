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
import com.example.smoothcheckbox.SmoothCheckBox;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.fuwuBean.CPDataBean;
import com.mnn.mydream.cosmetology.bean.fuwuBean.FuWuSaleBean;
import com.mnn.mydream.cosmetology.bean.fuwuBean.ServerTypeBean;
import com.mnn.mydream.cosmetology.dialog.BeautyAddServerTypeDialog;
import com.mnn.mydream.cosmetology.dialog.CommonDialog;
import com.mnn.mydream.cosmetology.dialog.LoadingDialog;
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
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：添加产品
 */
public class CPAddDialogActivity extends AppCompatActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.cp_img_photo)
    ImageView cpImgPhoto;
    @BindView(R.id.layout_photo)
    PercentRelativeLayout layoutPhoto;
    @BindView(R.id.cp_name)
    AppCompatEditText cpName;
    @BindView(R.id.cp_type)
    NiceSpinner cpType;
    @BindView(R.id.add_img)
    ImageView addImg;
    @BindView(R.id.add_type_layout)
    PercentRelativeLayout addTypeLayout;
    @BindView(R.id.cp_money)
    AppCompatEditText cpMoney;
    @BindView(R.id.cp_check_box1)
    SmoothCheckBox cpCheckBox1;
    @BindView(R.id.cp_check_specifications)
    AppCompatEditText cpCheckSpecifications;
    @BindView(R.id.cp_vip_money)
    AppCompatEditText cpVipMoney;
    @BindView(R.id.cp_check_box2)
    SmoothCheckBox cpCheckBox2;
    @BindView(R.id.cp_original_price)
    AppCompatEditText cpOriginalPrice;
    @BindView(R.id.cp_md)
    NiceSpinner cpMd;
    @BindView(R.id.cp_num)
    AppCompatEditText cpNum;
    @BindView(R.id.characteristic_content)
    AppCompatEditText characteristicContent;
    @BindView(R.id.remarks_num)
    TextView remarksNum;
    @BindView(R.id.btn_yes)
    AppCompatButton btnYes;
    @BindView(R.id.btn_cancel)
    AppCompatButton btnCancel;
    @BindView(R.id.myScrollView)
    ScrollView myScrollView;
    @BindView(R.id.cp_check_specifications_text)
    TextView cpCheckSpecificationsText;

    private String TAG = "CPAddDialogActivity";

    private String picPath = "https://bmob-cdn-28614.bmobpay.com/2020/07/12/49e9500440be379380eff778e5dff13a.png";

    private CPDataBean cpDataBean;

    private boolean CP_FLAG;

    private LoadingDialog loadingDialog;

    private BeautyAddServerTypeDialog beautyAddServerTypeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_cp_dialog);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        cpDataBean = (CPDataBean) getIntent().getSerializableExtra(Constons.RESULT_UPDATE_REQUEST);

        cpMd.attachDataSource(Constons.OPERATION_MD);
        cpType.attachDataSource(Constons.ServerTypeString);

        cpCheckBox1.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if (isChecked) {
                    cpCheckSpecifications.setVisibility(View.VISIBLE);
                    cpCheckSpecificationsText.setVisibility(View.VISIBLE);
                } else {
                    cpCheckSpecifications.setVisibility(View.GONE);
                    cpCheckSpecificationsText.setVisibility(View.GONE);
                }
            }
        });

        cpCheckBox2.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {

                if (isChecked) {
                    cpVipMoney.setEnabled(false);
                    cpVipMoney.setAlpha((float) 0.5);
                    cpVipMoney.setHint(getString(R.string.beauty_not_enabled));
                } else {
                    cpVipMoney.setEnabled(true);
                    cpVipMoney.setAlpha((float) 1);
                    cpVipMoney.setHint(getString(R.string.beauty_cp_vip_money));
                }
            }
        });

        if (cpDataBean != null) {

            CP_FLAG = false;
            title.setText("修改产品界面");

            //加载网络图片
            ImageLoader.displayImageView(this, cpDataBean.getCpUrl(), cpImgPhoto, R.mipmap.ic_launcher_round);
            cpName.setText(cpDataBean.getCpName());
            int j = cpType.getAdapter().getCount();
            Log.e(TAG, "initView: " + j);
            for (int i = 0; i < j; i++) {
                Log.e(TAG, "initView: " + cpType.getAdapter().getItem(i).toString());
                if (cpDataBean.getCpType().equals(cpType.getAdapter().getItem(i).toString())) {
                    cpType.setSelectedIndex(i + 1);// 默认选中项
                    break;
                }
            }
            cpMd.setSelectedIndex(0);
            cpMoney.setText(cpDataBean.getCpMoney() + "");
            cpNum.setText(cpDataBean.getCpNum() + "");
            cpCheckSpecifications.setText(cpDataBean.getIntSpecifications() + "");//规格
            cpOriginalPrice.setText(cpDataBean.getCpOriginalPrice() + "");//原价
            characteristicContent.setText(cpDataBean.getCpCharacteristic() + "");//特点
            remarksNum.setText(cpDataBean.getCpCharacteristic().length());//特点长度
            cpCheckBox1.setChecked(cpDataBean.isOpenSpecifications());
            cpCheckBox2.setChecked(cpDataBean.isOpenVipMoney());
            cpVipMoney.setText(cpDataBean.getCpVipMoney() + "");

        } else {
            CP_FLAG = true;
            title.setText("添加产品界面");
            cpCheckBox2.setChecked(true);
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
                        ImageLoader.displayLocalImageViewCircle(this, cpImgPhoto, picPath);
                        uoloadImg(picPath);

                    } catch (Exception e) {
                        ToastUtils.showToast(getBaseContext(), "图片路径获取失败", false);
                    }

                    break;
            }
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


    ///刷新Handler
    @SuppressLint("HandlerLeak")
    private Handler refreshHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case 0://刷新类型
                    finish();
                    break;

                case 1://刷新
                    setResult(Constons.RESULT_CP_CODE_SCUESS_REQUEST);

                    finish();
                    break;

                case 2://刷新
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constons.RESULT_UPDATE_REQUEST, cpDataBean);
                    intent.putExtras(bundle);
                    setResult(Constons.RESULT_CP_CODE_UPDATE_REQUEST, intent);
                    finish();
                    break;

                case 3://刷新
                    cpType.attachDataSource(Constons.ServerTypeString);

                    int j = cpType.getAdapter().getCount();

                    Log.e(TAG, "handleMessage: " + j);
                    for (int i = 0; i < j; i++) {
                        Log.e(TAG, "handleMessage: " + cpType.getAdapter().getItem(i).toString());
                        if (cpDataBean.getCpType().equals(cpType.getAdapter().getItem(i).toString())) {
                            cpType.setSelectedIndex(i + 1);// 默认选中项
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


    @OnClick({R.id.layout_photo, R.id.btn_yes, R.id.btn_cancel, R.id.add_type_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_photo:
                showPickerDialog();
                break;
            case R.id.btn_yes:
                saveCP();
                break;
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.add_type_layout:
                addTypeDialog();
                break;

        }
    }


    private void saveCP() {

        if (StringUtils.isEmpty(cpName.getText().toString())) {
            ToastUtils.showToast(this, "请输入产品名称", false);
            return;
        }

        if (Constons.ServerTypeString.size() == 0) {
            ToastUtils.showToast(this, "请选择产品类型", false);
            return;
        }
        if (StringUtils.isEmpty(cpMoney.getText().toString())) {
            ToastUtils.showToast(this, "请输入产品价格", false);
            return;
        }

        if (StringUtils.isEmpty(cpOriginalPrice.getText().toString())) {
            ToastUtils.showToast(this, "请输入产品原价", false);
            return;
        }

        if (cpCheckBox1.isChecked()) {
            if (StringUtils.isEmpty(cpCheckSpecifications.getText().toString())) {
                ToastUtils.showToast(this, "请输入产品规格", false);
                return;
            }
        }

        if (CP_FLAG) {

            CPDataBean cpDataBean = new CPDataBean();

            cpDataBean.setCpUrl(picPath);
            cpDataBean.setCpName(cpName.getText().toString());
            cpDataBean.setCpType(cpType.getSelectedItem().toString());
            cpDataBean.setApplyMd(cpMd.getSelectedItem().toString());

            cpDataBean.setCpMoney(Float.parseFloat(cpMoney.getText().toString()));
            //销量
            cpDataBean.setCpNum(StringUtils.isEmpty(cpNum.getText().toString()) ? 0 : Integer.parseInt(cpNum.getText().toString()));
            //vip价钱
            cpDataBean.setOpenVipMoney(cpCheckBox2.isChecked());

            cpDataBean.setCpVipMoney(StringUtils.isEmpty(cpVipMoney.getText().toString()) ? 0 : Float.parseFloat(cpVipMoney.getText().toString()));

            //规格
            cpDataBean.setOpenSpecifications(cpCheckBox1.isChecked());

            cpDataBean.setIntSpecifications(StringUtils.isEmpty(cpCheckSpecifications.getText().toString()) ? 0 : Integer.parseInt(cpCheckSpecifications.getText().toString()));
            //特点
            cpDataBean.setCpCharacteristic(characteristicContent.getText().toString());
            //原价
            cpDataBean.setCpOriginalPrice(Float.parseFloat(cpOriginalPrice.getText().toString()));
            //是否上架
            cpDataBean.setCpSaleFlag(true);

            setSaveCP(cpDataBean);

        } else {
            cpDataBean.setCpUrl(picPath);
            cpDataBean.setCpName(cpName.getText().toString());
            cpDataBean.setCpType(cpType.getSelectedItem().toString());
            cpDataBean.setApplyMd(cpMd.getSelectedItem().toString());

            cpDataBean.setCpMoney(Float.parseFloat(cpMoney.getText().toString()));
            //销量
            cpDataBean.setCpNum(StringUtils.isEmpty(cpNum.getText().toString()) ? 0 : Integer.parseInt(cpNum.getText().toString()));

            //vip价钱
            cpDataBean.setOpenVipMoney(cpCheckBox2.isChecked());
            cpDataBean.setCpVipMoney(Float.parseFloat(cpVipMoney.getText().toString()));

            //规格
            cpDataBean.setOpenSpecifications(cpCheckBox1.isChecked());
            cpDataBean.setIntSpecifications(StringUtils.isEmpty(cpCheckSpecifications.getText().toString()) ? 0 : Integer.parseInt(cpCheckSpecifications.getText().toString()));

            //特点
            cpDataBean.setCpCharacteristic(characteristicContent.getText().toString());
            //原价
            cpDataBean.setCpOriginalPrice(Float.parseFloat(cpOriginalPrice.getText().toString()));
            //是否上架
            cpDataBean.setCpSaleFlag(true);

            setUpdateCP(cpDataBean);

        }
    }


    private void setUpdateCP(CPDataBean cpDataBean) {

        cpDataBean.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtils.showToast(getBaseContext(), "修改成功!", true);
                    refreshHandler.sendEmptyMessage(2);
                    Log.e("bmob", "成功");
                } else {
                    ToastUtils.showToast(getBaseContext(), "修改失败" + e.toString(), false);
                }
            }

        });

    }

    private void setSaveCP(CPDataBean cpDataBean) {
        cpDataBean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    ToastUtils.showToast(getBaseContext(), "添加(" + cpDataBean.getCpName() + ")服务项目成功!", true);
                    refreshHandler.sendEmptyMessage(1);
                    Log.e("bmob", "成功");
                } else {
                    ToastUtils.showToast(getBaseContext(), "添加(" + cpDataBean.getCpName() + ")服务项目失败" + e.toString(), false);
                }

            }
        });

    }
}
