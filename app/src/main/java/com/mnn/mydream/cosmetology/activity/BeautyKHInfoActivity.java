package com.mnn.mydream.cosmetology.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.adapter.BeautyKhInfoListAdapter;
import com.mnn.mydream.cosmetology.bean.User;
import com.mnn.mydream.cosmetology.bean.khBean.BeautyBeanKh;
import com.mnn.mydream.cosmetology.bean.khBean.BeautyListBeanKh;
import com.mnn.mydream.cosmetology.dialog.CommonDialog;
import com.mnn.mydream.cosmetology.utils.Constons;
import com.mnn.mydream.cosmetology.utils.ImageLoader;
import com.mnn.mydream.cosmetology.utils.ToastUtils;
import com.mnn.mydream.cosmetology.view.CircleImageView;
import com.mnn.mydream.cosmetology.view.MyViewPager;
import com.zhy.android.percent.support.PercentLinearLayout;
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
import cn.bmob.v3.listener.UploadFileListener;

/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：客户详情信息界面
 */

public class BeautyKHInfoActivity extends AppCompatActivity {

    private String TAG = "BeautyKHInfoActivity";

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.kh_tx)
    CircleImageView khTx;
    @BindView(R.id.kh_vip)
    TextView khVip;
    @BindView(R.id.kh_vip_layout)
    PercentLinearLayout khVipLayout;
    @BindView(R.id.kh_name)
    TextView khName;
    @BindView(R.id.top_list1)
    ListView topList1;
    @BindView(R.id.top_layout1)
    PercentRelativeLayout topLayout1;
    @BindView(R.id.top_list2)
    ListView topList2;
    @BindView(R.id.top_layout2)
    PercentLinearLayout topLayout2;
    @BindView(R.id.top_list3)
    ListView topList3;
    @BindView(R.id.top_layout3)
    PercentLinearLayout topLayout3;
    @BindView(R.id.label_text)
    TextView labelText;
    @BindView(R.id.top_list4)
    TextView topList4;
    @BindView(R.id.top_layout4)
    PercentLinearLayout topLayout4;
    @BindView(R.id.top_info_layout)
    PercentLinearLayout topInfoLayout;
    @BindView(R.id.content_left_layout1_text1)
    TextView contentLeftLayout1Text1;
    @BindView(R.id.content_left_layout1_text2)
    TextView contentLeftLayout1Text2;
    @BindView(R.id.content_left_layout1_text3)
    TextView contentLeftLayout1Text3;
    @BindView(R.id.content_left_layout1_text4)
    TextView contentLeftLayout1Text4;
    @BindView(R.id.content_left_layout1)
    PercentRelativeLayout contentLeftLayout1;
    @BindView(R.id.content_left_layout2_text1)
    TextView contentLeftLayout2Text1;
    @BindView(R.id.content_left_layout2_text2)
    TextView contentLeftLayout2Text2;
    @BindView(R.id.content_left_layout2_text3)
    TextView contentLeftLayout2Text3;
    @BindView(R.id.content_left_layout2_text4)
    TextView contentLeftLayout2Text4;
    @BindView(R.id.content_left_layout2)
    PercentRelativeLayout contentLeftLayout2;
    @BindView(R.id.content_left_layout3_text1)
    TextView contentLeftLayout3Text1;
    @BindView(R.id.content_left_layout3_text2)
    TextView contentLeftLayout3Text2;
    @BindView(R.id.content_left_layout3_text3)
    TextView contentLeftLayout3Text3;
    @BindView(R.id.content_left_layout3_text4)
    TextView contentLeftLayout3Text4;
    @BindView(R.id.content_left_layout3)
    PercentRelativeLayout contentLeftLayout3;
    @BindView(R.id.dc_fuwu)
    TextView dcFuwu;
    @BindView(R.id.yhq)
    TextView yhq;
    @BindView(R.id.qk)
    TextView qk;
    @BindView(R.id.zsj)
    TextView zsj;
    @BindView(R.id.content_left_layout4)
    PercentLinearLayout contentLeftLayout4;
    @BindView(R.id.content_left_layout)
    PercentLinearLayout contentLeftLayout;
    @BindView(R.id.content_right_layout2_text1)
    TextView contentRightLayout2Text1;
    @BindView(R.id.content_right_layout2_img1)
    ImageView contentRightLayout2Img1;
    @BindView(R.id.content_right_layout2_text2)
    TextView contentRightLayout2Text2;
    @BindView(R.id.content_right_layout2_img2)
    ImageView contentRightLayout2Img2;
    @BindView(R.id.content_right_layout3)
    PercentLinearLayout contentRightLayout3;
    @BindView(R.id.content_right_layout2_text3)
    TextView contentRightLayout2Text3;
    @BindView(R.id.content_right_layout2)
    PercentRelativeLayout contentRightLayout2;
    @BindView(R.id.content_right_list)
    ListView contentRightList;
    @BindView(R.id.content_right_layout)
    PercentLinearLayout contentRightLayout;
    @BindView(R.id.bottom_text1)
    TextView bottomText1;
    @BindView(R.id.bottom_text2)
    TextView bottomText2;
    @BindView(R.id.bottom_text3)
    TextView bottomText3;
    @BindView(R.id.bottom_text4)
    TextView bottomText4;
    @BindView(R.id.viewpagers)
    MyViewPager viewpagers;


    private BeautyBeanKh beautyBeanKh;


    private String picPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_kh_info_layout);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {

        beautyBeanKh = (BeautyBeanKh) getIntent().getSerializableExtra(Constons.RESULT_KG_INFO_code_REQUEST);

        if (beautyBeanKh == null) {
            return;
        }
        //加载网络图片
        ImageLoader.displayImageView(this, beautyBeanKh.getTx(), khTx, R.mipmap.def_photo);
        khName.setText(beautyBeanKh.getName());
        khVip.setText(beautyBeanKh.getHy());

        List<BeautyListBeanKh> beautyListBeanKhs1 = new ArrayList<>();
        beautyListBeanKhs1.add(new BeautyListBeanKh("手机", beautyBeanKh.getPhone()));
        beautyListBeanKhs1.add(new BeautyListBeanKh("会员日期", beautyBeanKh.getCreatedAt()));
        beautyListBeanKhs1.add(new BeautyListBeanKh("归属门店", beautyBeanKh.getMd()));
        User user = BmobUser.getCurrentUser(User.class);
        beautyListBeanKhs1.add(new BeautyListBeanKh("性别", beautyBeanKh.getSex()));
        beautyListBeanKhs1.add(new BeautyListBeanKh("来源", beautyBeanKh.getLy()));
        beautyListBeanKhs1.add(new BeautyListBeanKh("所属技师", user.getUsername()));//技师为当前登陆客户
        beautyListBeanKhs1.add(new BeautyListBeanKh("所属销售", "--"));//技师为当前登陆客户
        beautyListBeanKhs1.add(new BeautyListBeanKh("最近到店", beautyBeanKh.getCreatedAt().equals(beautyBeanKh.getUpdatedAt()) ? "--" : beautyBeanKh.getUpdatedAt()));
        beautyListBeanKhs1.add(new BeautyListBeanKh("上次消费项目", "--"));
        beautyListBeanKhs1.add(new BeautyListBeanKh("累计消费", "--"));
        beautyListBeanKhs1.add(new BeautyListBeanKh("本年消费", "--"));

        BeautyKhInfoListAdapter beautyKhInfoListAdapter = new BeautyKhInfoListAdapter(getBaseContext(), beautyListBeanKhs1, 1);
        topList1.setAdapter(beautyKhInfoListAdapter);

        BeautyKhInfoListAdapter beautyKhInfoListAdapter2 = new BeautyKhInfoListAdapter(getBaseContext(), beautyListBeanKhs1, 2);
        topList2.setAdapter(beautyKhInfoListAdapter2);

        BeautyKhInfoListAdapter beautyKhInfoListAdapter3 = new BeautyKhInfoListAdapter(getBaseContext(), beautyListBeanKhs1, 3);
        topList3.setAdapter(beautyKhInfoListAdapter3);

        topList4.setText(beautyBeanKh.getRemarksContent());//标签




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
//                        ImageLoader.displayLocalImageViewCircle(this, serverImgPhoto, picPath);
                        uoloadImg(picPath);

                    } catch (Exception e) {
                        ToastUtils.showToast(getBaseContext(), "图片路径获取失败", false);
                    }

                    break;
            }
        }

    }


    ///刷新Handler
    @SuppressLint("HandlerLeak")
    private Handler refreshHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0://刷新类型

                    break;

                default:
                    break;
            }
        }
    };


    @OnClick({R.id.content_right_layout2_img1, R.id.content_right_layout2_img2, R.id.bottom_text1, R.id.bottom_text2, R.id.bottom_text3, R.id.bottom_text4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.content_right_layout2_img1:
                break;
            case R.id.content_right_layout2_img2:
                break;
            case R.id.bottom_text1:
                break;
            case R.id.bottom_text2:
                break;
            case R.id.bottom_text3:
                break;
            case R.id.bottom_text4:
                break;
        }
    }
}
