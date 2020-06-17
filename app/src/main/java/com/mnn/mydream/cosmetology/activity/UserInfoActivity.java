package com.mnn.mydream.cosmetology.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.adapter.ArrayWheelAdapter;
import com.mnn.mydream.cosmetology.dialog.CommonDialog;
import com.mnn.mydream.cosmetology.utils.ImageLoader;
import com.mnn.mydream.cosmetology.utils.ToastUtils;
import com.mnn.mydream.cosmetology.utils.Tools;
import com.mnn.mydream.cosmetology.view.CircleImageView;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：管理员信息activity
 */

public class UserInfoActivity extends AppCompatActivity {

    private String TAG = "UserInfoActivity";

    @BindView(R.id.tb_back)
    ImageView tbBack;
    @BindView(R.id.tb_title)
    TextView tbTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;

    @BindView(R.id.img_photo)
    CircleImageView imgPhoto;
    @BindView(R.id.layout_photo)
    PercentRelativeLayout layoutPhoto;
    @BindView(R.id.et_nickname)
    EditText etNickname;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.layout_sex)
    PercentLinearLayout layoutSex;

    String sexTemp;

    //头像地址
    String picPath = "";

    private String name = "";

//    private MyDreamUser myDreamUsers;//当前的用户

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        name = getIntent().getStringExtra("username");

        defaultInfo();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = getWindow();
//            // Translucent status bar
//            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            SystemBarTintManager tintManager = new SystemBarTintManager(this);
//            tintManager.setStatusBarTintEnabled(true);
//            tintManager.setStatusBarTintResource(R.color.colorPrimary);// 通知栏所需颜色
//        }

    }

    private void defaultInfo() {


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }

    @OnClick({R.id.tb_back, R.id.tv_right, R.id.img_photo, R.id.layout_sex})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tb_back:
//                startActivity(new Intent(this, FragmentActivity.class));
                finish();
//                overridePendingTransition(R.anim.finish_left_in, R.anim.finish_left_out);
                break;
            case R.id.tv_right:

                updateUser();
                break;
            case R.id.img_photo:
                showPickerDialog();
                break;
            case R.id.layout_sex:
                showSexDialog();
                break;
        }
    }

    private void updateUser() {

        if (Tools.isEmpty(picPath)) {
            ToastUtils.showToast(UserInfoActivity.this, "头像不能为空", false);
            return;
        }
        if (Tools.isEmpty(etNickname.getText().toString().trim())) {

            ToastUtils.showToast(UserInfoActivity.this, "昵称不能为空", false);
            return;
        }

        if (Tools.isEmpty(tvSex.getText().toString().trim())) {
            ToastUtils.showToast(UserInfoActivity.this, "性别不能为空", false);
            return;
        }

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

    /**
     * 性别选择弹窗
     */
    private void showSexDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_selecter_wheelview, null);

        final CommonDialog dialog = new CommonDialog.Builder(this)
                .setStyle(R.style.DialogBottomStyle)
                .setDialogMode(CommonDialog.DialogMode.MODE_BOTTOM)
                .setWidthPro(1.0f)
                .cancelTouchout(false)
                .setView(view)
                .build();


        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.btn_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sexTemp != null) {
                    tvSex.setText(sexTemp);
                }
                dialog.dismiss();
            }
        });

        WheelView wheelView = view.findViewById(R.id.wheelView);
        wheelView.setCyclic(false);
        final List<String> sexItems = new ArrayList<>();
        sexItems.add("男");
        sexItems.add("女");

        for (int i = 0; i < sexItems.size(); i++) {
            if (tvSex.getText().toString().equals(sexItems.get(i))) {
                wheelView.setCurrentItem(i);
            }
        }

        wheelView.setAdapter(new ArrayWheelAdapter(sexItems));
        wheelView.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(int index) {
                sexTemp = sexItems.get(index);
            }
        });

        dialog.show();
    }

    /**
     * 相册
     */
    private void pickPictrue() {
        PictureSelector.create(UserInfoActivity.this)
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
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    try {
                        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                        // 例如 LocalMedia 里面返回三种path
                        // 1.media.getPath(); 为原图path
                        // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                        // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                        // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                        picPath = selectList.get(0).getCutPath();

                        Log.e(TAG, "onActivityResult: " + picPath);
                        if (picPath == null || picPath.equals("")) {
                            ToastUtils.showShort(UserInfoActivity.this, "图片路径获取失败");
                            return;
                        }
                        //加载本地图片
                        ImageLoader.displayLocalImageViewCircle(UserInfoActivity.this, imgPhoto, picPath);

                        //上传图片到服务器
//                        uploadFile(picPath);
                    } catch (Exception e) {
                        ToastUtils.showShort(UserInfoActivity.this, "图片路径获取失败");

                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 拍照
     */
    private void takePictrue() {
        PictureSelector.create(UserInfoActivity.this)
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
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

    }

}
